package empik.kata.restapi.users.model.domain.events;

import empik.kata.restapi.common.DomainEvent;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@ToString
public class UserVisited implements DomainEvent {

    private final UUID eventId;
    private final String login;
    private final Instant occurredAt;

    public UserVisited(UUID eventId, String login) {
        this.eventId = eventId;
        this.login = login;
        this.occurredAt = Instant.now();
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Instant getWhen() {
        return occurredAt;
    }

    @Override
    public String getLogin() {
        return login;
    }
}
