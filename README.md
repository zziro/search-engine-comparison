# search-engine-comparison

Search engine comparison is an application that allows to determine the popularity of programming languages by getting result from:

	 * Google
	 * Bing
	 * Yandex (It only allows to make 10 request)

The application works as follows:

When we pass paremeters like: "Java "Java Script" Python PHP", the application will take every parameter and send a request to get results from every Search Engine API. The result will be like this:

		1.- Java ->  Google=51300000 Bing=77700000
		2.- Java Script ->  Google=6 Bing=101000000
		3.- Python ->  Google=32400000 Bing=68200000
		4.- PHP ->  Google=333000000 Bing=6250000000

As we can see, we have a result for Java from Google and Bing. 
From this information we can get the highest number by Search Engine (in this case Google and Bing). We will be calling this, winners:

	Google Winner = 333000000 Bing Winner = 6250000000

And also from this information we can get the higest of the higest. This value we will be calling as Total Winner:

	Total Winner = 6250000000

## Prerequesites
* Generate and getet the API Key from Search Engine:
	- Google Search Engine API:https://developers.google.com/custom-search/v1/overview
	- Bing Search Engine API:https://docs.microsoft.com/en-us/azure/cognitive-services/bing-web-search/quickstarts/java
	- Yadex Search Engine API: https://xml.yandex.com/settings/

* Install Java
	- Go to the Oracle Web page and download the Oracle JDK (Windows x64): https://www.oracle.com/java/technologies/javase-jdk8-downloads.html.
	- Once we have the jdk-8u241-windows-x64.exe file in our computer, execute and follow the wizard instalation instructions.

* Install Maven 
	- Go to Maven page: https://maven.apache.org/download.cgi
	- Download the "Source zip archive".
	- Once you have the .zip file, uncompress it on "C:\" disk (e.g: C:\Dev\apache-maven-3.6.1)	

## Configure development environment
* Configure JAVA_HOME 
	- Go to Control Panel -> System and Security -> System -> Advanced System Settings
	- Click the Enviroment Variables button
	- On the "System variables" section.
	- Select the "Path" option and click the Edit button.
	- On th new window, click the "New" button and type the following:
		C:\Program Files\Java\jdk1.8.0_241\bin
		
	- Click OK.
	
	At this time Java is already configured in our machine, to ensure that, we need to fo the following:
	- Open a Command Prompt
	- Type "java -version"
	- And hit Enter.

	This will give us a the following result which meand that Java is configured correctly.

		C:\Users\Liberato>java -version
		java version "1.8.0_241"
		Java(TM) SE Runtime Environment (build 1.8.0_241-b07)
		Java HotSpot(TM) 64-Bit Server VM (build 25.241-b07, mixed mode)

* Configure MAVE_HOME 
	- Go to Control Panel -> System and Security -> System -> Advanced System Settings
	- Click the Enviroment Variables button
	- On the "System variables" section.
	- Select the "Path" option and click the Edit button.
	- On th new window, click the "New" button and type the following:
		C:\Dev\apache-maven-3.6.1\bin
		
	- Click OK.
	
	At this time Maven is already configured in our machine, to ensure that, we need to fo the following:
	- Open a Command Prompt
	- Type "mvn -version"
	- And hit Enter.

	This will give us a the following result which meand that Maven was configured correctly.

		C:\Users\Liberato>mvn -version
		Apache Maven 3.6.1 (d66c9c0b3152b2e69ee9bac180bb8fcc8e6af555; 2019-04-04T14:00:29-05:00)
		Maven home: C:\Dev\apache-maven-3.6.1\bin\..
		Java version: 1.8.0_241, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jre1.8.0_241
		Default locale: en_US, platform encoding: Cp1252
		OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
	 
* Install IDE (Spring Tool Suite 4)
	- Go to the Spring Tool Suite web page: https://spring.io/tools
	- Downloead the Windows 64-bit version.
	- Once the file is downloaded in your machine just execute it (thi will uncompress the file).
	- Inside the uncompressed folder, execute the "SpringToolSuite4.exe" file.
	- Select the work space of the clonned repository:


## Clonning aplication
* Clonning the application
    - First we need to create a folder (workspace), e.g: D:\workspace
	- Inside that folder, we need to clone the repository:
		git clone https://github.com/zziro/search-engine-comparison.git
	
	- Inside the repository, we find the app "search-engine-comparison-app".
	- Inside the target folder we find the executable .jar: "search-engine-comparison-app-1.0-SNAPSHOT-jar-with-dependencies.jar"	

### Using command Line
* Running the application

	Open the command prompt and navigate to the aplication and execute the following:
	
		D:\workspace\search-engine-comparison\search-engine-comparison-app>java -jar "target/search-engine-comparison-app-1.0-SNAPSHOT-jar-with-dependencies.jar" Java "Java Script" Python PHP
	
	This will print the following information in the console:
	
		1.- Java ->  Google=51300000 Bing=77700000
		2.- Java Script ->  Google=6 Bing=101000000
		3.- Python ->  Google=32400000 Bing=68200000
		4.- PHP ->  Google=333000000 Bing=6250000000

		Google Winner = 333000000 Bing Winner = 6250000000

		Total Winner = 6250000000	
	
Note: It also suports the Russian Seach Engine Yandex(I hit the limit of request, which is 10, so I am not able to perform more requests, but I will give you the steps to configure it)
* Configuration
	In the App.java of the "search-engine-comparison-app" project. We need to comment/uncomment the following lines:

		Uncomment Line 32
		Uncomment Line 36
		Uncomment Line 46, 47
		Uncomment Line 51
		Uncomment Line 58
		Comment Line 60 and uncomment 61	
		Comment Line 64 and uncomment 65
		Inside de callAPI method: uncomment from 93 to 101
		Inside de callAPI method: comment 103, uncomment 104
		Inside de callAPI method: comment 107, uncomment 108
		Inside de callAPI method: comment 110, uncomment 111

	Basically, enable the lines to work with Yadex Search Engine API.
	
	Open the command prompt and navigate to the aplication path.

		D:\workspace\search-engine-comparison\search-engine-comparison-app

	And Run the following maven commands:

		D:\workspace\search-engine-comparison\search-engine-comparison-app>mvn clean install
		D:\workspace\search-engine-comparison\search-engine-comparison-app>mvn package
		D:\workspace\search-engine-comparison\search-engine-comparison-app>mvn clean package assembly:single

	The execution way is the same, to perform this, just go to the "Using command Line" step. And the result will be like this:

		Google = 45400000 Bing = 78300000 Yandex = 14622975
		Google = 294000000 Bing = 6290000000 Yandex = 48394357
		Google = 253000000 Bing = 107000000 Yandex = 15615644

		Google Winner = 294000000 Bing Winner = 6290000000 Yandex Winner = 48394357

		Total Winner = 6290000000



