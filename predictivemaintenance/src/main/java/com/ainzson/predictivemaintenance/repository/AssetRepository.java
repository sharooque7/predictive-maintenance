package com.ainzson.predictivemaintenance.repository;

import com.ainzson.predictivemaintenance.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {
}
