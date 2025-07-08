package com.ainzson.predictivemaintenance.entities;

import com.ainzson.predictivemaintenance.entities.Plc;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "asset")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @Column(name = "asset_id", nullable = false)
    private UUID assetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plc_id", nullable = false)
    private Plc plc;

    @Column(name = "asset_name", nullable = false, length = 100)
    private String assetName;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sensor> sensors = new ArrayList<>();
}
