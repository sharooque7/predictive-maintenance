package com.ainzson.predictivemaintenance.mapper;

import com.ainzson.predictivemaintenance.dto.AssetDto;
import com.ainzson.predictivemaintenance.entities.Asset;
import com.ainzson.predictivemaintenance.entities.Plc;

public class AssetMapper {

    public static Asset toEntity(AssetDto dto, Plc plc) {
        return Asset.builder()
                .assetId(dto.getId())
                .assetName(dto.getAssetName())
                .type(dto.getType())
                .location(dto.getLocation())
                .plc(plc)
                .build();
    }

    public static AssetDto toDto(Asset entity) {
        return AssetDto.builder()
                .id(entity.getAssetId())
                .assetName(entity.getAssetName())
                .type(entity.getType())
                .location(entity.getLocation())
                .plcId(entity.getPlc().getPlcId())
                .build();
    }
}
