package empik.kata.restapi.counting.application;

import empik.kata.restapi.counting.model.domain.Counter;
import empik.kata.restapi.counting.model.port.Counters;
import empik.kata.restapi.users.model.domain.events.UserVisited;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserEventHandlerTest {

    @Mock
    private Counters repository;

    private UserEventHandler userEventHandler;

    @BeforeEach
    void before() {
        userEventHandler = new UserEventHandler(repository);
    }

    @Test
    void contextLoad() {
        assertNotNull(repository);
        assertNotNull(userEventHandler);
    }

    @Test
    void whenLoginDoesNotExist_thenCreateAndStoreVisitCounter() {
        final String login = "octocat";

        Optional<Counter> counterOptional = Optional.empty();

        when(repository.findByLogin(Mockito.anyString())).thenReturn(counterOptional);

        userEventHandler.handle(new UserVisited(UUID.randomUUID(), login));

        verify(repository, times(1)).save(Counter.create(login, 1));
    }

    @Test
    void whenLoginExist_thenCreateAndStoreVisitCounter() {
        final String login = "octocat";

        Optional<Counter> counterOptional = Optional.of(Counter.create(login, 1));

        when(repository.findByLogin(Mockito.anyString())).thenReturn(counterOptional);

        userEventHandler.handle(new UserVisited(UUID.randomUUID(), login));

        verify(repository, times(1)).save(Counter.create(login, 2));
    }
}
