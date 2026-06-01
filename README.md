● Spring Boot, Maven
● Data link: https://raw.githubusercontent.com/mledoze/countries/master/countries.json
● The application exposes REST endpoint /routing/{origin}/{destination} that
returns a list of border crossings to get from origin to destination
● Single route is returned if the journey is possible
● Algorithm needs to be efficient
● If there is no land crossing, the endpoint returns HTTP 400
● Countries are identified by cca3 field in country data
● HTTP request sample (land route from Czech Republic to Italy):
○ GET /routing/CZE/ITA HTTP/1.0 :
{
"route": ["CZE", "AUT", "ITA"]
}

========================================================================
1.	Unzip the archive file
2.	CD to the location folder where the archive unzipped, make sure the "src" folder and pom.xml files are existing
3.	Run maven task to execute clean install goals (mvn clean install)
4.	Make sure the output.jar file created and all maven goals have finished successful. Output .jar should be located inside \target folder
5.	The jar file name is routing-trips-app-1.0-SNAPSHOT-s-b.jar the path is target\routing-trips-app-1.0-SNAPSHOT-s-b.jar
6.	Run the jar file by executing following command java -jar target\routing-trips-app-1.0-SNAPSHOT-s-b.jar
7.	The Spring Boot app should be up and running
8.	Verify the endpoint works and accessible at http://localhost:8080/routing/CZE/PRT or using POSTMAN
9.	Execute GET requests using input parameters and get response results or 400 HttpResponses back (including response message details)
 
