package com.example.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import com.example.camel.processors.LoggingProcessor;
import com.example.camel.processors.UpperCaseProcessor;

@Component
public class FileRoute extends RouteBuilder {

    private final LoggingProcessor loggingProcessor;
    private final UpperCaseProcessor upperCaseProcessor;

    public FileRoute(LoggingProcessor loggingProcessor,
                     UpperCaseProcessor upperCaseProcessor) {
        this.loggingProcessor = loggingProcessor;
        this.upperCaseProcessor = upperCaseProcessor;
    }

    @Override
    public void configure() {

        from("file:src/main/resources/data/input?noop=true")
            .routeId("file-route")
            .process(loggingProcessor)
            .process(upperCaseProcessor)
            .to("file:src/main/resources/data/output");
    }
}
