package com.cignium.searchengine;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.cignium.searchengine.model.GenericResult;
import com.cignium.searchengine.model.bing.BingGenericResponse;
import com.cignium.searchengine.model.bing.BingResult;
import com.cignium.searchengine.model.bing.SearchResults;
import com.cignium.searchengine.model.google.GoogleGenericResponse;
import com.cignium.searchengine.model.google.GoogleResult;
import com.cignium.searchengine.model.yandex.YandexResult;
import com.cignium.searchengine.model.yandex.Yandexsearch;
import com.cignium.searchengine.service.APIService;
import com.cignium.searchengine.service.impl.APIServiceImpl;
import com.cignium.searchengine.util.Constants;
import com.cignium.searchengine.util.HightestUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App {

	public static void main(String[] args) throws IOException {
		App app = new App();

		// List of parameters
		List<GenericResult> resultList = app.callAPI(args);

		// List of value by Search Engine
		List<Long> googleHighestList = new ArrayList<Long>();
		List<Long> bingHighestList = new ArrayList<Long>();
		List<Long> yandexHighestList = new ArrayList<Long>();

		GoogleResult googleResult = null;
		BingResult bingResult = null;
		YandexResult yandexResult = null;
		
		for (GenericResult genericResult : resultList) {

			System.out.println("Google = " + genericResult.getGoogleResult() + " Bing = " + genericResult.getBingResult() + " Yandex = " + genericResult.getYandexResult()); //Fixme: Use System.format()
					
			googleResult = new GoogleResult();
			googleResult.setGoogleValue(genericResult.getGoogleResult());

			bingResult = new BingResult();
			bingResult.setBingResult(genericResult.getBingResult());

			yandexResult = new YandexResult();
			yandexResult.setYandexValue(genericResult.getYandexResult());

			googleHighestList.add(googleResult.getGoogleValue());
			bingHighestList.add(bingResult.getBingResult());
			yandexHighestList.add(yandexResult.getYandexValue());

		}

		// Getting the highest by Search Engine
		Long googleHighestValue = HightestUtil.getHighestResult(googleHighestList);
		Long bingHighestValue = HightestUtil.getHighestResult(bingHighestList);
		Long yandexHighestValue = HightestUtil.getHighestResult(yandexHighestList);
		System.out.println("\nGoogle Winner = " + googleHighestValue + " Bing Winner = " + bingHighestValue + " Yandex Winner = " + yandexHighestValue);

		// The total highest
		Long totalHighestValue = HightestUtil.getTotalHighest(googleHighestValue, bingHighestValue, yandexHighestValue);
		System.out.println("\nTotal Winner = "+ totalHighestValue);

	}

	public List<GenericResult> callAPI(String[] args) {
		try {

			List<GenericResult> collectionResult = new ArrayList<GenericResult>();

			for (int i = 0; i < args.length; i++) {

				APIService apiService = new APIServiceImpl();

				// Retrieving results from Google Search Engine
				String googleResponse = apiService.getResponseFromGooleAPI(args[i]);
				String googleResponseParsed = prettify(googleResponse);
				Gson obj = new Gson();
				GoogleGenericResponse googleGenericResponse = obj.fromJson(googleResponseParsed, GoogleGenericResponse.class);
				Long googleResult = googleGenericResponse.getSearchInformation().getTotalResults();

				// Retrieving results from Bing Search Engine
				SearchResults bingResponse = apiService.getResponseFromBingAPI(args[i]);
				String bingResponseParsed = prettify(bingResponse.getJsonResponse());
				BingGenericResponse bingGenericResponse = obj.fromJson(bingResponseParsed, BingGenericResponse.class);
				Long bingResult = bingGenericResponse.getWebPages().getTotalEstimatedMatches();

				// Retrieving results Yandex Search Engine 
				String yandexResponse = apiService.getResponseFromYandexAPI(args[i]);
				JAXBContext jaxbContext = JAXBContext.newInstance(Yandexsearch.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(yandexResponse);
				Yandexsearch yandexResponseParsed = (Yandexsearch) jaxbUnmarshaller.unmarshal(reader);
				Long yandexResult = null;
				if (yandexResponseParsed.getResponse().getFound().getPriority().equals(Constants.FOUND_PRIORITY_ALL)) { 
					yandexResult = yandexResponseParsed.getResponse().getFound().getValue();
				}

				// Adding values to collection
				GenericResult genericResult = new GenericResult(googleResult, bingResult, yandexResult);
				collectionResult.add(genericResult);
			}
			return collectionResult;
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
