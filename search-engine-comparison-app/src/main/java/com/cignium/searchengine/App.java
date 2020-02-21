package com.cignium.searchengine;

import java.io.IOException;

import com.cignium.searchengine.model.SearchInformationModel;
import com.cignium.searchengine.service.APIConsumerService;
import com.cignium.searchengine.service.impl.APIConsumerServiceImpl;
import com.google.gson.Gson;

public class App {

	public static void main(String[] args) throws IOException {

		APIConsumerService apiConsumerService = new APIConsumerServiceImpl();

		String response = apiConsumerService.compareSearchEngine("Java");

		Gson obj = new Gson();
		SearchInformationModel objParsed = obj.fromJson(response, SearchInformationModel.class);
		System.out.println(objParsed.getSearchInformation().getTotalResults());

	}

}
