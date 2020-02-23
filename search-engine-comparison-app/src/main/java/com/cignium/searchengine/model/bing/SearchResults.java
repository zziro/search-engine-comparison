package com.cignium.searchengine.model.bing;

import java.util.HashMap;

public class SearchResults {
	private HashMap<String, String> relevantHeaders;
	private String jsonResponse;
	private WebPages webPages;
	
	public SearchResults() {
		// TODO Auto-generated constructor stub
	}

	public SearchResults(HashMap<String, String> headers, String json) {
		relevantHeaders = headers;
		jsonResponse = json;
	}

	public HashMap<String, String> getRelevantHeaders() {
		return relevantHeaders;
	}

	public void setRelevantHeaders(HashMap<String, String> relevantHeaders) {
		this.relevantHeaders = relevantHeaders;
	}

	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}

	public WebPages getWebPages() {
		return webPages;
	}

	public void setWebPages(WebPages webPages) {
		this.webPages = webPages;
	}

}
