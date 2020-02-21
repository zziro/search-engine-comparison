package com.cignium.searchengine;

import java.io.IOException;
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
		app.searchEngineComparison("Java PHP");
	}

	public static void searchEngineComparison(String request) {
		try {
			if (request == null || request.isEmpty()) {
				return;
			}

			String[] requestSplitted = request.split("\\s+"); // [Java, PHP]

			List<Response> collection = new LinkedList<Response>();

			for (int i = 0; i < requestSplitted.length; i++) {

				// Calling APIs
				APIConsumerService apiConsumerService = new APIConsumerServiceImpl();

				// Retrieving response from Google
				String response = apiConsumerService.compareSearchEngine(requestSplitted[i]);
				Gson obj = new Gson();
				SearchInformationModel objParsed = obj.fromJson(response, SearchInformationModel.class);
				Long googleResults = objParsed.getSearchInformation().getTotalResults();
				System.out.println(objParsed.getSearchInformation().getTotalResults());

				// Retrieving response from Bing
				SearchResults result = apiConsumerService.SearchWeb(requestSplitted[i]);
				String responseBing = prettify(result.getJsonResponse());
				GenericResponse responseParsed = obj.fromJson(responseBing, GenericResponse.class);
				Long microsoftResponse = responseParsed.getWebPages().getTotalEstimatedMatches();
				System.out.println(responseParsed.getWebPages().getTotalEstimatedMatches());

				Response apiResponse = new Response(googleResults, microsoftResponse);
				collection.add(apiResponse);
			}

			System.out.println(collection);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.exit(1);
		}
	}

	public static String prettify(String json_text) {
		JsonObject json = JsonParser.parseString(json_text).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}

}
