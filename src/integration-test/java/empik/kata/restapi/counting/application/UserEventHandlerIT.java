package empik.kata.restapi.counting.application;

import empik.kata.restapi.common.EventPublisher;
import empik.kata.restapi.counting.model.domain.Counter;
import empik.kata.restapi.counting.model.port.Counters;
import empik.kata.restapi.users.model.domain.events.UserVisited;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class UserEventHandlerIT {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private UserEventHandler userEventHandler;

    @Autowired
    private Counters repository;

    @Test
    void contextLoad() {
        assertNotNull(eventPublisher);
        assertNotNull(userEventHandler);
        assertNotNull(repository);
    }

    @Test
    void eventPublisherTest() {
        final String login = "octocat";

        eventPublisher.publish(new UserVisited(UUID.randomUUID(), login));
        assertEquals(repository.findByLogin(login), Optional.of(Counter.create(login, 1)));

        eventPublisher.publish(new UserVisited(UUID.randomUUID(), login));
        assertEquals(repository.findByLogin(login), Optional.of(Counter.create(login, 2)));
    }

    @Test
    void eventHandlerTest() {
        final String login = "octocat";

        userEventHandler.handle(new UserVisited(UUID.randomUUID(), login));
        assertEquals(repository.findByLogin(login), Optional.of(Counter.create(login, 1)));

        userEventHandler.handle(new UserVisited(UUID.randomUUID(), login));
        assertEquals(repository.findByLogin(login), Optional.of(Counter.create(login, 2)));
    }
}
