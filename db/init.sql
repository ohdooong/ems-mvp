CREATE TABLE IF NOT EXISTS sensor_latest (
    id BIGSERIAL PRIMARY KEY,
    device_id VARCHAR(50),
    site_id VARCHAR(50) NOT NULL,
    zone_id VARCHAR(50) NOT NULL,
    power_usage NUMERIC(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    event_time TIMESTAMP NOT NULL,
    ingestion_time TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);