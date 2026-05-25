CREATE TYPE punch_type AS ENUM ('clock_in', 'clock_out', 'break_start', 'break_end');
--;;
CREATE TYPE punch_status AS ENUM ('pending', 'approved', 'rejected');

--;;

CREATE TABLE punches (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id      UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    punched_at   TIMESTAMP NOT NULL,
    type         punch_type NOT NULL,
    is_manual    BOOLEAN NOT NULL DEFAULT FALSE,
    status       punch_status NOT NULL DEFAULT 'approved',
    note         TEXT,
    reviewed_by  UUID REFERENCES users(id) ON DELETE SET NULL,
    reviewed_at  TIMESTAMP,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW()
);

--;;

CREATE INDEX idx_punches_user_id ON punches(user_id);
--;;
CREATE INDEX idx_punches_punched_at ON punches(punched_at);