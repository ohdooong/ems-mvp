## Flink Job
### SensorLatestJob (센서 최신상태 집계)
`SensorLatestJob`은 Kafka topic `raw.sensor.energy`에서 센서 이벤트를 읽어 설비별 최신 상태를 PostgreSQL에 저장하는 Job입니다.

- Kafka Source에서 JSON 이벤트 consume
- JSON → `SensorEvent` 파싱
- `device_id` 기준 `sensor_latest` 테이블 upsert
- 최신 상태 조회를 위한 serving table 구성


### SensorEnergyAgg1mJob (센서 에니저 1분 평균 집계)
`EnergyAgg1mJob`은 센서 이벤트를 `event_time` 기준으로 1분 단위 집계하는 Job입니다.

- Kafka Source에서 JSON 이벤트 consume
- `event_time` 기반 watermark 적용
- `site_id + zone_id` 기준 keyBy
- 1분 tumbling event-time window 적용
- 평균 전력 사용량, 총 전력 사용량, 이벤트 수 계산
- `energy_agg_1m` 테이블 upsert
