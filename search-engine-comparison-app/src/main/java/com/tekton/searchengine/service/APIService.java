package com.tekton.searchengine.service;

import com.tekton.searchengine.model.bing.SearchResults;

public interface APIService {

	String getResponseFromGoogleAPI(String searchQuery);

	SearchResults getResponseFromBingAPI(String searchQuery);

	String getResponseFromYandexAPI(String searchQuery);

}
