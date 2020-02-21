package com.cignium.searchengine.model;

import java.io.Serializable;

public class SearchInformationModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7947457643397082206L;

	private SearchInformation searchInformation;


	public SearchInformationModel() {
		
	}
	
	public SearchInformation getSearchInformation() {
		return searchInformation;
	}

	public void setSearchInformation(SearchInformation searchInformation) {
		this.searchInformation = searchInformation;
	}

}
