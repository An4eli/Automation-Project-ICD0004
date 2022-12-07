# Automation Project ICD0004

### Participants
- Alekasndr Borovkov
- Alen Siilivask
- Anneli Väli

***
### Tech stack used
- JDK 17
- Maven
- Log4j

***
### Installation

```
git clone https://gitlab.cs.ttu.ee/alsiil/Automation-Project-ICD0004.git
cd Automation-Project-ICD0004
mvn clean install -DskipTests
```

***

### Run the app in the command line

UNIX and Maven Goal executor in IntelliJ
```
mvn exec:java -Dexec.mainClass=main.WeatherMain
```
Windows CMD
```
mvn exec:java -D"exec.mainClass"="main.WeatherMain"
```
- Application will ask to enter a number: 1 for writing city name, 2 for providing full path to file containing city names

#### Example 1:
To insert city name - 1, to insert path to file with cities - 2:
```
1
```
Enter a city name:
```
Tallinn
```
***
#### Example 2:
To insert city name - 1, to insert path to file with cities - 2:
```
2
```
Enter path to file with cities
```
/Users/username/Desktop/automation-project/cities.txt
```
- City names in the file must be each on different line and file format should be .txt.

Example of cities.txt:
```
Tallinn
Riga
```
***

Application creates .json file containing weather report data for each city in the project folder


***
### Run tests
```
mvn test
```
