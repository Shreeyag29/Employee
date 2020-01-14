### Employee Activity Tracking System
1. Description: 
      * This application reads employee activities from a folder which consist of two json files consisting of 
        employee activities and saves it to inbuilt database (h2). 
      * It also validates json structure and displays the 7 days statistics of employee activities which consists 
        of activity name and its occurence.

2. Your environment  and Installation Steps:
      * IDE : Eclipse Neon with Spring Integrated or Spring Tool Suite, 
      * Java : JDK 1.8
      * Database : Inbuilt h2 database (in-memory database)

3. Execution steps:
      * You need to clone this project on your local machine
      * You need to go to project's path and run the following commands:
          * `mvn clean install`
          * `mvn spring-boot:run`
      * Then just open browser and type url: http://localhost:8080/summery

4. Project usefulness: 
      * It is a small proof of concept of data fetching, validating and displaying activity statistics from database using spring boot. 
      * It's useful for beginners who want to start working on spring boot.
                       
5. Extra Information: 
      * New Library is used for creating getter setter ie Lombok.
      * Java 8 features also used example : for each function , lambda expression, Collectors , filter streams. 

