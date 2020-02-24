# search-engine-comparison




## Prerequesites

* Install Java
	- Go to the Oracle Web page and download the Oracle JDK (Windows x64): https://www.oracle.com/java/technologies/javase-jdk8-downloads.html.
	- Once you have the .exe file in you computer, just execute and follow the wizard instructions.

* Install Maven 
	- Go to Maven page: https://maven.apache.org/download.cgi
	- Download the "Source zip archive".
	- Once you have the .zip file, uncompress it con C:\ disk (e.g: C:\Dev\apache-maven-3.6.1)	

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
	- Once the file is downloaded in your machine just uncompress it  and execute the .exe file.


## Running aplication
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
	
### Open with IDE



