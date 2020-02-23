package com.cignium.searchengine.model;

public class Response {
	private Long googleResponse;
	private Long bingResponse;
	private Long yandexResponse;

	public Response() {
	}

	public Response(Long googleResponse, Long bingResponse, Long yandexResponse) {
		super();
		this.googleResponse = googleResponse;
		this.bingResponse = bingResponse;
		this.yandexResponse = yandexResponse;
	}

	public Long getGoogleResponse() {
		return googleResponse;
	}

	public void setGoogleResponse(Long googleResponse) {
		this.googleResponse = googleResponse;
	}

	public Long getBingResponse() {
		return bingResponse;
	}

	public void setBingResponse(Long bingResponse) {
		this.bingResponse = bingResponse;
	}

	public Long getYandexResponse() {
		return yandexResponse;
	}

	public void setYandexResponse(Long yandexResponse) {
		this.yandexResponse = yandexResponse;
	}

}
