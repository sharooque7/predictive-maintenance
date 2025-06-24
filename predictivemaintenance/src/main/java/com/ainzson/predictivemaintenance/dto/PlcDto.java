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
public class PlcDto {
    private UUID id;               // Optional for POST, used in responses
    private String plcName;
    private String ipAddress;
    private String protocol;       // e.g., "opcua", "modbus", etc.
    private String location;
}
