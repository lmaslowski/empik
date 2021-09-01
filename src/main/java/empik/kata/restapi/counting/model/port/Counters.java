package empik.kata.restapi.counting.model.port;

import empik.kata.restapi.counting.model.domain.Counter;

import java.util.Optional;

public interface Counters {

    Optional<Counter> findByLogin(String login);

    void save(Counter counter);

    void delete(Counter counter);
}
