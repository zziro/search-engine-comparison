package com.cignium.searchengine.model;

import java.io.Serializable;

public class SearchInformationModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7947457643397082206L;

	private String kind;
	private SearchInformation searchInformation;


	public SearchInformationModel() {
		// TODO Auto-generated constructor stub
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public SearchInformation getSearchInformation() {
		return searchInformation;
	}

	public void setSearchInformation(SearchInformation searchInformation) {
		this.searchInformation = searchInformation;
	}

}
