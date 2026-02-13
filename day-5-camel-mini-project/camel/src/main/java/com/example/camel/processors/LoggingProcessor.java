package com.example.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(LoggingProcessor.class);

    @Override
    public void process(Exchange exchange) {
        String body = exchange.getIn().getBody(String.class);
        log.info("Processing message: {}", body);
    }
}
