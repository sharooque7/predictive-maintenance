package com.ainzson.predictivemaintenance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetOnboardingRowDto {
    // PLC Info
    private String plcName;
    private String ipAddress;
    private String protocol;
    private String plcLocation;

    // Asset Info
    private String assetName;
    private String assetType;
    private String assetLocation;

    // Sensor Info
    private String sensorType;
    private String unit;
    private String signalType;
    private String tagName;
    private String readingRange; // {"min": 0, "max": 100}
}
