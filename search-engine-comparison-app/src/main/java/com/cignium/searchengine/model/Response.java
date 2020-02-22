package com.cignium.searchengine.model;

public class Response {
    private Long googleResponse;
    private Long microsoftResponse;

    public Response() {
    }

    public Response(Long googleResponse, Long microsoftResponse) {
        this.googleResponse = googleResponse;
        this.microsoftResponse = microsoftResponse;
    }

    public Long getGoogleResponse() {
        return googleResponse;
    }

    public void setGoogleResponse(Long googleResponse) {
        this.googleResponse = googleResponse;
    }

    public Long getMicrosoftResponse() {
        return microsoftResponse;
    }

    public void setMicrosoftResponse(Long microsoftResponse) {
        this.microsoftResponse = microsoftResponse;
    }
}
