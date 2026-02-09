-- Spring Modulith event publication registry (JPA).
-- Required by spring-modulith-starter-jpa for transactional event listener log.
-- Ref: https://docs.spring.io/spring-modulith/reference/events.html
CREATE TABLE event_publication (
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
