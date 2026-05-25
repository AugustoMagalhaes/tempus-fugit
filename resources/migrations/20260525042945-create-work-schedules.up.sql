CREATE TABLE work_schedules (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    manager_id    UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name          VARCHAR(100) NOT NULL,
    daily_hours   NUMERIC(4,2) NOT NULL DEFAULT 8.0,
    created_at    TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP NOT NULL DEFAULT NOW()
);

--;;

CREATE TABLE work_schedule_days (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    schedule_id     UUID NOT NULL REFERENCES work_schedules(id) ON DELETE CASCADE,
    day_of_week     SMALLINT NOT NULL CHECK (day_of_week BETWEEN 0 AND 6),
    start_time      TIME NOT NULL,
    end_time        TIME NOT NULL,
    break_minutes   INTEGER NOT NULL DEFAULT 60,
    UNIQUE (schedule_id, day_of_week)
);

--;;

CREATE TABLE user_schedules (
    user_id      UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    schedule_id  UUID NOT NULL REFERENCES work_schedules(id) ON DELETE CASCADE,
    assigned_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id, schedule_id)
);