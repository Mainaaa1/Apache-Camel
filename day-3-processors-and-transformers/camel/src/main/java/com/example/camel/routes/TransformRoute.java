package com.example.camel.routes;

import com.example.camel.processors.UppercaseProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TransformRoute extends RouteBuilder {

    private final UppercaseProcessor uppercaseProcessor;

    public TransformRoute(UppercaseProcessor uppercaseProcessor) {
        this.uppercaseProcessor = uppercaseProcessor;
    }

    @Override
    public void configure() {

        from("file:input?noop=true")
            .routeId("day3-transform-route")
            .log("Original message: ${body}")
            .process(uppercaseProcessor)
            .log("Transformed message: ${body}")
            .to("file:output");
    }
}
