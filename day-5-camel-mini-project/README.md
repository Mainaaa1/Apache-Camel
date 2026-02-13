# Week 5 – Day 5  
## Apache Camel Mini Integration Project

---

## Overview

Day 5 focused on building a complete integration flow using Apache Camel and Spring Boot.  
The goal was to combine file ingestion, validation, transformation, external API communication, and error handling into a single cohesive route.

This represents a realistic enterprise integration scenario.

---

## Objectives

- Build a full Apache Camel integration route
- Implement custom processors
- Validate and transform incoming data
- Call an external HTTP service
- Handle errors gracefully
- Archive processed files

---

## What I Built

A working integration pipeline:

```
File → Validate → Transform → HTTP Call → Archive
```

The application:

1. Reads order files from a directory
2. Validates file format
3. Transforms raw CSV data into a Java object
4. Calls an external REST API
5. Archives successful files
6. Redirects failed files to an error folder

---

## Key Concepts Practiced

- Apache Camel RouteBuilder
- Custom Processors (`Processor` interface)
- Dependency Injection in routes
- Global Exception Handling (`onException`)
- File Component
- HTTP Component
- Spring Boot Auto Configuration
- Integration project structure

---

## Implementation Details

### File Input Location

```
src/main/resources/data/orders
```

### Expected File Format

```
orderId,customerName,amount
```

Example:

```
101,Ian,250
```

### Error Handling Strategy

Global exception handling is configured using:

```java
onException(Exception.class)
    .handled(true)
    .to("file:src/main/resources/data/error");
```

Any invalid or malformed file is redirected automatically.

---

## Project Structure

```
camel-day5-mini-project/
│── pom.xml
└── src/main/
    ├── java/com/example/camel/
    │   ├── CamelIntegrationApplication.java
    │   ├── model/Order.java
    │   ├── processors/
    │   │     ├── OrderValidationProcessor.java
    │   │     └── OrderTransformationProcessor.java
    │   └── routes/OrderIntegrationRoute.java
    │
    └── resources/
        └── data/
            ├── orders/
            ├── archive/
            └── error/
```

---

## How to Run

### 1. Build

```
mvn clean install
```

### 2. Start Application

```
mvn spring-boot:run
```

### 3. Test

Place a file inside:

```
src/main/resources/data/orders/
```

With content:

```
101,Ian,250
```

The file will:

- Be processed
- Trigger an HTTP call
- Move to the archive folder

If invalid, it moves to the error folder.

---

## Challenges Faced

- Maven dependency resolution issues
- Classpath synchronization in IDE
- Public class/file naming mismatches
- Test compilation conflicts
- Ensuring correct Spring Boot + Camel starter usage

---

## Reflection

This day consolidated multiple integration concepts into a single working system.  
It moved from isolated Camel components to a realistic end-to-end workflow.

Understanding how processors, routing logic, and error handling work together is a key milestone in mastering integration frameworks.

