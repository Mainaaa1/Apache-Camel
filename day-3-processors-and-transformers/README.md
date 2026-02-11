# Day 3 — Processors & Transformers

**Week 5 — Apache Camel**  
**Date:** 11 Feb 2026  
**Focus:** Custom Processing & Message Transformation

---

## Overview

On Day 3, the focus shifted from defining routes to transforming messages within those routes.

If Day 2 was about **where messages go**,  
Day 3 is about **what happens to messages along the way**.

Apache Camel achieves this using **Processors** and the **Exchange** object.

---

## Key Concepts Covered

- The `Processor` interface
- The `Exchange` object
- Message body transformation
- Header manipulation
- Clean separation between routing and business logic

---

## Understanding the Exchange

Every message in Camel is wrapped inside an `Exchange`.

The Exchange contains:

- Message body
- Headers
- Properties
- Context metadata

Example:

```java
exchange.getIn().getBody();
exchange.getIn().setBody(newBody);
exchange.getIn().setHeader("processed", true);
exchange.setProperty("trackingId", "abc-123");
```
Camel automatically creates and passes the Exchange through the route pipeline.

### Creating a Custom Processor

Example: Converting message content to uppercase.
```java
@Component
public class UppercaseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {

        String body = exchange.getIn().getBody(String.class);

        if (body != null) {
            exchange.getIn().setBody(body.toUpperCase());
        }
    }
}
```

What this does:

- Reads the incoming message body

- Transforms it

- Replaces the body in the Exchange

Using the Processor in a Route
```java
from("file:input?noop=true")
    .routeId("day3-transform-route")
    .log("Original message: ${body}")
    .process(uppercaseProcessor)
    .log("Transformed message: ${body}")
    .to("file:output");
```

**Execution Flow**
```pgsql
Input Folder → Log Original → Transform → Log Result → Output Folder
```

**Inline Processor Alternative**

Instead of a separate class:
```java
.process(exchange -> {
    String body = exchange.getIn().getBody(String.class);
    exchange.getIn().setBody(body.toUpperCase());
})
```

However, for maintainable systems:

- Keep routes declarative

- Move business logic into dedicated Processor classes

- Follow separation of concerns

### Why This Matters

Processors allow you to implement:

- Data transformations (JSON ↔ XML)

- Enrichment

- Validation logic

- Custom routing decisions

- Logging and auditing

- Header-based logic

This is what makes Camel powerful in enterprise integration systems.

### Architectural Insight

- Camel encourages a clean architecture:

- Routes define the flow

- Processors contain logic

- Exchange carries data

This separation improves:

- Maintainability

- Testability

- Scalability

### What Was Achieved Today

- Implemented a custom Processor

- Manipulated the Exchange body

- Added logging before and after transformation

- Practiced clean route design

- Understood internal message handling

### Preparation for Day 4

Next, the integration expands beyond file routing into:

- HTTP endpoints

- Database connectivity

- External service calls

Day 3 establishes the foundation required for building real integration pipelines.
