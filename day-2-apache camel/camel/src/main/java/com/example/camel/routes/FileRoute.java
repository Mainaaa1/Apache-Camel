package com.example.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("file:input?noop=true")
            .routeId("file-input-route")
            .log("File received: ${file:name}")
            .to("file:output");
    }
}
