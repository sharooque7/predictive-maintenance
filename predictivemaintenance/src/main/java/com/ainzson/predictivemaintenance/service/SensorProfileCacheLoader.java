package com.ainzson.predictivemaintenance.service;

import com.ainzson.predictivemaintenance.domain.SensorProfile;

import com.ainzson.predictivemaintenance.entities.Sensor;
import com.ainzson.predictivemaintenance.mapper.SensorProfileMapper;
import com.ainzson.predictivemaintenance.repository.SensorRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SensorProfileCacheLoader {

    private final SensorRepository sensorRepository;

    @Getter
    private Map<UUID, SensorProfile> sensorProfileMap = new ConcurrentHashMap<>();

    public SensorProfileCacheLoader(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @PostConstruct
    public void loadProfiles() {
        List<Sensor> sensors = sensorRepository.findAllWithHierarchy();
        sensorProfileMap = sensors.stream()
                .map(SensorProfileMapper::toProfile)
                .collect(Collectors.toMap(SensorProfile::getSensorId, Function.identity()));

       log.info("Loaded " + sensorProfileMap.size() + " sensor profiles into cache.");
    }

}
