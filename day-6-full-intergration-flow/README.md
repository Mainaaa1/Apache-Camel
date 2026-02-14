# Week 5 – Day 6  
# Advanced Routing & Error Handling in Apache Camel

---

## Overview

On Day 6, we evolved our Camel mini-project from basic routing into a more production-style integration flow by introducing enterprise integration patterns and robust error handling.

This session focused on:

- Content-Based Routing  
- Dead Letter Channel  
- Retry policies  
- Structured logging  
- Controlled failure management  

The goal was to build a resilient message processing pipeline capable of handling real-world failure scenarios.

---

## Objectives

By the end of Day 6, the application is able to:

- Dynamically route messages based on message content  
- Retry failed messages automatically  
- Send permanently failed messages to a dead-letter folder  
- Log retry attempts with appropriate log levels  
- Separate high-value and low-value order processing paths  

---

## Architecture Flow
```pgsql
File Input (orders/)
↓
Validation Processor
↓
Transformation Processor
↓
Content-Based Router
├── High Value Orders (> 500)
│ ├── HTTP Call
│ └── Archive → high-value/
│
└── Low Value Orders (≤ 500)
└── Archive → low-value/

Failures (after retries)
↓
Dead Letter Folder (error/)
```

## Project structure

```pgsql
day-5-camel-mini-project/
│
├── pom.xml
│
└── src/
└── main/
├── java/
│ └── com/example/camel/
│ ├── Application.java
│ ├── OrderRoute.java
│ ├── OrderProcessor.java
│ └── ValidationProcessor.java
│
└── resources/
└── data/
├── orders/
├── archive/
│ ├── high-value/
│ └── low-value/
└── error/
```


---

## Key Implementation Details

### 5.1 Global Error Handler (Dead Letter Channel)

```java
errorHandler(deadLetterChannel("file:src/main/resources/data/error")
    .maximumRedeliveries(3)
    .redeliveryDelay(2000)
    .retryAttemptedLogLevel(LoggingLevel.WARN));

```

**Behavior**

- Retries each failed message up to 3 times

- Waits 2 seconds between retry attempts

- Logs retry attempts at WARN level

- Moves the message to error/ if all retries fail

This implements the Dead Letter Channel pattern, a fundamental Enterprise Integration Pattern (EIP).

**5.2 Content based Router**
```java
.choice()
    .when(simple("${body.amount} > 500"))
        .log("High value order detected")
        .to("https://jsonplaceholder.typicode.com/posts/1")
        .to("file:src/main/resources/data/archive/high-value")
    .otherwise()
        .log("Low value order detected")
        .to("file:src/main/resources/data/archive/low-value")
.end();
```

**Behavior**

- Orders above 500 are classified as high-value

- High-value orders trigger an HTTP call

- Low-value orders are directly archived

- Routing decisions are made dynamically at runtime

This implements the Content-Based Router pattern.

## Test Scenarios
 Scenario 1 – Low Value Order

Input (place inside orders/):
```java
101,Ian,200
```

Expected Result:

- Processed successfully

- Archived inside:

```yaml
archive/low-value/
```

 Scenario 2 – High Value Order

Input:
```java
102,Ian,1000
```

Expected Result:

- HTTP call executed

- Archived inside:

```pqsql
archive/high-value/
```

Scenario 3 – Invalid Order Format

Input:
```java
bad,data
```

Expected Result:

Retry 3 times

Warning logs displayed

File moved to:

error/

## Enterprise Integration Patterns Practiced

During Day 6, we implemented:

- Content-Based Router

- Dead Letter Channel

- Retry Pattern

- Processor Pattern

- Message Transformation

- Fault-Tolerant Routing

These patterns are foundational for designing enterprise-grade integration systems.

## Logging & Observability

We enhanced visibility by:

- Logging route entry and exit

- Logging retry attempts at WARN level

- Logging routing decisions

- Logging final error handling outcomes

This improves traceability and operational monitoring.

## Key Lessons

Day 6 shifted focus from simple routing to:

- Designing resilient integrations

- Handling failure deterministically

- Building retry-aware systems

- Applying real Enterprise Integration Patterns

- Structuring production-ready message flows

We moved from “it works” to “it works reliably.”

## Key Takeaway

In real-world integration systems:

- Failure is expected.
- Retries must be controlled.
- Errors must be captured.
- Routing must be deterministic.

Today’s implementation ensures:

- Controlled retries

- Clear failure handling

- Intelligent routing

- Improved observability
