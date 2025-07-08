package com.ainzson.predictivemaintenance.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "plc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plc {

    @Id
    @Column(name = "plc_id", nullable = false)
    private UUID plcId;

    @Column(name = "plc_name", nullable = false, length = 100)
    private String plcName;

    @Column(name = "ip_address", nullable = false, length = 45)
    private String ipAddress;

    @Column(name = "protocol", nullable = false, length = 20)
    private String protocol; // values: 'opcua', 'modbus', 'mqtt'

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "plc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asset> assets = new ArrayList<>();
}
