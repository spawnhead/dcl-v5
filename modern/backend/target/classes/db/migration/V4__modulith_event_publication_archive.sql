-- Spring Modulith event publication archive (ARCHIVE completion mode).
-- Same structure as event_publication for ArchivedJpaEventPublication.
CREATE TABLE event_publication_archive (
    id UUID NOT NULL PRIMARY KEY,
    publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
    listener_id VARCHAR(255) NOT NULL,
    serialized_event TEXT NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    completion_date TIMESTAMP WITH TIME ZONE,
    last_resubmission_date TIMESTAMP WITH TIME ZONE,
    completion_attempts INT NOT NULL DEFAULT 0,
    status VARCHAR(50) NOT NULL
);
