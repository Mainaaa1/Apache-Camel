package com.example.camel.processors;

import com.example.camel.model.Order;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class OrderTransformationProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {

        String body = exchange.getIn().getBody(String.class);
        String[] parts = body.split(",");

        Long id = Long.parseLong(parts[0].trim());
        String name = parts[1].trim();
        Double amount = Double.parseDouble(parts[2].trim());

        Order order = new Order(id, name, amount);
        exchange.getIn().setBody(order);
    }
}
