package com.tekton.searchengine.model;

public class GenericResult {
	private Long googleResult;
	private Long bingResult;
	private Long yandexResult;

	public GenericResult() {
	}

	public GenericResult(Long googleResult, Long bingResult) {
		super();
		this.googleResult = googleResult;
		this.bingResult = bingResult;
	}

	public GenericResult(Long googleResult, Long bingResult, Long yandexResult) {
		super();
		this.googleResult = googleResult;
		this.bingResult = bingResult;
		this.yandexResult = yandexResult;
	}

	public Long getGoogleResult() {
		return googleResult;
	}

	public void setGoogleResult(Long googleResult) {
		this.googleResult = googleResult;
	}

	public Long getBingResult() {
		return bingResult;
	}

	public void setBingResult(Long bingResult) {
		this.bingResult = bingResult;
	}

	public Long getYandexResult() {
		return yandexResult;
	}

	public void setYandexResult(Long yandexResult) {
		this.yandexResult = yandexResult;
	}

}
