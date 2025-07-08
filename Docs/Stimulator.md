# IIoT High-Performance Event Simulator

## 📄 Overview

This document outlines the architecture, design goals, and implementation plan for a high-throughput Industrial IoT (IIoT) event simulator. The simulator is designed to mimic real-world sensor data generation at scale, producing 2 million+ events per hour, and scalable to handle 10 million+ events per hour.

---

## 🚀 Problem Statement

Real-time systems in industrial automation need to process massive volumes of telemetry data from sensors attached to assets controlled by PLCs (Programmable Logic Controllers). Building and validating pipelines for such use cases requires a synthetic yet realistic event generator.

### Key Goals

- Generate **2M events/hour** (baseline)
- Scalable to **10M events/hour**
- Simulate hierarchical device structure: **PLC ➔ Asset ➔ Sensor**
- Emit structured, timestamped events
- Support delivery to **Kafka**, and optionally to file/log for debugging

---

## 🤝 Solution Overview

### Device Topology

- **PLCs**: 12 (each with a unique IP and protocol)
- **Assets per PLC**: 32
- **Sensors per Asset**: 8
- **Total Sensors**: 3,072

Each sensor emits one reading every **5 seconds**, resulting in:

```
3072 sensors * (3600 / 5) = 2,211,840 events/hour
```

### Event Format (JSON)

```json
{
  "sensorId": "UUID",
  "assetId": "UUID",
  "plcId": "UUID",
  "timestamp": "ISO8601",
  "value": 42.5,
  "unit": "C",
  "tag": "PLC_01_Asset_01_Sensor_temperature_1"
}
```

---

## 📁 Architecture

### 1. Metadata Loader

- Loads PLC, Asset, and Sensor information from database or static SQL/json
- In-memory `Map<UUID, SensorProfile>` for fast lookup

### 2. Simulation Engine

- Uses `ScheduledExecutorService` to run concurrent sensor tickers
- One thread handles \~250-500 sensors
- Fixed rate emission every 5s with optional jitter

### 3. Event Publisher

- Sends data asynchronously to Kafka
- Supports batch publishing, retries, and metrics

### 4. Configuration Layer

- YAML or CLI driven:

  - `thread.count`
  - `kafka.topic`
  - `sensor.interval`
  - `log.mode`

### 5. Monitoring & Metrics

- Track `events/sec`, `latency`, `failures`
- Expose via REST endpoint or Prometheus

---

## ⚠️ Risks and Mitigations

| Risk               | Description                          | Mitigation                              |
| ------------------ | ------------------------------------ | --------------------------------------- |
| Kafka bottleneck   | Slow broker stalls simulator         | Use async sends, retries, buffer limits |
| Spike load at tick | Threads wake up simultaneously       | Add jitter to start times               |
| Thread starvation  | Too many tasks overwhelm CPU         | Limit threads, benchmark load           |
| Memory bloat       | If Kafka blocks, queue may grow      | Apply backpressure or drop policies     |
| Serialization cost | JSON is slow under heavy load        | Consider Avro/Protobuf later            |
| Logging overload   | Logging every event kills throughput | Log summaries only                      |

---

## 🚧 Implementation Strategy

1. ✅ Define `SensorProfile` and `SensorReadingEvent` Java models
2. ✅ Load static metadata from SQL or file
3. ✅ Build simulator core using `ScheduledExecutorService`
4. ✅ Implement KafkaProducer wrapper with async sending
5. ✅ Add logging, metrics, and config support
6. ✅ Scale test with 2M/hr, then tune for 10M/hr

---

## 🚧 Future Enhancements

- Switch from JSON to Protobuf or Avro
- Add event jitter, failure simulation
- Support dynamic sensor behavior (e.g., threshold alerts)
- Dynamic topology loading from DB or API
- Plug into TSDB (InfluxDB, TDengine) or Elastic

---

## 🛌 Conclusion

This simulator provides a scalable, configurable foundation for real-time IIoT data generation, helping test and validate production-grade pipelines. By using concurrency control, asynchronous streaming, and realistic device modeling, we ensure a performant and extensible simulation platform.
