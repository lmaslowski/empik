package empik.kata.restapi.users.infrastructure.publisher;

import empik.kata.restapi.common.DomainEvent;
import empik.kata.restapi.common.EventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpringEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent domainEvent) {
        applicationEventPublisher.publishEvent(domainEvent);
    }
}
