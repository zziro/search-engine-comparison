package com.cignium.searchengine;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.cignium.searchengine.model.BingResult;
import com.cignium.searchengine.model.GenericResponse;
import com.cignium.searchengine.model.GoogleResult;
import com.cignium.searchengine.model.Response;
import com.cignium.searchengine.model.SearchInformationModel;
import com.cignium.searchengine.model.SearchResults;
import com.cignium.searchengine.model.YandexResult;
import com.cignium.searchengine.model.yandex.Yandexsearch;
import com.cignium.searchengine.service.APIConsumerService;
import com.cignium.searchengine.service.impl.APIConsumerServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App {

	public static void main(String[] args) throws IOException {
		App app = new App();

		// List of parameters
		List<Response> collectionResponse = app.searchEngineComparison("Java PHP JavaScript");

		// List of value by Search Engine
		List<Long> googleHighestList = new ArrayList<Long>();
		List<Long> bingHighestList = new ArrayList<Long>();
		List<Long> yandexHighestList = new ArrayList<Long>();

		// The highest by Search Engine
		Long googleHighestValue = null;
		Long bingHighestValue = null;
		Long yandexHighestValue = null;

		// The total highest
		Long totalHighestValue = null;

		GoogleResult googleResult = null;
		BingResult bingResult = null;
		YandexResult yandexResult = null;

		for (Response response : collectionResponse) {

			System.out.println(response.getGoogleResponse() + " " + response.getBingResponse() + " "
					+ response.getYandexResponse());
			googleResult = new GoogleResult();
			googleResult.setGoogleValue(response.getGoogleResponse());

			bingResult = new BingResult();
			bingResult.setBingResult(response.getBingResponse());

			yandexResult = new YandexResult();
			yandexResult.setYandexValue(response.getYandexResponse());

			googleHighestList.add(googleResult.getGoogleValue());
			bingHighestList.add(bingResult.getBingResult());
			yandexHighestList.add(yandexResult.getYandexValue());

		}

		// Gettting the hightest by Search Engine
		googleHighestValue = getGoogleHighestResult(googleHighestList);
		bingHighestValue = getBingHighestResult(bingHighestList);
		yandexHighestValue = getYandexHighestResult(yandexHighestList);
		System.out.println(googleHighestValue + " " + bingHighestValue + " " + yandexHighestValue);

		// The total highest
		totalHighestValue = getTotalHighest(googleHighestValue, bingHighestValue, yandexHighestValue);
		System.out.println(totalHighestValue);

	}

	private static Long getTotalHighest(Long googleHighestValue, Long bingHighestValue, Long yandexHighestValue) {
		return (googleHighestValue > bingHighestValue)
				? (googleHighestValue > yandexHighestValue ? googleHighestValue : yandexHighestValue)
				: (bingHighestValue > yandexHighestValue ? bingHighestValue : yandexHighestValue);
	}

	private static Long getGoogleHighestResult(List<Long> googleHighestList) {
		if (googleHighestList == null || googleHighestList.size() == 0) {
			throw new IllegalArgumentException("List has no value");
		}

		List<Long> sortedGoogleHighestList = new ArrayList<Long>(googleHighestList);

		Collections.sort(sortedGoogleHighestList);
		return sortedGoogleHighestList.get(sortedGoogleHighestList.size() - 1);
	}

	private static Long getBingHighestResult(List<Long> bingHighestList) {
		if (bingHighestList == null || bingHighestList.size() == 0) {
			throw new IllegalArgumentException("List has no value");
		}

		List<Long> sortedBingHighestList = new ArrayList<Long>(bingHighestList);

		Collections.sort(sortedBingHighestList);
		return sortedBingHighestList.get(sortedBingHighestList.size() - 1);
	}

	private static Long getYandexHighestResult(List<Long> yandexHighestList) {
		if (yandexHighestList == null || yandexHighestList.size() == 0) {
			throw new IllegalArgumentException("List has no value");
		}

		List<Long> sortedYandexHighestList = new ArrayList<Long>(yandexHighestList);

		Collections.sort(sortedYandexHighestList);
		return sortedYandexHighestList.get(sortedYandexHighestList.size() - 1);
	}

	public List<Response> searchEngineComparison(String request) {
		try {
			if (request == null || request.isEmpty()) {
				throw new IllegalArgumentException("Enter at lest two parameters");
			}

			String[] requestSplitted = request.split("\\s+");
			List<Response> collection = new ArrayList<Response>();

			for (int i = 0; i < requestSplitted.length; i++) {

				// Calling APIs
				APIConsumerService apiConsumerService = new APIConsumerServiceImpl();

				// Retrieving response from Google
				String response = apiConsumerService.compareSearchEngine(requestSplitted[i]);
				String googleResponseParsed = prettify(response);
				Gson obj = new Gson();
				SearchInformationModel searchInformationModel = obj.fromJson(googleResponseParsed,
						SearchInformationModel.class);
				Long googleResponseValue = searchInformationModel.getSearchInformation().getTotalResults();

				// Retrieving response from Bing
				SearchResults result = apiConsumerService.SearchWeb(requestSplitted[i]);
				String responseBing = prettify(result.getJsonResponse());
				GenericResponse responseParsed = obj.fromJson(responseBing, GenericResponse.class);
				Long bingResponseValue = responseParsed.getWebPages().getTotalEstimatedMatches();

				// Retrieving response from Yandex
				String yandexResponse = apiConsumerService.getYandexSearchEngineResult(requestSplitted[i]);
				JAXBContext jaxbContext = JAXBContext.newInstance(Yandexsearch.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(yandexResponse);
				Yandexsearch yandexResponseParsed = (Yandexsearch) jaxbUnmarshaller.unmarshal(reader);
				Long yandexResponseValue = null;
				if (yandexResponseParsed.getResponse().getFound().getPriority().equals("all")) {
					yandexResponseValue = yandexResponseParsed.getResponse().getFound().getValue();
				}

				// Adding values to collection
				Response apiResponse = new Response(googleResponseValue, bingResponseValue, yandexResponseValue);
				collection.add(apiResponse);
			}
			return collection;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public static String prettify(String json_text) {
		JsonObject json = JsonParser.parseString(json_text).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}

}
