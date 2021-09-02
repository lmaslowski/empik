package empik.kata.restapi.counting.application;

import empik.kata.restapi.counting.model.domain.Counter;
import empik.kata.restapi.counting.model.port.Counters;
import empik.kata.restapi.users.model.domain.events.UserVisited;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@AllArgsConstructor
@Transactional
class UserEventHandler {

    private final Counters counters;

    @EventListener
    void handle(UserVisited event) {
        final String login = event.getLogin();
        final Counter counter = counters.findByLogin(login)
                .map(transform())
                .orElseGet(() -> Counter.create(event.getLogin()));
        counters.save(counter);
    }

    private Function<Counter, Counter> transform() {
        return c -> {
            counters.delete(c);
            return c.increment();
        };
    }

}
