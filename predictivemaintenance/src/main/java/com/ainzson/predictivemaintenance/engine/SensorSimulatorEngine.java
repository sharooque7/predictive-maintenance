package com.ainzson.predictivemaintenance.engine;

import com.ainzson.predictivemaintenance.domain.SensorProfile;
import com.ainzson.predictivemaintenance.domain.SensorReadingEvent;
import com.ainzson.predictivemaintenance.initializations.SensorProfileCacheLoader;
import com.ainzson.predictivemaintenance.service.EventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class SensorSimulatorEngine {

    private final SensorProfileCacheLoader cacheLoader;
    private final EventPublisher eventPublisher;
    private List<SensorProfile> sensorProfiles;


    private int threadCount;
    private int intervalSeconds;
    private final ScheduledExecutorService scheduler;
    private final SensorProfileCacheLoader sensorProfileCacheLoader;

    @Value("${simulator.thread-count:8}")
    private int configuredThreadCount;

    @Value("${simulator.tick-interval-seconds:1}")
    private int configuredIntervalSeconds;

    public SensorSimulatorEngine(
            SensorProfileCacheLoader cacheLoader,
            EventPublisher eventPublisher,
            SensorProfileCacheLoader sensorProfileCacheLoader
    ) {
        this.cacheLoader = cacheLoader;
        this.eventPublisher = eventPublisher;
        this.threadCount = 0; // Temp placeholder, will be set in initConfig
        this.intervalSeconds = 0;
        this.scheduler = Executors.newScheduledThreadPool(configuredThreadCount);
        this.sensorProfileCacheLoader = sensorProfileCacheLoader;

    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        initConfig();
        loadSensorProfiles();

        List<List<SensorProfile>> profilePartitions = partitionProfiles(this.sensorProfiles, this.threadCount);

        for (int i=0; i < profilePartitions.size(); i++) {
            List<SensorProfile> batch = profilePartitions.get(i);
            System.out.println("🧵 Thread " + (i + 1) + " assigned " + batch.size() + " sensors.");
            simulateBatch(batch);
        }
    }

    private void initConfig() {
        // Assign values from @Value fields to actual final fields
        this.threadCount = configuredThreadCount;
        this.intervalSeconds = configuredIntervalSeconds;
        System.out.println("🟢 SensorSimulatorEngine initialized with " + threadCount + " threads.");

    }

    private void loadSensorProfiles() {
        Map<UUID, SensorProfile> profileMap = cacheLoader.getSensorProfileMap();
        this.sensorProfiles = profileMap.values()
                .stream()
                .collect(Collectors.toList());

        System.out.println("Loaded " + sensorProfiles.size() + " sensor profiles from cache.");
    }

    private List<List<SensorProfile>> partitionProfiles(List<SensorProfile> profiles, int partitionSize){
        List<List<SensorProfile>> partitions = new ArrayList<>();

        int chunkSize = (int) Math.ceil((double) profiles.size() / partitionSize);

        for (int i = 0; i < profiles.size(); i += chunkSize) {
            int end = Math.min(i + chunkSize, profiles.size());
            partitions.add(profiles.subList(i, end));
        }

        return partitions;
    }

    private void simulateBatch(List<SensorProfile> batch) {
        scheduler.scheduleAtFixedRate(() -> {
            long currentTime = System.currentTimeMillis();

            for (SensorProfile profile : batch) {
                double value = generateReading(profile);
                SensorReadingEvent event = new SensorReadingEvent(
                        profile.getSensorId(),
                        profile.getAssetId(),
                        profile.getPlcId(),
                        Instant.ofEpochMilli(currentTime),
                        value,
                        profile.getUnit(),
                        profile.getTagName()
                );

                eventPublisher.publish(event);
            }

        }, 0, intervalSeconds, TimeUnit.SECONDS);
    }

    private double generateReading(SensorProfile profile) {
        double min = profile.getMinRange();
        double max = profile.getMaxRange();
        return min + Math.random() * (max - min); // uniform random value
    }


}
