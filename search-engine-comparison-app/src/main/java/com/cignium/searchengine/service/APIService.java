package com.cignium.searchengine.service;

import com.cignium.searchengine.model.bing.SearchResults;

public interface APIService {

	String getResponseFromGoogleAPI(String searchQuery);

	SearchResults getResponseFromBingAPI(String searchQuery);

	String getResponseFromYandexAPI(String searchQuery);

}
