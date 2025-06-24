package com.ainzson.predictivemaintenance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorDto {
    private UUID id;
    private String sensorType;
    private String unit;
    private String signalType;     // analog/digital/virtual
    private String readingRange;   // JSON string like {"min":0,"max":100}
    private String tagName;
    private UUID assetId;
}
