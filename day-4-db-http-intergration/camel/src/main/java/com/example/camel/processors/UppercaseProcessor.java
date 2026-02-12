package com.example.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class UppercaseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {

        String body = exchange.getIn().getBody(String.class);

        if (body != null) {
            String transformed = body.toUpperCase();
            exchange.getIn().setBody(transformed);
        }
    }
}
