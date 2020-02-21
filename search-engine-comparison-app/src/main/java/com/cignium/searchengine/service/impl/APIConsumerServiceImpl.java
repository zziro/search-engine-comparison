package com.cignium.searchengine.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class APIConsumerServiceImpl {

	public static void MyGETRequest() throws IOException {
		URL urlForGetRequest = new URL(
				"https://www.googleapis.com/customsearch/v1?key=AIzaSyBvZTE5aKlUXbCLz2Wy0GayLZEY4B42-X4&cx=017576662512468239146:omuauf_lfve&q=lectures&exactTerms=Java");
		String readLine = null;
		HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
		conection.setRequestMethod("GET");
		// conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample
		// here
		int responseCode = conection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
			StringBuffer response = new StringBuffer();
			while ((readLine = in.readLine()) != null) {
				response.append(readLine);
			}
			in.close();
			// print result
			System.out.println("JSON String Result " + response.toString());
			// GetAndPost.POSTRequest(response.toString());
		} else {
			System.out.println("GET NOT WORKED");
		}
	}

	public String getPropValues() throws IOException {

		String result = "";
		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			String propFileName = "application.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			String GOOGLE_API_KEY = prop.getProperty("api.google.key");

			System.out.println(GOOGLE_API_KEY);

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}

}
