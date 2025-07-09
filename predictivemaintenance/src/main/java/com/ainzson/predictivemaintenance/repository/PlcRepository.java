package com.ainzson.predictivemaintenance.repository;

import com.ainzson.predictivemaintenance.entities.Plc;
import com.ainzson.predictivemaintenance.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlcRepository extends JpaRepository<Plc, UUID> {
}
