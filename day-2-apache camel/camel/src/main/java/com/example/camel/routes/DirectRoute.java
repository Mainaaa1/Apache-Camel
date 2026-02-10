package com.example.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DirectRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("direct:start")
            .routeId("direct-start-route")
            .log("Message received in direct:start")
            .to("direct:end");

        from("direct:end")
            .routeId("direct-end-route")
            .log("Message reached direct:end");
    }
}
