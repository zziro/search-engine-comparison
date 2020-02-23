package com.cignium.searchengine.model.google;

import java.io.Serializable;

public class GoogleGenericResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7947457643397082206L;

	private SearchInformation searchInformation;


	public GoogleGenericResponse() {
		
	}
	
	public SearchInformation getSearchInformation() {
		return searchInformation;
	}

	public void setSearchInformation(SearchInformation searchInformation) {
		this.searchInformation = searchInformation;
	}

}
