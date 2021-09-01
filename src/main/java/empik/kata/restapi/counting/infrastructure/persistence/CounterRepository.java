package empik.kata.restapi.counting.infrastructure.persistence;

import empik.kata.restapi.counting.model.domain.Counter;
import empik.kata.restapi.counting.model.port.Counters;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CounterRepository implements Counters {

    private final CounterSpringDataRepository counterSpringDataRepository;

    @Override
    public Optional<Counter> findByLogin(String login) {
        return counterSpringDataRepository.findByLogin(login);
    }

    @Override
    public void save(Counter counter) {
        counterSpringDataRepository.save(counter);
    }

    @Override
    public void delete(Counter counter) {
        counterSpringDataRepository.delete(counter);
    }
}
