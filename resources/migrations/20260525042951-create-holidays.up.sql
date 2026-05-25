CREATE TABLE holidays (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    manager_id   UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name         VARCHAR(100) NOT NULL,
    date         DATE NOT NULL,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW()
);