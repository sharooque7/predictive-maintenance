package com.ainzson.predictivemaintenance.service.publisher;

import com.ainzson.predictivemaintenance.domain.SensorReadingEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface EventPublisherStrategy {
    void publish(SensorReadingEvent event) throws JsonProcessingException;
}
