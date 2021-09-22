package empik.kata.restapi.common.publisher;

import empik.kata.restapi.common.DomainEvent;
import empik.kata.restapi.common.EventPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SpringEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent domainEvent) {
        log.info("Publish : " + domainEvent);
        applicationEventPublisher.publishEvent(domainEvent);
    }
}
