package com.cignium.searchengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.cignium.searchengine.model.GenericResponse;
import com.cignium.searchengine.model.Response;
import com.cignium.searchengine.model.SearchInformationModel;
import com.cignium.searchengine.model.SearchResults;
import com.cignium.searchengine.service.APIConsumerService;
import com.cignium.searchengine.service.impl.APIConsumerServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App {

	public static void main(String[] args) throws IOException {
		App app = new App();
		List<Response> collectionResponse = app.searchEngineComparison("Java PHP JavaScript");
		for (Response response : collectionResponse) {
			System.out.println(response.getGoogleResponse() + " " + response.getMicrosoftResponse());
		}

	}

	public List<Response> searchEngineComparison(String request) {
		try {
			if (request == null || request.isEmpty()) {
				return new LinkedList<>();
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
				SearchInformationModel searchInformationModel = obj.fromJson(googleResponseParsed, SearchInformationModel.class);
				Long googleResults = searchInformationModel.getSearchInformation().getTotalResults();

				// Retrieving response from Bing
				SearchResults result = apiConsumerService.SearchWeb(requestSplitted[i]);
				String responseBing = prettify(result.getJsonResponse());
				GenericResponse responseParsed = obj.fromJson(responseBing, GenericResponse.class);
				Long microsoftResponse = responseParsed.getWebPages().getTotalEstimatedMatches();

				Response apiResponse = new Response(googleResults, microsoftResponse);
				collection.add(apiResponse);
			}
			return collection;
		} catch (Exception e) {
			e.printStackTrace(System.out);
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
