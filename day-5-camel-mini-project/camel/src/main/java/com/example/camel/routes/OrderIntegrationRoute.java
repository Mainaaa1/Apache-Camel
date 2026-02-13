package com.example.camel.routes;

import com.example.camel.processors.OrderValidationProcessor;
import com.example.camel.processors.OrderTransformationProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderIntegrationRoute extends RouteBuilder {

    private final OrderValidationProcessor validationProcessor;
    private final OrderTransformationProcessor transformationProcessor;

    public OrderIntegrationRoute(
            OrderValidationProcessor validationProcessor,
            OrderTransformationProcessor transformationProcessor) {
        this.validationProcessor = validationProcessor;
        this.transformationProcessor = transformationProcessor;
    }

    @Override
    public void configure() {

        // Global error handler
        onException(Exception.class)
            .log("Error processing order: ${exception.message}")
            .handled(true)
            .to("file:src/main/resources/data/error");

        from("file:src/main/resources/data/orders?noop=true")
            .routeId("order-integration-route")

            .log("Received Order File: ${file:name}")

            // Step 1: Validate
            .process(validationProcessor)

            // Step 2: Transform
            .process(transformationProcessor)

            // Step 3: Call external HTTP (simulation)
            .setHeader("CamelHttpMethod", constant("GET"))
            .to("https://jsonplaceholder.typicode.com/posts/1")
            .log("External Service Response: ${body}")

            // Step 4: Save to DB
            .setBody(simple("INSERT INTO orders (id, name, amount) VALUES (1,'Test',100)"))
            .to("jdbc:dataSource")

            // Step 5: Archive file
            .to("file:src/main/resources/data/archive")

            .log("Order processing complete");
    }
}
