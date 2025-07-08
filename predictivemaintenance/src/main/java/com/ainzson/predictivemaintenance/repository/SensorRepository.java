package com.ainzson.predictivemaintenance.repository;

import com.ainzson.predictivemaintenance.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SensorRepository extends JpaRepository<Sensor, UUID> {
    @Query("SELECT s FROM Sensor s JOIN FETCH s.asset a JOIN FETCH a.plc")
    List<Sensor> findAllWithHierarchy();
}
