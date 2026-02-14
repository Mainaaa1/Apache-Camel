package com.example.camel.routes;

import com.example.camel.model.Order;
import com.example.camel.processors.OrderTransformationProcessor;
import com.example.camel.processors.OrderValidationProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderIntegrationRoute extends RouteBuilder {

    private final OrderValidationProcessor validationProcessor;
    private final OrderTransformationProcessor transformationProcessor;

    public OrderIntegrationRoute(OrderValidationProcessor validationProcessor,
                                 OrderTransformationProcessor transformationProcessor) {
        this.validationProcessor = validationProcessor;
        this.transformationProcessor = transformationProcessor;
    }

    @Override
    public void configure() {

        // Dead Letter Channel
        errorHandler(deadLetterChannel("file:src/main/resources/data/error")
                .maximumRedeliveries(3)
                .redeliveryDelay(2000)
                .retryAttemptedLogLevel(org.apache.camel.LoggingLevel.WARN));

        from("file:src/main/resources/data/orders?noop=true")
                .routeId("order-integration-route")

                .log("Processing file: ${file:name}")

                .process(validationProcessor)
                .process(transformationProcessor)

                .log("Transformed Order: ${body}")

                // Content-Based Router
                .choice()
                    .when(simple("${body.amount} > 500"))
                        .log("High value order detected")
                        .setHeader("CamelHttpMethod", constant("GET"))
                        .to("https://jsonplaceholder.typicode.com/posts/1")
                        .log("HTTP Call Completed")
                        .to("file:src/main/resources/data/archive/high-value")
                    .otherwise()
                        .log("Low value order detected")
                        .to("file:src/main/resources/data/archive/low-value")
                .end()

                .log("Route execution completed");
    }
}
