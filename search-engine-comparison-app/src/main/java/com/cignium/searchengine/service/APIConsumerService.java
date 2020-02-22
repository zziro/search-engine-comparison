package com.cignium.searchengine.service;

import com.cignium.searchengine.model.SearchResults;

public interface APIConsumerService {

    String compareSearchEngine(String request);

    SearchResults SearchWeb(String searchQuery) throws Exception;

}
