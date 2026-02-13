package com.example.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class OrderValidationProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String body = exchange.getIn().getBody(String.class);

        if (body == null || body.split(",").length != 3) {
            throw new IllegalArgumentException("Invalid order format");
        }
    }
}
