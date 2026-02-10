-- Dev-only: marker for dataMode=FAKE_SEEDED. Deterministic, no random data.
CREATE TABLE IF NOT EXISTS dev_seed_marker (
    id INTEGER PRIMARY KEY,
    seeded_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);

INSERT INTO dev_seed_marker (id, seeded_at)
VALUES (1, current_timestamp)
ON CONFLICT (id) DO NOTHING;
