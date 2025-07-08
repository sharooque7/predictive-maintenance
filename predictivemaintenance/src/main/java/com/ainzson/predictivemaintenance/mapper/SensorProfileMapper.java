package com.ainzson.predictivemaintenance.mapper;

import com.ainzson.predictivemaintenance.domain.SensorProfile;
import com.ainzson.predictivemaintenance.entities.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SensorProfileMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static SensorProfile toProfile(Sensor sensor) {
        try {
            ReadingRange range = objectMapper.readValue(sensor.getReadingRange(), ReadingRange.class);
            return new SensorProfile(
                    sensor.getSensorId(),
                    sensor.getAsset().getAssetId(),
                    sensor.getAsset().getPlc().getPlcId(),
                    sensor.getTagName(),
                    sensor.getSensorType(),
                    sensor.getUnit(),
                    range.getMin(),
                    range.getMax()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse reading range for sensor: " + sensor.getSensorId(), e);
        }
    }
}
