**Industrial IoT System Design Document: OT Side (Operational Technology)**

---

### 1. Overview

This document outlines the OT-side architecture and data flow design for a high-throughput Industrial IoT (IIoT) system, capable of processing 1M+ events per hour. The focus is on data acquisition from physical sensors up to the edge computing layer.

---

### 2. OT Data Flow Summary

```
[Sensors]
   │ (Electrical signals: 4–20 mA, 0–10V, Digital, Pulse)
   │
[PLC (Programmable Logic Controller)]
   │ (Signal conversion to engineering values)
   │
[OPC UA Server / Modbus Registers]
   │ (Standardized data exposure)
   │
[Edge Agent / PLC Client (Java)]
   │ (Polling/subscribing to data)
   │
[Kafka / MQTT Broker]
```

---

### 3. Component Breakdown

#### 3.1 Sensors

* Types: Temperature, Vibration, Pressure, Flow, RPM, Current, Proximity, Humidity
* Output Formats: Analog (4–20 mA, 0–10V), Digital (On/Off), Pulse (Hz)
* Quantity: \~3200 sensors (400 machines × 8 sensors each)

#### 3.2 PLCs

* Estimated Count: 100 PLCs (each handles \~32 sensors)
* Role:

    * Acquire electrical signals
    * Use ADCs to convert to digital values
    * Scale values to engineering units (e.g., 4 mA = 0°C, 20 mA = 200°C)
    * Expose values via OPC UA or Modbus

#### 3.3 OPC UA Server (on PLC)

* Exposes sensor values as structured, typed nodes
* Supports:

    * Data browsing (NodeID-based)
    * Secure communication (TLS, certs)
    * Subscription-based updates

#### 3.4 Edge Agent (OPC UA Client)

* Runs on edge server/gateway
* Responsibilities:

    * Connects to 1+ PLCs
    * Reads or subscribes to selected nodes
    * Packages values into JSON payloads
    * Publishes to Kafka or MQTT

---

### 4. Data Frequency & Volume

* Sensor Frequency: Every 5 seconds
* Per PLC: 32 sensors × 12 readings/minute = 384 readings/minute
* Total System: 3200 sensors × 12 = 38,400 readings/minute ≈ 2.3M/hour

---

### 5. Communication Protocols

| Layer        | Protocol         | Direction    |
| ------------ | ---------------- | ------------ |
| Sensor → PLC | Analog / Digital | Electrical   |
| PLC → Edge   | OPC UA / Modbus  | TCP (binary) |
| Edge → Cloud | Kafka / MQTT     | TCP (JSON)   |

---

### 6. Security & Reliability

* PLC to Client:

    * OPC UA with TLS encryption
    * Role-based authentication
* Client to Kafka:

    * Kafka SSL or SASL mechanisms
    * Retry & buffering on failure
* Deployment:

    * Edge agents deployed per factory unit or zone
    * Redundant edge gateways in critical zones

---

### 7. Tooling Recommendations

* **Java OPC UA Client:** Eclipse Milo
* **OPC UA Simulator (for testing):** Prosys OPC UA Server or Milo Test Server
* **Edge Collector Framework (custom or Node-RED)**

---

### 8. Next Steps

* Finalize sensor-to-PLC mappings
* Identify simulation requirements
* Confirm Kafka topics & schemas
* Begin development of edge agent

---

Prepared for Design Review
Date: 2025-06-23
Author: Mohammed Sharooque
