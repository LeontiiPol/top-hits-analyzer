# Top Hits Analyzer

Below you can see the short description of the project, stack and instructions to run project
via Docker. To see detailed API documentation run the project and open swagger as instructed below.

## Description

Top Hits Analyzer analyzes csv file which contains dataset of 2000 records from Spotify.
Dataset contains information about top hit songs from 2000 to 2019 years.
You can check more detailed info about dataset 
[here](https://www.kaggle.com/datasets/paradisejoy/top-hits-spotify-from-20002019).

Application has single endpoint **/api/songs/deciles**. It requires 
mandatory request parameter *colname* that accepts a csv file's column name.
Application calculates and returns [deciles](https://math.semestr.ru/group/deciles.php) of a csv file's column 
based on given column name. Also, you can pass optional *year* request parameter.
If *year* is passed, application calculates 
deciles taking into account songs that were released on specified year only.

## Stack

+ SpringBoot 2.7.0
+ Spring Web
+ Opencsv
+ Gradle
+ Docker
+ JUnit 5
+ Mockito
+ Lombok
+ Swagger

## Run using Docker

1. clone this repo
2. open the terminal in the project root folder (topHitsAnalyzer)
3. run `docker build -t top-hits-analyzer:latest .`
4. run `docker run --name top-hits-analyzer -p 8080:8080 -d top-hits-analyzer:latest`
5. open Postman and test API. Examples of requests:
   1. *http://localhost:8080/api/songs/deciles?colname=duration_ms*
   2. *http://localhost:8080/api/songs/deciles?colname=key&year=2008*
6. to see API documentation open *http://localhost:8080/swagger-ui/*
7. run `docker stop top-hits-analyzer` to stop container

## Run using Gradle
1. clone this repo
2. open the terminal in the project root folder (topHitsAnalyzer)
3. make sure you use java 11
4. run `gradle build`
5. run `java -jar build/libs/TopHitsAnalyzer-1.0.0.jar`
