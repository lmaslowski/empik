package empik.kata.restapi.counting.infrastructure.persistence;

import empik.kata.restapi.counting.model.domain.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CounterSpringDataRepository extends JpaRepository<Counter, Long> {

    Optional<Counter> findByLogin(String login);
}
