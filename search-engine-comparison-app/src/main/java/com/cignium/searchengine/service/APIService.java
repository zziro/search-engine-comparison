package com.cignium.searchengine.service;

import com.cignium.searchengine.model.bing.SearchResults;

public interface APIService {

	String getResponseFromGooleAPI(String searchQuery);

	SearchResults getResponseFromBingAPI(String searchQuery) throws Exception;

	String getResponseFromYandexAPI(String searchQuery);

}
