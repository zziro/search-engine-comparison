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

	static String subscriptionKey = "b76a1ac8c2624fdb9435a81e87f22ae8";
	static String host = "https://api.cognitive.microsoft.com";
	static String path = "/bing/v7.0/search";
	static String searchTerm = "Microsoft Cognitive Services";
	
	//Yandex URL
	static String yandex_url = "https://yandex.com/search/xml?user=zziro.123@gmail.com&key=03.1031818419:3321e9ba03126a4eb4da3a94c4b497a1&query=PHP&l10n=en&sortby=tm.order%3Dascending&filter=strict&maxpassages=5&groupby=attr%3Dd.mode%3Ddeep.groups-on-page%3D10.docs-in-group%3D3&page=10";

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

			String url = new StringBuilder().append(path).append("?").append("key=").append(key).append("&cx=")
					.append(cx).append("&q=").append(q).append("&exactTerms=").append(request).toString();

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
		URL url = new URL(host + path + "?q=" + URLEncoder.encode(searchQuery, "UTF-8"));

		// Open the connection.
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

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
	}

	@Override
	public String getYandexSearchEngineResult(String yandexRequest) {
//		try {
//			URL urlForGetRequest = new URL(yandex_url);
//			String readLine = null;
//			HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
//			conection.setRequestMethod("GET");
//			int responseCode = conection.getResponseCode();
//			if (responseCode == HttpURLConnection.HTTP_OK) {
//				BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
//				StringBuffer response = new StringBuffer();
//				while ((readLine = in.readLine()) != null) {
//					response.append(readLine);
//				}
//				in.close();
//				return response.toString();
//
//			} else {
//				System.out.println("GET NOT WORKED");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
		
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
				"<yandexsearch version=\"1.0\">\r\n" + 
				"  <request>\r\n" + 
				"    <query>\r\n" + 
				"      Java\r\n" + 
				"    </query>\r\n" + 
				"    <page>\r\n" + 
				"      10\r\n" + 
				"    </page>\r\n" + 
				"    <sortby order=\"ascending\" priority=\"no\">\r\n" + 
				"      tm\r\n" + 
				"    </sortby>\r\n" + 
				"    <maxpassages>\r\n" + 
				"      5\r\n" + 
				"    </maxpassages>\r\n" + 
				"    <groupings>\r\n" + 
				"      <groupby attr=\"d\" mode=\"deep\" groups-on-page=\"10\" docs-in-group=\"3\" curcateg=\"-1\" />\r\n" + 
				"    </groupings>\r\n" + 
				"  </request>\r\n" + 
				"  <response date=\"20200223T051050\">\r\n" + 
				"    <reqid>\r\n" + 
				"      1582434650111984-375089441481204123306042-vla1-0241-XML-p10\r\n" + 
				"    </reqid>\r\n" + 
				"    <found priority=\"phrase\">\r\n" + 
				"      14615631\r\n" + 
				"    </found>\r\n" + 
				"    <found priority=\"strict\">\r\n" + 
				"      14615631\r\n" + 
				"    </found>\r\n" + 
				"    <found priority=\"all\">\r\n" + 
				"      14615631\r\n" + 
				"    </found>\r\n" + 
				"    <found-human>\r\n" + 
				"      15 mln. answers found\r\n" + 
				"      </found-human>\r\n" + 
				"    <results>\r\n" + 
				"      <grouping attr=\"d\" mode=\"deep\" groups-on-page=\"10\" docs-in-group=\"3\" curcateg=\"-1\">\r\n" + 
				"        <found priority=\"phrase\">\r\n" + 
				"          1710\r\n" + 
				"        </found>\r\n" + 
				"        <found priority=\"strict\">\r\n" + 
				"          1710\r\n" + 
				"        </found>\r\n" + 
				"        <found priority=\"all\">\r\n" + 
				"          1710\r\n" + 
				"        </found>\r\n" + 
				"        <found-docs priority=\"phrase\">\r\n" + 
				"          5574351\r\n" + 
				"          </found-docs>\r\n" + 
				"        <found-docs priority=\"strict\">\r\n" + 
				"          5574351\r\n" + 
				"          </found-docs>\r\n" + 
				"        <found-docs priority=\"all\">\r\n" + 
				"          5574351\r\n" + 
				"          </found-docs>\r\n" + 
				"        <found-docs-human>\r\n" + 
				"          found 6 mln. answers\r\n" + 
				"          </found-docs-human>\r\n" + 
				"        <page first=\"101\" last=\"110\">\r\n" + 
				"          10\r\n" + 
				"        </page>\r\n" + 
				"        <group>\r\n" + 
				"          <categ attr=\"d\" name=\"web-shpargalka.ru\" />\r\n" + 
				"          <doccount>\r\n" + 
				"            1\r\n" + 
				"          </doccount>\r\n" + 
				"          <relevance />\r\n" + 
				"          <doc id=\"ZD1360AED18F1658B\">\r\n" + 
				"            <relevance />\r\n" + 
				"            <url>\r\n" + 
				"              https://web-shpargalka.ru/error-java-installer-chto-delat.php\r\n" + 
				"            </url>\r\n" + 
				"            <domain>\r\n" + 
				"              web-shpargalka.ru\r\n" + 
				"            </domain>\r\n" + 
				"            <title>\r\n" + 
				"              Error \r\n" + 
				"              <hlword>\r\n" + 
				"                java\r\n" + 
				"              </hlword>\r\n" + 
				"              installer что делать - Вэб-шпаргалка для интернет...\r\n" + 
				"            </title>\r\n" + 
				"            <modtime>\r\n" + 
				"              20191220T184449\r\n" + 
				"            </modtime>\r\n" + 
				"            <size>\r\n" + 
				"              3751\r\n" + 
				"            </size>\r\n" + 
				"            <charset>\r\n" + 
				"              utf-8\r\n" + 
				"            </charset>\r\n" + 
				"            <passages>\r\n" + 
				"              <passage>\r\n" + 
				"                Не устанавливается \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                на Windows 7, 8 или 10 — произошла ошибка.\r\n" + 
				"              </passage>\r\n" + 
				"              <passage>\r\n" + 
				"                Программное обеспечение \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                используется как платформа для очень многих программ под Windows и...\r\n" + 
				"              </passage>\r\n" + 
				"            </passages>\r\n" + 
				"            <properties>\r\n" + 
				"              <_PassagesType>\r\n" + 
				"                0\r\n" + 
				"              </_PassagesType>\r\n" + 
				"              <lang>\r\n" + 
				"                ru\r\n" + 
				"              </lang>\r\n" + 
				"            </properties>\r\n" + 
				"            <mime-type>\r\n" + 
				"              text/html\r\n" + 
				"              </mime-type>\r\n" + 
				"            <saved-copy-url>\r\n" + 
				"              https://yandexwebcache.net/yandbtm?lang=ru&amp;fmode=inject&amp;tm=1582434650&amp;tld=com&amp;la=1581932288&amp;text=Java&amp;url=https%3A%2F%2Fweb-shpargalka.ru%2Ferror-java-installer-chto-delat.php&amp;l10n=en&amp;mime=html&amp;sign=7976fef00f8398eca460230aa35104c5&amp;keyno=0\r\n" + 
				"              </saved-copy-url>\r\n" + 
				"          </doc>\r\n" + 
				"        </group>\r\n" + 
				"        <group>\r\n" + 
				"          <categ attr=\"d\" name=\"howtolearn.ru\" />\r\n" + 
				"          <doccount>\r\n" + 
				"            1\r\n" + 
				"          </doccount>\r\n" + 
				"          <relevance />\r\n" + 
				"          <doc id=\"Z2CF34E6499A90ABB\">\r\n" + 
				"            <relevance />\r\n" + 
				"            <url>\r\n" + 
				"              https://howtolearn.ru/online-kursy/java.html\r\n" + 
				"            </url>\r\n" + 
				"            <domain>\r\n" + 
				"              howtolearn.ru\r\n" + 
				"            </domain>\r\n" + 
				"            <title>\r\n" + 
				"              15 лучших платных и бесплатных онлайн-курсов по \r\n" + 
				"              <hlword>\r\n" + 
				"                Java\r\n" + 
				"              </hlword>\r\n" + 
				"              ...\r\n" + 
				"            </title>\r\n" + 
				"            <modtime>\r\n" + 
				"              20191217T210205\r\n" + 
				"            </modtime>\r\n" + 
				"            <size>\r\n" + 
				"              3060\r\n" + 
				"            </size>\r\n" + 
				"            <charset>\r\n" + 
				"              utf-8\r\n" + 
				"            </charset>\r\n" + 
				"            <passages>\r\n" + 
				"              <passage>\r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                — это язык программирования, на котором написано большинство мобильных\r\n" + 
				"              </passage>\r\n" + 
				"              <passage>\r\n" + 
				"                Курс посвящен изучению основ \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                , популярного объектно-ориентированного языка...\r\n" + 
				"              </passage>\r\n" + 
				"            </passages>\r\n" + 
				"            <properties>\r\n" + 
				"              <_PassagesType>\r\n" + 
				"                0\r\n" + 
				"              </_PassagesType>\r\n" + 
				"              <lang>\r\n" + 
				"                ru\r\n" + 
				"              </lang>\r\n" + 
				"            </properties>\r\n" + 
				"            <mime-type>\r\n" + 
				"              text/html\r\n" + 
				"              </mime-type>\r\n" + 
				"            <saved-copy-url>\r\n" + 
				"              https://yandexwebcache.net/yandbtm?lang=ru&amp;fmode=inject&amp;tm=1582434650&amp;tld=com&amp;la=1582206720&amp;text=Java&amp;url=https%3A%2F%2Fhowtolearn.ru%2Fonline-kursy%2Fjava.html&amp;l10n=en&amp;mime=html&amp;sign=ee6360090c7ce17b5b10a4fc7a05157d&amp;keyno=0\r\n" + 
				"              </saved-copy-url>\r\n" + 
				"          </doc>\r\n" + 
				"        </group>\r\n" + 
				"        <group>\r\n" + 
				"          <categ attr=\"d\" name=\"geeksforgeeks.org\" />\r\n" + 
				"          <doccount>\r\n" + 
				"            25\r\n" + 
				"          </doccount>\r\n" + 
				"          <relevance />\r\n" + 
				"          <doc id=\"ZFD5C5E986D6E186D\">\r\n" + 
				"            <relevance />\r\n" + 
				"            <url>\r\n" + 
				"              https://www.geeksforgeeks.org/system-out-println-in-java/\r\n" + 
				"            </url>\r\n" + 
				"            <domain>\r\n" + 
				"              www.geeksforgeeks.org\r\n" + 
				"            </domain>\r\n" + 
				"            <title>\r\n" + 
				"              System.out.println in \r\n" + 
				"              <hlword>\r\n" + 
				"                Java\r\n" + 
				"              </hlword>\r\n" + 
				"              - GeeksforGeeks\r\n" + 
				"            </title>\r\n" + 
				"            <modtime>\r\n" + 
				"              20191129T160522\r\n" + 
				"            </modtime>\r\n" + 
				"            <size>\r\n" + 
				"              4015\r\n" + 
				"            </size>\r\n" + 
				"            <charset>\r\n" + 
				"              utf-8\r\n" + 
				"            </charset>\r\n" + 
				"            <passages>\r\n" + 
				"              <passage>\r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                System.out.println() is used to print an argument that is passed to it. The statement can be broken into 3 parts which can be understood separately as\r\n" + 
				"              </passage>\r\n" + 
				"            </passages>\r\n" + 
				"            <properties>\r\n" + 
				"              <_PassagesType>\r\n" + 
				"                0\r\n" + 
				"              </_PassagesType>\r\n" + 
				"              <lang>\r\n" + 
				"                en\r\n" + 
				"              </lang>\r\n" + 
				"            </properties>\r\n" + 
				"            <mime-type>\r\n" + 
				"              text/html\r\n" + 
				"              </mime-type>\r\n" + 
				"            <saved-copy-url>\r\n" + 
				"              https://yandexwebcache.net/yandbtm?lang=en&amp;fmode=inject&amp;tm=1582434650&amp;tld=com&amp;la=1582195584&amp;text=Java&amp;url=https%3A%2F%2Fwww.geeksforgeeks.org%2Fsystem-out-println-in-java%2F&amp;l10n=en&amp;mime=html&amp;sign=3a4a37fa025b3cde96237746fffb9918&amp;keyno=0\r\n" + 
				"              </saved-copy-url>\r\n" + 
				"          </doc>\r\n" + 
				"        </group>\r\n" + 
				"        <group>\r\n" + 
				"          <categ attr=\"d\" name=\"hr-vector.com\" />\r\n" + 
				"          <doccount>\r\n" + 
				"            6\r\n" + 
				"          </doccount>\r\n" + 
				"          <relevance />\r\n" + 
				"          <doc id=\"Z126B91F8CAAFEE95\">\r\n" + 
				"            <relevance />\r\n" + 
				"            <url>\r\n" + 
				"              https://hr-vector.com/java/klassy-poji-pojo-i-java-beans\r\n" + 
				"            </url>\r\n" + 
				"            <domain>\r\n" + 
				"              hr-vector.com\r\n" + 
				"            </domain>\r\n" + 
				"            <title>\r\n" + 
				"              Для чего нужны классы POJI, POJO и \r\n" + 
				"              <hlword>\r\n" + 
				"                Java\r\n" + 
				"              </hlword>\r\n" + 
				"              Beans в \r\n" + 
				"              <hlword>\r\n" + 
				"                Java\r\n" + 
				"              </hlword>\r\n" + 
				"              ?\r\n" + 
				"            </title>\r\n" + 
				"            <modtime>\r\n" + 
				"              20191127T212054\r\n" + 
				"            </modtime>\r\n" + 
				"            <size>\r\n" + 
				"              798\r\n" + 
				"            </size>\r\n" + 
				"            <charset>\r\n" + 
				"              utf-8\r\n" + 
				"            </charset>\r\n" + 
				"            <passages>\r\n" + 
				"              <passage>\r\n" + 
				"                Является аббревиатурой от Plain Old \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                Interface, который соответствует стандартному интерфейсу \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                , что означает, что эти интерфейсы находятся в контексте предоставления...\r\n" + 
				"              </passage>\r\n" + 
				"            </passages>\r\n" + 
				"            <properties>\r\n" + 
				"              <_PassagesType>\r\n" + 
				"                0\r\n" + 
				"              </_PassagesType>\r\n" + 
				"              <lang>\r\n" + 
				"                ru\r\n" + 
				"              </lang>\r\n" + 
				"            </properties>\r\n" + 
				"            <mime-type>\r\n" + 
				"              text/html\r\n" + 
				"              </mime-type>\r\n" + 
				"            <saved-copy-url>\r\n" + 
				"              https://yandexwebcache.net/yandbtm?lang=ru&amp;fmode=inject&amp;tm=1582434650&amp;tld=com&amp;la=1582047360&amp;text=Java&amp;url=https%3A%2F%2Fhr-vector.com%2Fjava%2Fklassy-poji-pojo-i-java-beans&amp;l10n=en&amp;mime=html&amp;sign=a33e8c43dc48275db80e225088d16f28&amp;keyno=0\r\n" + 
				"              </saved-copy-url>\r\n" + 
				"          </doc>\r\n" + 
				"        </group>\r\n" + 
				"        <group>\r\n" + 
				"          <categ attr=\"d\" name=\"examclouds.com\" />\r\n" + 
				"          <doccount>\r\n" + 
				"            16\r\n" + 
				"          </doccount>\r\n" + 
				"          <relevance />\r\n" + 
				"          <doc id=\"ZF4048687D447D65D\">\r\n" + 
				"            <relevance />\r\n" + 
				"            <url>\r\n" + 
				"              https://www.examclouds.com/ru/java/java-core-russian/static-block\r\n" + 
				"            </url>\r\n" + 
				"            <domain>\r\n" + 
				"              www.examclouds.com\r\n" + 
				"            </domain>\r\n" + 
				"            <title>\r\n" + 
				"              Статический блок \r\n" + 
				"              <hlword>\r\n" + 
				"                Java\r\n" + 
				"              </hlword>\r\n" + 
				"              - \r\n" + 
				"              <hlword>\r\n" + 
				"                Java\r\n" + 
				"              </hlword>\r\n" + 
				"              программирование | ExamClouds\r\n" + 
				"            </title>\r\n" + 
				"            <modtime>\r\n" + 
				"              20191121T213924\r\n" + 
				"            </modtime>\r\n" + 
				"            <size>\r\n" + 
				"              335\r\n" + 
				"            </size>\r\n" + 
				"            <charset>\r\n" + 
				"              utf-8\r\n" + 
				"            </charset>\r\n" + 
				"            <passages>\r\n" + 
				"              <passage>\r\n" + 
				"                Статический блок \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                . Если для инициализации статических переменных требуется произвести вычисления, то для этой цели достаточно объявить статический блок \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                , который...\r\n" + 
				"              </passage>\r\n" + 
				"            </passages>\r\n" + 
				"            <properties>\r\n" + 
				"              <_PassagesType>\r\n" + 
				"                0\r\n" + 
				"              </_PassagesType>\r\n" + 
				"              <lang>\r\n" + 
				"                ru\r\n" + 
				"              </lang>\r\n" + 
				"            </properties>\r\n" + 
				"            <mime-type>\r\n" + 
				"              text/html\r\n" + 
				"              </mime-type>\r\n" + 
				"            <saved-copy-url>\r\n" + 
				"              https://yandexwebcache.net/yandbtm?lang=ru&amp;fmode=inject&amp;tm=1582434650&amp;tld=com&amp;la=1582035456&amp;text=Java&amp;url=https%3A%2F%2Fwww.examclouds.com%2Fru%2Fjava%2Fjava-core-russian%2Fstatic-block&amp;l10n=en&amp;mime=html&amp;sign=2b8142d1abdeaca1ce84aa710757a4b4&amp;keyno=0\r\n" + 
				"              </saved-copy-url>\r\n" + 
				"          </doc>\r\n" + 
				"        </group>\r\n" + 
				"        <group>\r\n" + 
				"          <categ attr=\"d\" name=\"javacodeexamples.com\" />\r\n" + 
				"          <doccount>\r\n" + 
				"            4\r\n" + 
				"          </doccount>\r\n" + 
				"          <relevance />\r\n" + 
				"          <doc id=\"ZC0FE78A1589262A8\">\r\n" + 
				"            <relevance />\r\n" + 
				"            <url>\r\n" + 
				"              https://www.javacodeexamples.com/java-hashmap-foreach-for-loop-example/2329\r\n" + 
				"            </url>\r\n" + 
				"            <domain>\r\n" + 
				"              www.javacodeexamples.com\r\n" + 
				"            </domain>\r\n" + 
				"            <title>\r\n" + 
				"              <hlword>\r\n" + 
				"                Java\r\n" + 
				"              </hlword>\r\n" + 
				"              HashMap forEach for loop example - \r\n" + 
				"              <hlword>\r\n" + 
				"                Java\r\n" + 
				"              </hlword>\r\n" + 
				"              Code Examples\r\n" + 
				"            </title>\r\n" + 
				"            <modtime>\r\n" + 
				"              20191121T114349\r\n" + 
				"            </modtime>\r\n" + 
				"            <size>\r\n" + 
				"              1588\r\n" + 
				"            </size>\r\n" + 
				"            <charset>\r\n" + 
				"              utf-8\r\n" + 
				"            </charset>\r\n" + 
				"            <passages>\r\n" + 
				"              <passage>\r\n" + 
				"                23. 24. import \r\n" + 
				"                <hlword>\r\n" + 
				"                  java\r\n" + 
				"                </hlword>\r\n" + 
				"                .util.HashMap; public class HashMapForEachExample.\r\n" + 
				"              </passage>\r\n" + 
				"              <passage>\r\n" + 
				"                If you are using \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                8 or later version, you can use the forEach to iterate over all the mappings of the HashMap as given below.\r\n" + 
				"              </passage>\r\n" + 
				"            </passages>\r\n" + 
				"            <properties>\r\n" + 
				"              <_PassagesType>\r\n" + 
				"                0\r\n" + 
				"              </_PassagesType>\r\n" + 
				"              <lang>\r\n" + 
				"                en\r\n" + 
				"              </lang>\r\n" + 
				"            </properties>\r\n" + 
				"            <mime-type>\r\n" + 
				"              text/html\r\n" + 
				"              </mime-type>\r\n" + 
				"            <saved-copy-url>\r\n" + 
				"              https://yandexwebcache.net/yandbtm?lang=en&amp;fmode=inject&amp;tm=1582434650&amp;tld=com&amp;la=1582134912&amp;text=Java&amp;url=https%3A%2F%2Fwww.javacodeexamples.com%2Fjava-hashmap-foreach-for-loop-example%2F2329&amp;l10n=en&amp;mime=html&amp;sign=3867b22564920a29f89b8e4ab4ff900b&amp;keyno=0\r\n" + 
				"              </saved-copy-url>\r\n" + 
				"          </doc>\r\n" + 
				"        </group>\r\n" + 
				"        <group>\r\n" + 
				"          <categ attr=\"d\" name=\"hexlet.io\" />\r\n" + 
				"          <doccount>\r\n" + 
				"            3\r\n" + 
				"          </doccount>\r\n" + 
				"          <relevance />\r\n" + 
				"          <doc id=\"Z1F9DF63ECB3930BC\">\r\n" + 
				"            <relevance />\r\n" + 
				"            <url>\r\n" + 
				"              https://ru.hexlet.io/blog/posts/yazyk-programmirovaniya-java-osobennosti-populyarnost-situatsiya-na-rynke-truda\r\n" + 
				"            </url>\r\n" + 
				"            <domain>\r\n" + 
				"              ru.hexlet.io\r\n" + 
				"            </domain>\r\n" + 
				"            <title>\r\n" + 
				"              Язык программирования \r\n" + 
				"              <hlword>\r\n" + 
				"                Java\r\n" + 
				"              </hlword>\r\n" + 
				"              : особенности, популярность,..\r\n" + 
				"            </title>\r\n" + 
				"            <modtime>\r\n" + 
				"              20191118T093016\r\n" + 
				"            </modtime>\r\n" + 
				"            <size>\r\n" + 
				"              3519\r\n" + 
				"            </size>\r\n" + 
				"            <charset>\r\n" + 
				"              utf-8\r\n" + 
				"            </charset>\r\n" + 
				"            <passages>\r\n" + 
				"              <passage>\r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                — язык программирования общего назначения.\r\n" + 
				"              </passage>\r\n" + 
				"              <passage>\r\n" + 
				"                Это маркетинговое сообщение сложно проверить. Тем не менее \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                широко используется и входит в число самых востребованных...\r\n" + 
				"              </passage>\r\n" + 
				"            </passages>\r\n" + 
				"            <properties>\r\n" + 
				"              <_PassagesType>\r\n" + 
				"                0\r\n" + 
				"              </_PassagesType>\r\n" + 
				"              <lang>\r\n" + 
				"                ru\r\n" + 
				"              </lang>\r\n" + 
				"            </properties>\r\n" + 
				"            <mime-type>\r\n" + 
				"              text/html\r\n" + 
				"              </mime-type>\r\n" + 
				"            <saved-copy-url>\r\n" + 
				"              https://yandexwebcache.net/yandbtm?lang=ru&amp;fmode=inject&amp;tm=1582434650&amp;tld=com&amp;la=1582179200&amp;text=Java&amp;url=https%3A%2F%2Fru.hexlet.io%2Fblog%2Fposts%2Fyazyk-programmirovaniya-java-osobennosti-populyarnost-situatsiya-na-rynke-truda&amp;l10n=en&amp;mime=html&amp;sign=d2a6f49da4fc2ae973b60fabb9d08755&amp;keyno=0\r\n" + 
				"              </saved-copy-url>\r\n" + 
				"          </doc>\r\n" + 
				"        </group>\r\n" + 
				"        <group>\r\n" + 
				"          <categ attr=\"d\" name=\"geekbrains.ru\" />\r\n" + 
				"          <doccount>\r\n" + 
				"            5\r\n" + 
				"          </doccount>\r\n" + 
				"          <relevance />\r\n" + 
				"          <doc id=\"Z4F992C3505BFAD5F\">\r\n" + 
				"            <relevance />\r\n" + 
				"            <url>\r\n" + 
				"              https://geekbrains.ru/posts/java_quick_start_part1\r\n" + 
				"            </url>\r\n" + 
				"            <domain>\r\n" + 
				"              geekbrains.ru\r\n" + 
				"            </domain>\r\n" + 
				"            <title>\r\n" + 
				"              Быстрый старт с \r\n" + 
				"              <hlword>\r\n" + 
				"                Java\r\n" + 
				"              </hlword>\r\n" + 
				"              : от знакомства до вашей первой игры\r\n" + 
				"            </title>\r\n" + 
				"            <modtime>\r\n" + 
				"              20191115T184412\r\n" + 
				"            </modtime>\r\n" + 
				"            <size>\r\n" + 
				"              3019\r\n" + 
				"            </size>\r\n" + 
				"            <charset>\r\n" + 
				"              utf-8\r\n" + 
				"            </charset>\r\n" + 
				"            <passages>\r\n" + 
				"              <passage>\r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                -программы состоят из классов — это «кирпичики» приложения.\r\n" + 
				"              </passage>\r\n" + 
				"              <passage>\r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                поставляется с набором packages (пакетов). Они содержат скомпилированные классы, сгруппированные по...\r\n" + 
				"              </passage>\r\n" + 
				"            </passages>\r\n" + 
				"            <properties>\r\n" + 
				"              <_PassagesType>\r\n" + 
				"                0\r\n" + 
				"              </_PassagesType>\r\n" + 
				"              <lang>\r\n" + 
				"                ru\r\n" + 
				"              </lang>\r\n" + 
				"            </properties>\r\n" + 
				"            <mime-type>\r\n" + 
				"              text/html\r\n" + 
				"              </mime-type>\r\n" + 
				"            <saved-copy-url>\r\n" + 
				"              https://yandexwebcache.net/yandbtm?lang=ru&amp;fmode=inject&amp;tm=1582434650&amp;tld=com&amp;la=1582208128&amp;text=Java&amp;url=https%3A%2F%2Fgeekbrains.ru%2Fposts%2Fjava_quick_start_part1&amp;l10n=en&amp;mime=html&amp;sign=97cfd2d44e8cd2a6be8957cd762ca051&amp;keyno=0\r\n" + 
				"              </saved-copy-url>\r\n" + 
				"          </doc>\r\n" + 
				"        </group>\r\n" + 
				"        <group>\r\n" + 
				"          <categ attr=\"d\" name=\"w3schools.com\" />\r\n" + 
				"          <doccount>\r\n" + 
				"            29\r\n" + 
				"          </doccount>\r\n" + 
				"          <relevance />\r\n" + 
				"          <doc id=\"Z053DC0B95774A72D\">\r\n" + 
				"            <relevance />\r\n" + 
				"            <url>\r\n" + 
				"              https://www.w3schools.com/java/java_files_read.asp\r\n" + 
				"            </url>\r\n" + 
				"            <domain>\r\n" + 
				"              www.w3schools.com\r\n" + 
				"            </domain>\r\n" + 
				"            <title>\r\n" + 
				"              <hlword>\r\n" + 
				"                Java\r\n" + 
				"              </hlword>\r\n" + 
				"              Read Files\r\n" + 
				"            </title>\r\n" + 
				"            <modtime>\r\n" + 
				"              20191114T191217\r\n" + 
				"            </modtime>\r\n" + 
				"            <size>\r\n" + 
				"              2218\r\n" + 
				"            </size>\r\n" + 
				"            <charset>\r\n" + 
				"              utf-8\r\n" + 
				"            </charset>\r\n" + 
				"            <passages>\r\n" + 
				"              <passage>\r\n" + 
				"                Files in \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                might be tricky, but it is fun enough!\r\n" + 
				"              </passage>\r\n" + 
				"              <passage>\r\n" + 
				"                Note: There are many available classes in the \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                API that can be used to read and write files in \r\n" + 
				"                <hlword>\r\n" + 
				"                  Java\r\n" + 
				"                </hlword>\r\n" + 
				"                : FileReader, BufferedReader, Files, Scanner...\r\n" + 
				"              </passage>\r\n" + 
				"            </passages>\r\n" + 
				"            <properties>\r\n" + 
				"              <_PassagesType>\r\n" + 
				"                0\r\n" + 
				"              </_PassagesType>\r\n" + 
				"              <lang>\r\n" + 
				"                en\r\n" + 
				"              </lang>\r\n" + 
				"            </properties>\r\n" + 
				"            <mime-type>\r\n" + 
				"              text/html\r\n" + 
				"              </mime-type>\r\n" + 
				"            <saved-copy-url>\r\n" + 
				"              https://yandexwebcache.net/yandbtm?lang=en&amp;fmode=inject&amp;tm=1582434650&amp;tld=com&amp;la=1582201728&amp;text=Java&amp;url=https%3A%2F%2Fwww.w3schools.com%2Fjava%2Fjava_files_read.asp&amp;l10n=en&amp;mime=html&amp;sign=1035c90f391484f088ce18555e4877c7&amp;keyno=0\r\n" + 
				"              </saved-copy-url>\r\n" + 
				"          </doc>\r\n" + 
				"        </group>\r\n" + 
				"        <group>\r\n" + 
				"          <categ attr=\"d\" name=\"pjin.jp\" />\r\n" + 
				"          <doccount>\r\n" + 
				"            3\r\n" + 
				"          </doccount>\r\n" + 
				"          <relevance />\r\n" + 
				"          <doc id=\"Z48A91F79BECBA389\">\r\n" + 
				"            <relevance />\r\n" + 
				"            <url>\r\n" + 
				"              https://tech.pjin.jp/blog/tag/java%E7%B7%B4%E7%BF%92%E5%95%8F%E9%A1%8C/\r\n" + 
				"            </url>\r\n" + 
				"            <domain>\r\n" + 
				"              tech.pjin.jp\r\n" + 
				"            </domain>\r\n" + 
				"            <title>\r\n" + 
				"              <hlword>\r\n" + 
				"                JAVA\r\n" + 
				"              </hlword>\r\n" + 
				"              練習問題 | TECH Projin\r\n" + 
				"            </title>\r\n" + 
				"            <modtime>\r\n" + 
				"              20191114T030000\r\n" + 
				"            </modtime>\r\n" + 
				"            <size>\r\n" + 
				"              2541\r\n" + 
				"            </size>\r\n" + 
				"            <charset>\r\n" + 
				"              utf-8\r\n" + 
				"            </charset>\r\n" + 
				"            <passages>\r\n" + 
				"              <passage>\r\n" + 
				"                出題範囲は\r\n" + 
				"                <hlword>\r\n" + 
				"                  java\r\n" + 
				"                </hlword>\r\n" + 
				"                9 以降に追加された範囲を中心とします。\r\n" + 
				"              </passage>\r\n" + 
				"            </passages>\r\n" + 
				"            <properties>\r\n" + 
				"              <_PassagesType>\r\n" + 
				"                0\r\n" + 
				"              </_PassagesType>\r\n" + 
				"              <lang>\r\n" + 
				"                ja\r\n" + 
				"              </lang>\r\n" + 
				"            </properties>\r\n" + 
				"            <mime-type>\r\n" + 
				"              text/html\r\n" + 
				"              </mime-type>\r\n" + 
				"            <saved-copy-url>\r\n" + 
				"              https://yandexwebcache.net/yandbtm?lang=ja&amp;fmode=inject&amp;tm=1582434650&amp;tld=com&amp;la=1575262080&amp;text=Java&amp;url=https%3A%2F%2Ftech.pjin.jp%2Fblog%2Ftag%2Fjava%25E7%25B7%25B4%25E7%25BF%2592%25E5%2595%258F%25E9%25A1%258C%2F&amp;l10n=en&amp;mime=html&amp;sign=918f985d02a9955402ea19f3c70601f4&amp;keyno=0\r\n" + 
				"              </saved-copy-url>\r\n" + 
				"          </doc>\r\n" + 
				"        </group>\r\n" + 
				"      </grouping>\r\n" + 
				"    </results>\r\n" + 
				"  </response>\r\n" + 
				"</yandexsearch>";
	}

}
