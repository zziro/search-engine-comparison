package com.tekton.searchengine.model.yandex;

import javax.xml.bind.annotation.XmlElement;

public class Response {

	private Found found;

	public Response() {
		// TODO Auto-generated constructor stub
	}

	public Found getFound() {
		return found;
	}

	@XmlElement
	public void setFound(Found found) {
		this.found = found;
	}

}
