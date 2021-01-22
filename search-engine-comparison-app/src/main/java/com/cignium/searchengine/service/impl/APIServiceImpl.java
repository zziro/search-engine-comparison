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

import com.cignium.searchengine.model.bing.SearchResults;
import com.cignium.searchengine.service.APIService;
import com.cignium.searchengine.util.Constants;

public class APIServiceImpl implements APIService {

	public String getResponseFromGoogleAPI(String searchQuery) {
		String googleApiUrl = null;
		if(searchQuery == null || searchQuery.isEmpty()){
			throw new IllegalArgumentException("Please enter a valid input.");
		}
		try {
			googleApiUrl = buildGoogleUrl(searchQuery);
			URL urlForGetRequest = new URL(googleApiUrl);
			String readLine = null;
			HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer response = new StringBuffer();
				while ((readLine = in.readLine()) != null) {
					response.append(readLine);
				}
				in.close();
				return response.toString();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String buildGoogleUrl(String searchQuery) throws IOException {

		String result = "";
		String path = "";
		String cx = "";
		String q = "";
		String key = "";

		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			String propFileName = Constants.CONFIG_FILE;

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			key = prop.getProperty("api.google.key");
			path = prop.getProperty("api.google.host");
			cx = prop.getProperty("api.google.cx");
			q = prop.getProperty("api.google.q");

			String url = new StringBuilder()
					.append(path).append("?")
					.append("key=").append(key)
					.append("&cx=").append(cx)
					.append("&q=").append(q)
					.append("&exactTerms=").append(URLEncoder.encode(searchQuery, "UTF-8")).toString();

			return url;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return result;
	}

	//@Override
	public SearchResults getResponseFromBingAPI(String searchQuery) {

		if (searchQuery == null || searchQuery.isEmpty()) {
			throw new IllegalArgumentException("Please enter a valid input.");
		}

		try {
			// Construct the URL.
			String bingApiUrl = buildBingUrl(searchQuery);
			URL url = new URL(bingApiUrl);

			// Open the connection.
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestProperty("Ocp-Apim-Subscription-Key", getKey());

			// Receive the JSON response body.
			InputStream stream = connection.getInputStream();
			String response = new Scanner(stream).useDelimiter("\\A").next();

			// Construct the result object.
			SearchResults results = new SearchResults(new HashMap<String, String>(), response);

			// Extract Bing-related HTTP headers.
			Map<String, List<String>> headers = connection.getHeaderFields();
			for (String header : headers.keySet()) {
				if (header == null)
					continue; // may have null key
				if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")) {
					results.getRelevantHeaders().put(header, headers.get(header).get(0));
				}
			}
			stream.close();
			return results;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;

	}

	private String buildBingUrl(String searchQuery) throws IOException {
		String result = "";
		String host = "";
		String path = "";

		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			String propFileName = Constants.CONFIG_FILE;

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			host = prop.getProperty("api.bing.host");
			path = prop.getProperty("api.bing.path");

			String url = new StringBuilder()
					.append(host)
					.append(path)
					.append("?q=").append(URLEncoder.encode(searchQuery, "UTF-8")).toString();

			return url;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return result;
	}

	private String getKey() throws IOException {
		String result = "";
		String key = "";

		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			String propFileName = Constants.CONFIG_FILE;

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			key = prop.getProperty("api.bing.key");

			String apiKey = new StringBuilder().append(key).toString();
			return apiKey;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			inputStream.close();
		}
		return result;
	}

	//@Override
	public String getResponseFromYandexAPI(String yandexRequest) {

		if(yandexRequest == null || yandexRequest.isEmpty()){
			throw new IllegalArgumentException("Please enter a valid input.");
		}
		String yandexApiUrl = null;
		try {
			yandexApiUrl = buildYandexUrl(yandexRequest);
			URL urlForGetRequest = new URL(yandexApiUrl);
			String readLine = null;
			HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer response = new StringBuffer();
				while ((readLine = in.readLine()) != null) {
					response.append(readLine);
				}
				in.close();
				return response.toString();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	private String buildYandexUrl(String searchQuery) throws IOException {

		String result = "";
		String key = "";
		String host = "";
		String user = "";
		String language = "";
		String sortby = "";
		String filter = "";
		String maxpassages = "";
		String groupby = "";
		String page = "";

		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			String propFileName = Constants.CONFIG_FILE;

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			key = prop.getProperty("api.yandex.key");
			host = prop.getProperty("api.yandex.host");
			user = prop.getProperty("api.yandex.user");
			language = prop.getProperty("api.yandex.l10n");
			sortby = prop.getProperty("api.yandex.sortby");
			filter = prop.getProperty("api.yandex.filter");
			maxpassages = prop.getProperty("api.yandex.maxpassages");
			groupby = prop.getProperty("api.yandex.groupby");
			page = prop.getProperty("api.yandex.page");

			String url = new StringBuilder()
					.append(host).append("?")
					.append("user=").append(user)
					.append("&key=").append(key)
					.append("&query=").append(URLEncoder.encode(searchQuery, "UTF-8"))
					.append("&l10n=").append(language)
					.append("&sortby=").append(sortby)
					.append("&filter=").append(filter)
					.append("&maxpassages=").append(maxpassages)
					.append("&groupby=").append(groupby)
					.append("&page=").append(page).toString();

			return url;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return result;
	}

}
