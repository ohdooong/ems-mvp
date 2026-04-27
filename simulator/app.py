import json
import random
import time
from datetime import datetime, timezone
from kafka import KafkaProducer


BOOTSTRAP_SERVERS = "localhost:29092"
TOPIC_NAME = "raw.sensor.energy"

DEVICE_COUNT = 20
SITE_IDS = ["SITE-A"]
ZONE_IDS = ["ZONE-01", "ZONE-02", "ZONE-03", "ZONE-04", "ZONE-05"]
STATUSES = ["NORMAL", "WARNING", "CRITICAL"]


def now_iso() -> str:
    return datetime.now(timezone.utc).replace(microsecond=0).isoformat().replace("+00:00", "Z")


def random_status(power_usage: float) -> str:
    if power_usage >= 250:
        return "CRITICAL"
    elif power_usage >= 180:
        return "WARNING"
    return "NORMAL"


def build_event(device_id: str) -> dict:
    power_usage = round(random.uniform(50, 300), 2)
    event_time = now_iso()
    ingestion_time = now_iso()

    return {
        "device_id": device_id,
        "site_id": random.choice(SITE_IDS),
        "zone_id": random.choice(ZONE_IDS),
        "power_usage": power_usage,
        "status": random_status(power_usage),
        "event_time": event_time,
        "ingestion_time": ingestion_time,
    }


def create_producer() -> KafkaProducer:
    return KafkaProducer(
        bootstrap_servers=BOOTSTRAP_SERVERS,
        value_serializer=lambda v: json.dumps(v).encode("utf-8"),
        acks="all",
        retries=3,
    )


def main():
    producer = create_producer()
    device_ids = [f"D-{i:04d}" for i in range(1, DEVICE_COUNT + 1)]

    print(f"Starting simulator -> topic={TOPIC_NAME}, bootstrap={BOOTSTRAP_SERVERS}")

    try:
        while True:
            for device_id in device_ids:
                event = build_event(device_id)
                producer.send(TOPIC_NAME, value=event, key=device_id.encode("utf-8"))
                print(event)

            producer.flush()
            time.sleep(1)

    except KeyboardInterrupt:
        print("Stopping simulator...")
    finally:
        producer.flush()
        producer.close()


if __name__ == "__main__":
    main()