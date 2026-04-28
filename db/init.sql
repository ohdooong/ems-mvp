CREATE TABLE IF NOT EXISTS sensor_latest (
    device_id VARCHAR(50) PRIMARY KEY,
    site_id VARCHAR(50) NOT NULL,
    zone_id VARCHAR(50) NOT NULL,
    power_usage NUMERIC(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    event_time TIMESTAMP NOT NULL,
    ingestion_time TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS energy_agg_1m (
    site_id VARCHAR(50) NOT NULL,
    zone_id VARCHAR(50) NOT NULL,
    window_start TIMESTAMP NOT NULL,
    window_end TIMESTAMP NOT NULL,
    avg_power_usage NUMERIC(10,2) NOT NULL,
    total_power_usage NUMERIC(12,2) NOT NULL,
    event_count BIGINT NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (site_id, zone_id, window_start, window_end)
);