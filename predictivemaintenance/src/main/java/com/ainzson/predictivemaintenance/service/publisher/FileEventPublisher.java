package com.ainzson.predictivemaintenance.service.publisher;

import com.ainzson.predictivemaintenance.domain.SensorReadingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
@Slf4j
public class FileEventPublisher implements EventPublisherStrategy {

    private final String outputPath = "sensor_output.log"; // or make it configurable

    @Override
    public void publish(SensorReadingEvent event) {
        try (FileWriter writer = new FileWriter(outputPath, true)) {
            writer.write(event.toString() + "\n");
        } catch (IOException e) {
            log.error("❌ Failed to write event to file", e);
        }
    }
}
