package com.example.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HttpRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("timer:httpTimer?period=60000")
            .routeId("http-route")
            .setHeader("CamelHttpMethod", constant("GET"))
            .to("https://jsonplaceholder.typicode.com/posts/1")
            .log("HTTP Response: ${body}");
    }
}
