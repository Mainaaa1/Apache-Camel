package com.example.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("timer:dbTimer?period=30000")
            .routeId("database-route")
            .setBody(constant("INSERT INTO users (name) VALUES ('Camel User')"))
            .to("jdbc:dataSource");
    }
}
