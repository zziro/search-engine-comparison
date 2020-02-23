package com.cignium.searchengine.model.yandex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "yandexsearch")
@XmlAccessorType(XmlAccessType.NONE)
public class Yandexsearch {

	private Response response;

	public Yandexsearch() {
		// TODO Auto-generated constructor stub
	}

	public Response getResponse() {
		return response;
	}

	@XmlElement
	public void setResponse(Response response) {
		this.response = response;
	}

}
