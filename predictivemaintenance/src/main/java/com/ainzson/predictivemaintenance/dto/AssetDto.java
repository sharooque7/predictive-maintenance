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
public class AssetDto {
    private UUID id;
    private String assetName;
    private String type;           // Optional: "robot_arm", "conveyor", etc.
    private String location;
    private UUID plcId;            // Reference to owning PLC
}
