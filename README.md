# Course-Management-Challenge

Application to empower teachers in course management.

## Backend

#### Technologies

- Java 17
- Spring Boot

#### Getting Started

These instructions will get you a copy of the backend project up and running on your local machine for development and testing purposes:

##### Running the application locally

```
mvn clean package
mvn spring-boot:run
```

##### Testing

```
mvn clean test
```

## Frontend

#### Technologies

- React
  
#### Getting Started

These instructions will get you a copy of the frontend project up and running on your local machine for development and testing purposes

##### Running the application locally

```
npm install
npm start
```

##### Testing

```
npm test
```

## Requirements

- The application uses the H2 database to run as in-memory database and store the prices
  - http://localhost:8080/h2-console
    - username: test
    - password: test
- Swagger has been enabled to visualize and interact with the API’s resources
  - http://localhost:8080/swagger-ui.html
- Actuator health endpoint available
  - http://localhost:8080/actuator/health

## Implementation choices
- When the backend is started, 4 students are persisted by default in the database
- When a student registers for a course, the backend triggers an event to send an email to the student. Actually, the system does not send the email, the goal was to add an event-driven solution to decouple this use case and have it executed asynchronously
- A student can enroll in many courses and courses have many students enrolled. An intermediate table has been created to handle this
- A student cannot enroll in a course in which he/she is already enrolled
- Hexagonal Architecture applied to the backend
  - More details here: https://rbailen.medium.com/hexagonal-architecture-with-spring-boot-74e93030eba3

## Proposed improvements

- Development based on API First
- Dockerize the applications
- Security with OAuth and JWT tokens
- Evolutionary database design with Liquibase or Flyway
- Add pagination, filtering and sorting to the course list
- Add the use of a third party library for sending emails