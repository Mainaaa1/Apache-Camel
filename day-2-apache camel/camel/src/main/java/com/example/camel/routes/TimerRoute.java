package com.example.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TimerRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("timer:day2-timer?period=3000")
            .routeId("day2-timer-route")
            .log("Day 2 Camel route is running...");
    }
}
