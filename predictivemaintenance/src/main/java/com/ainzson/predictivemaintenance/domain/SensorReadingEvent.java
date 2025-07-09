package com.ainzson.predictivemaintenance.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SensorReadingEvent {
    private UUID sensorId;
    private UUID assetId;
    private UUID plcId;
    private Instant timestamp;
    private double value;
    private String unit;
    private String tag;

    public SensorReadingEvent(SensorProfile profile, double value, Instant timestamp) {
        this.sensorId = profile.getSensorId();
        this.assetId = profile.getAssetId();
        this.plcId = profile.getPlcId();
        this.unit = profile.getUnit();
        this.tag = profile.getTagName();
        this.timestamp = timestamp;
        this.value = value;
    }
}
