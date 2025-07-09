package com.ainzson.predictivemaintenance.service.publisher;

import com.ainzson.predictivemaintenance.domain.SensorReadingEvent;
import com.ainzson.predictivemaintenance.utils.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaEventPublisher implements EventPublisherStrategy {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Mapper mapper;

    public KafkaEventPublisher(Mapper mapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    private static final String TOPIC = "sensor-data";

    @Override
    public void publish(SensorReadingEvent event) {
        try {
            String payload = mapper.getObjectMapper().writeValueAsString(event);
            kafkaTemplate.send(TOPIC, payload);
        } catch (Exception e) {
            log.error("❌ Failed to publish to Kafka", e);
        }
    }
}
