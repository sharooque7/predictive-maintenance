-- Asset Management Module Database Schema

-- 1. PLC Table
CREATE TABLE plc (
    id UUID PRIMARY KEY,
    plc_name VARCHAR(100) NOT NULL,
    ip_address VARCHAR(45) NOT NULL,
    protocol VARCHAR(20) NOT NULL CHECK (protocol IN ('opcua', 'modbus', 'mqtt')),
    location VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Asset Table
CREATE TABLE asset (
    id UUID PRIMARY KEY,
    plc_id UUID NOT NULL REFERENCES plc(id) ON DELETE CASCADE,
    asset_name VARCHAR(100) NOT NULL,
    type VARCHAR(50),
    location VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Sensor Table
CREATE TABLE sensor (
    id UUID PRIMARY KEY,
    asset_id UUID NOT NULL REFERENCES asset(id) ON DELETE CASCADE,
    sensor_type VARCHAR(50) NOT NULL,
    unit VARCHAR(20),
    reading_range JSONB, -- e.g., {"min": 0, "max": 100}
    signal_type VARCHAR(20) CHECK (signal_type IN ('analog', 'digital', 'virtual')),
    tag_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
