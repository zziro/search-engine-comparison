package com.cignium.searchengine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.cignium.searchengine.model.SearchInformationModel;
import com.google.gson.Gson;

public class App {

	private static final String API_GOOGLE_URL = "https://www.googleapis.com/customsearch/v1?key=AIzaSyBvZTE5aKlUXbCLz2Wy0GayLZEY4B42-X4&cx=017576662512468239146:omuauf_lfve&q=lectures&exactTerms=Java";

	public static void main(String[] args) throws IOException {
		String response = App.MyGETRequest();

		Gson obj = new Gson();
		SearchInformationModel objParsed = obj.fromJson(response, SearchInformationModel.class);
		System.out.println(objParsed.getSearchInformation().getTotalResults());

	}

	public static String MyGETRequest() throws IOException {

		URL urlForGetRequest = new URL(API_GOOGLE_URL);
		String readLine = null;
		HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
		conection.setRequestMethod("GET");
		int responseCode = conection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
			StringBuffer response = new StringBuffer();
			while ((readLine = in.readLine()) != null) {
				response.append(readLine);
			}
			in.close();
			return response.toString();

		} else {
			System.out.println("GET NOT WORKED");
			// return response.toString();
		}
		return null;
	}
}
