package com.ainzson.predictivemaintenance.mapper;

import com.ainzson.predictivemaintenance.dto.PlcDto;
import com.ainzson.predictivemaintenance.entities.Plc;

public class PlcMapper {

    public static Plc toEntity(PlcDto dto) {
        return Plc.builder().plcId(dto.getId())
                .plcName(dto.getPlcName())
                .ipAddress(dto.getIpAddress())
                .protocol(dto.getProtocol())
                .location(dto.getLocation())
                .build();
    }

    public static PlcDto toDto(Plc entity) {
        return PlcDto.builder()
                .id(entity.getPlcId())
                .plcName(entity.getPlcName())
                .ipAddress(entity.getIpAddress())
                .protocol(entity.getProtocol())
                .location(entity.getLocation())
                .build();
    }
}
