package com.cignium.searchengine.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import com.cignium.searchengine.service.APIConsumerService;

public class APIConsumerServiceImpl implements APIConsumerService {

	public String compareSearchEngine(String request) {
		String API_URL;
		try {
			API_URL = buildUrl(request);
			URL urlForGetRequest = new URL(API_URL.concat(request));
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
				System.out.println("GET NOT WORKED"); // fix me: Throw Exception
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String buildUrl(String request) throws IOException {

		String result = "";
		String path = "";
		String cx = "";
		String q = "";
		String key = "";
		
		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			String propFileName = "application.properties"; //fix me

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			key = prop.getProperty("api.google.key");
			path = prop.getProperty("api.google.path");
			cx = prop.getProperty("api.google.cx");
			q = prop.getProperty("api.google.q");
			
			String url = new StringBuilder()
					.append(path)
					.append("?")
					.append("key=")
					.append(key)
					.append("&cx=")
					.append(cx)
					.append("&q=")
					.append(q)
					.append("&exactTerms")
					.append(request).toString();
			
			return url;
		} catch (Exception e) {
			System.out.println("Exception: " + e); //fix me
		} finally {
			inputStream.close();
		}
		return result;
	}

}
