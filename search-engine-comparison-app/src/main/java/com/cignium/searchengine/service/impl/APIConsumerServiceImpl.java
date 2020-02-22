package com.cignium.searchengine.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.cignium.searchengine.model.SearchResults;
import com.cignium.searchengine.service.APIConsumerService;

public class APIConsumerServiceImpl implements APIConsumerService {
	
	// Enter a valid subscription key.
	static String subscriptionKey = "b76a1ac8c2624fdb9435a81e87f22ae8";
	static String host = "https://api.cognitive.microsoft.com";
	static String path = "/bing/v7.0/search";
	static String searchTerm = "Microsoft Cognitive Services";

	public String compareSearchEngine(String request) {
		String API_URL;
		try {
			API_URL = buildUrl(request);
			URL urlForGetRequest = new URL(API_URL);
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
			String propFileName = "application.properties"; 

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
					.append("&exactTerms=")
					.append(request).toString();
			
			return url;
		} catch (Exception e) {
			System.out.println("Exception: " + e); 
		} finally {
			inputStream.close();
		}
		return result;
	}

	@Override
	public SearchResults SearchWeb(String searchQuery) throws Exception {
		// Construct the URL.
	    URL url = new URL(host + path + "?q=" +  URLEncoder.encode(searchQuery, "UTF-8"));

	    // Open the connection.
	    HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
	    connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

	    // Receive the JSON response body.
	    InputStream stream = connection.getInputStream();
	    String response = new Scanner(stream).useDelimiter("\\A").next();

	    // Construct the result object.
	    SearchResults results = new SearchResults(new HashMap<String, String>(), response);

	    // Extract Bing-related HTTP headers.
	    Map<String, List<String>> headers = connection.getHeaderFields();
	    for (String header : headers.keySet()) {
	        if (header == null) continue;      // may have null key
	        if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")){
	            results.getRelevantHeaders().put(header, headers.get(header).get(0));
	        }
	    }
	    stream.close();
	    return results;
	}

}
