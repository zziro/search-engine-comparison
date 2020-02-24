package com.cignium.searchengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cignium.searchengine.model.GenericResult;
import com.cignium.searchengine.model.bing.BingGenericResponse;
import com.cignium.searchengine.model.bing.BingResult;
import com.cignium.searchengine.model.bing.SearchResults;
import com.cignium.searchengine.model.google.GoogleGenericResponse;
import com.cignium.searchengine.model.google.GoogleResult;
import com.cignium.searchengine.service.APIService;
import com.cignium.searchengine.service.impl.APIServiceImpl;
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
		//List<Long> yandexHighestList = new ArrayList<Long>();

		GoogleResult googleResult = null;
		BingResult bingResult = null;
		//YandexResult yandexResult = null;

		for (GenericResult genericResult : resultList) {

			googleResult = new GoogleResult();
			googleResult.setGoogleValue(genericResult.getGoogleResult());

			bingResult = new BingResult();
			bingResult.setBingResult(genericResult.getBingResult());

			//yandexResult = new YandexResult();
			//yandexResult.setYandexValue(genericResult.getYandexResult());

			googleHighestList.add(googleResult.getGoogleValue());
			bingHighestList.add(bingResult.getBingResult());
			//yandexHighestList.add(yandexResult.getYandexValue());

		}

		// Getting the highest by Search Engine
		Long googleHighestValue = HightestUtil.getHighestResult(googleHighestList);
		Long bingHighestValue = HightestUtil.getHighestResult(bingHighestList);
		//Long yandexHighestValue = HightestUtil.getHighestResult(yandexHighestList);

		System.out.println("\nGoogle Winner = " + googleHighestValue + " Bing Winner = " + bingHighestValue);
		//System.out.println("\nGoogle Winner = " + googleHighestValue + " Bing Winner = " + bingHighestValue + " Yandex Winner = " + yandexHighestValue);

		// The total highest
		Long totalHighestValue = HightestUtil.getTotalHighestWithTwoParameters(googleHighestValue, bingHighestValue);
        //Long totalHighestValue = HightestUtil.getTotalHighestWithThreeParameters(googleHighestValue, bingHighestValue, yandexHighestValue);
		System.out.println("\nTotal Winner = " + totalHighestValue);

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
				/*String yandexResponse = apiService.getResponseFromYandexAPI(args[i]);
				JAXBContext jaxbContext = JAXBContext.newInstance(Yandexsearch.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(yandexResponse);
				Yandexsearch yandexResponseParsed = (Yandexsearch) jaxbUnmarshaller.unmarshal(reader);
				Long yandexResult = null;
				if (yandexResponseParsed.getResponse().getFound().getPriority().equals(Constants.FOUND_PRIORITY_ALL)) { 
					yandexResult = yandexResponseParsed.getResponse().getFound().getValue();
				}*/

				System.out.println(i + 1 + ".- " + args[i] + " -> " + " Google=" + googleResult + " Bing=" + bingResult);
				//System.out.println(i + 1 + ".- " + args[i] + " -> " + " Google=" + googleResult + " Bing=" + bingResult+ " Yandex=" + yandexResult);
				
				// Adding values to collection
				GenericResult genericResult = new GenericResult(googleResult, bingResult);
				//GenericResult genericResult = new GenericResult(googleResult, bingResult, yandexResult);

				collectionResult.add(genericResult);
				//collectionResult.add(genericResult);
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
