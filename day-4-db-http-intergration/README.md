# Apache Camel Integration Demo

## Overview

This project demonstrates enterprise integration patterns using **Apache Camel 4.17.0** with **Spring Boot**. It showcases file processing, HTTP integration, and database interaction within a modular, production-style architecture.

The application includes:

- File ingestion and transformation
- Scheduled HTTP API consumption
- Database inserts using JDBC
- Custom processors
- H2 in-memory database
- Structured and separated route definitions

---

## Tech Stack

- Java 21
- Spring Boot
- Apache Camel (camel-spring-boot-starter)
- H2 Database
- Maven

---

## Project Structure

```
camel/
│── pom.xml
│
└── src/
    └── main/
        ├── java/com/example/camel/
        │   │── CamelIntegrationApplication.java
        │   │
        │   ├── routes/
        │   │     ├── FileRoute.java
        │   │     ├── HttpRoute.java
        │   │     └── DatabaseRoute.java
        │   │
        │   ├── processors/
        │   │     ├── LoggingProcessor.java
        │   │     └── UpperCaseProcessor.java
        │   │
        │   └── config/
        │         └── CamelConfig.java
        │
        └── resources/
            │── application.properties
            │
            └── data/
                ├── input/
                └── output/
```

---

## Features

### 1. File Integration

**Route ID:** `file-route`

- Reads files from:
  ```
  src/main/resources/data/input
  ```
- Logs file content
- Converts content to uppercase
- Writes processed file to:
  ```
  src/main/resources/data/output
  ```

Demonstrates:
- Camel File Component
- Custom Processors
- Message transformation

---

### 2. HTTP Integration

**Route ID:** `http-route`

- Triggered every 60 seconds
- Sends GET request to:
  ```
  https://jsonplaceholder.typicode.com/posts/1
  ```
- Logs HTTP response

Demonstrates:
- Timer component
- HTTP component
- Scheduled polling

---

### 3. Database Integration

**Route ID:** `database-route`

- Executes every 30 seconds
- Inserts record into H2 in-memory database
- Uses Camel JDBC component

Demonstrates:
- Timer-based scheduling
- JDBC integration
- DataSource configuration

---

## How to Run

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd camel-integration-demo
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

or

```bash
java -jar target/camel-integration-demo.jar
```

---

## Testing File Route

1. Add a `.txt` file inside:
   ```
   src/main/resources/data/input
   ```
2. Start the application.
3. Check:
   ```
   src/main/resources/data/output
   ```
The file content should be converted to uppercase.

---

## Accessing H2 Console

H2 console is enabled.

URL:
```
http://localhost:8080/h2-console
```

JDBC URL:
```
jdbc:h2:mem:testdb
```

Username:
```
sa
```

Password:
```
(empty)
```

---

## Key Concepts Demonstrated

- RouteBuilder abstraction
- Custom Processor implementation
- Dependency injection of processors
- Component-based routing (file, http, jdbc)
- Modular package structure
- Spring-managed DataSource
- Scheduled route execution

---

## Architecture Flow

```
File System → Processor → File System
Timer → HTTP API → Log
Timer → SQL Insert → H2 Database
```

Each integration concern is isolated into its own route for maintainability and scalability.

---

## Potential Enhancements

- Add REST endpoints using Camel REST DSL
- Implement error handling (onException, dead letter channel)
- Add message queues (ActiveMQ, Kafka)
- Replace H2 with PostgreSQL or MySQL
- Add integration tests using Camel Test
- Containerize using Docker
- Implement centralized logging and monitoring

---

## Learning Outcomes

After completing this project, you should understand:

- How Apache Camel routes are structured
- How to integrate multiple systems using Camel
- How processors manipulate Exchange messages
- How to schedule jobs with timer endpoints
- How to perform database operations using Camel
- How to structure enterprise integration applications

---

## License

MIT License
