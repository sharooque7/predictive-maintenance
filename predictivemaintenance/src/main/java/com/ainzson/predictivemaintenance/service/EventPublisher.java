package com.ainzson.predictivemaintenance.service;

import com.ainzson.predictivemaintenance.domain.SensorReadingEvent;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    public void publish(SensorReadingEvent event) {
        // TODO: Integrate Kafka later
        System.out.println("Event: " + event);
    }
}
