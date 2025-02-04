# POLLING SERVICE

This is a simple Spring Boot application that is used to capture votes cast on a basic poll using a single web page

To run locally :: Go to the root directory of the project run the following command:
mvn spring-boot:run

To access the Front-end open tis URL in your browser: http://localhost:8080/index.html

This application persists the votes in an in-memory database (H2, view console after server starts from
here http://localhost:8080/h2-console/login.jsp).
Data is not saved between restarts of the application.
The database is initialized with some data when the application starts up.

Application logs are viewable in the console and the file `polling_Service.log` in the root directory of the project.

### Actuator Endpoints

The following actuator endpoints are enabled:

http://localhost:8080/actuator/info
http://localhost:8080/actuator/health
