package empik.kata.restapi.common;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {

    UUID getEventId();

    String getLogin();

    Instant getWhen();
}
