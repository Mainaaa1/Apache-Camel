# Day 2 — Camel Routes

**Week:** 5  
**Theme:** Apache Camel  
**Focus:** Defining Routes & Message Flow

---

## Overview

Day 2 moves from **integration theory** to **actual Apache Camel code**.

The goal is to understand **how Camel routes are written**, how messages move through a route, and how Camel integrates with **Spring Boot**.

If Day 1 answered *“why integration frameworks exist”*,  
Day 2 answers *“how Camel actually works.”*

---

## What Is a Camel Route?

A **route** defines the path a message follows from a **source** to one or more **destinations**.

In Camel, a route always starts with:

```java
from(...)
```
and continues with one or more processing steps:
```java
to(...)
```

Conceptually:
```java
Source → Processing → Destination
```

### Basic Route Structure

A minimal Camel route looks like this:

```java
from("timer:hello?period=5000")
    .log("Hello from Apache Camel");
```

This route:

- Triggers every 5 seconds

- Logs a message

- Requires no external system

## Camel with Spring Boot

Camel integrates seamlessly with Spring Boot.

### Key Dependencies

- camel-spring-boot-starter

- Spring Boot Web (optional)

- Java DSL for routes

Once Camel is on the classpath:

- Routes are auto-discovered

- No manual bootstrapping required

- Lifecycle is managed by Spring

### Creating a Route Class

Routes are defined by extending RouteBuilder.
```java
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("timer:demo?period=3000")
            .log("Camel route is running...");
    }
}
```
What’s Happening

- @Component → Spring registers the route

- configure() → Route definition

- timer: → Built-in Camel endpoint

- log() → Logs message to console

### Common Camel Endpoints

Camel supports hundreds of endpoints. Common ones include:

| Endpoint  | Purpose                        |
| --------- | ------------------------------ |
| `timer:`  | Trigger messages on a schedule |
| `direct:` | Synchronous in-memory routing  |
| `file:`   | Read/write files               |
| `http:`   | HTTP calls                     |
| `log:`    | Logging                        |
| `jms:`    | Messaging queues               |


Example:
```java
from("direct:start")
    .to("log:processing")
    .to("direct:end");
```
### Understanding Exchanges

Camel wraps each message inside an Exchange.

An Exchange contains:

- Message body

- Headers

- Properties

- Metadata

Camel automatically:

- Creates the exchange

- Passes it through the route

- Handles lifecycle

You rarely create exchanges manually.

Route Flow Example
```java
from("file:input")
    .log("File received")
    .to("file:output");
```

This route:

- Watches the input directory

- Logs when a file appears

- Moves it to output

- No polling logic. No file handling code. Camel handles it.

**Route Identification**

You can name routes explicitly:
```java
from("timer:sample?period=2000")
    .routeId("sample-timer-route")
    .log("Running sample route");
```

Useful for:

- Debugging

- Monitoring

- Metrics

## Why Routes Matter

Routes:

- Represent business workflows

- Encapsulate integration logic

- Are easy to read and reason about

- Reduce boilerplate drastically

- A good Camel route reads like documentation.

## Common Beginner Mistakes

- Forgetting @Component
- Mis-typed endpoint URIs
- Expecting HTTP routes without Spring Web
- Overloading routes with logic (use processors later)

## Key Takeaways

- Routes are the heart of Apache Camel

- from() defines the source

- to() defines the destination

- Camel handles plumbing automatically

- Spring Boot manages lifecycle