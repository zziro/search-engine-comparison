package com.cignium.searchengine;

import java.io.IOException;
import java.lang.reflect.GenericSignatureFormatError;

import com.cignium.searchengine.model.GenericResponse;
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

		APIConsumerService apiConsumerService = new APIConsumerServiceImpl();

		// Retrieving respose from Bing
		String response = apiConsumerService.compareSearchEngine("Java");

		Gson obj = new Gson();
		SearchInformationModel objParsed = obj.fromJson(response, SearchInformationModel.class);
		// System.out.println("Google: " +
		// objParsed.getSearchInformation().getTotalResults());

		// Retrieving response from Bing

		try {

			SearchResults result = apiConsumerService.SearchWeb("Java");
			// String resultBing = obj.fromJson(result, SearchResults.class);
			String responseBing = prettify(result.getJsonResponse());
			GenericResponse responseParsed = obj.fromJson(responseBing, GenericResponse.class);
			
			System.out.println(responseParsed.getWebPages().getTotalEstimatedMatches());

			// for (String header : result.getRelevantHeaders().keySet())

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.exit(1);
		}

	}

	// Pretty-printer for JSON; uses GSON parser to parse and re-serialize
	public static String prettify(String json_text) {
		JsonObject json = JsonParser.parseString(json_text).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}

}
