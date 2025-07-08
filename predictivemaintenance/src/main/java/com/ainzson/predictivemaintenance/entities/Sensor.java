package com.ainzson.predictivemaintenance.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sensor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sensor {

    @Id
    @Column(name = "sensor_id", nullable = false)
    private UUID sensorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(name = "sensor_type", nullable = false, length = 50)
    private String sensorType;

    @Column(name = "unit", length = 20)
    private String unit;

    @Column(name = "reading_range", columnDefinition = "jsonb")
    private String readingRange; // Should be parsed to min/max via utility

    @Column(name = "signal_type", length = 20)
    private String signalType; // 'analog', 'digital', 'virtual'

    @Column(name = "tag_name", length = 100)
    private String tagName;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
