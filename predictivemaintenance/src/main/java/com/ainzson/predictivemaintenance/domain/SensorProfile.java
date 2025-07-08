package com.ainzson.predictivemaintenance.domain;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SensorProfile {
    private UUID sensorId;
    private UUID assetId;
    private UUID plcId;
    private String tagName;
    private String sensorType;
    private String unit;
    private double minRange;
    private double maxRange;
}
