package com.ainzson.predictivemaintenance.mapper;

import com.ainzson.predictivemaintenance.dto.SensorDto;
import com.ainzson.predictivemaintenance.entities.Asset;
import com.ainzson.predictivemaintenance.entities.Sensor;

public class SensorMapper {

    public static Sensor toEntity(SensorDto dto, Asset asset) {
        return Sensor.builder()
                .sensorId(dto.getId())
                .sensorType(dto.getSensorType())
                .unit(dto.getUnit())
                .signalType(dto.getSignalType())
                .readingRange(dto.getReadingRange())
                .tagName(dto.getTagName())
                .asset(asset)
                .build();
    }

    public static SensorDto toDto(Sensor entity) {
        return SensorDto.builder()
                .id(entity.getSensorId())
                .sensorType(entity.getSensorType())
                .unit(entity.getUnit())
                .signalType(entity.getSignalType())
                .readingRange(entity.getReadingRange())
                .tagName(entity.getTagName())
                .assetId(entity.getAsset().getAssetId())
                .build();
    }
}
