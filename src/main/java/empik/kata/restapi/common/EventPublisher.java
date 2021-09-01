package empik.kata.restapi.common;

public interface EventPublisher {
    void publish(DomainEvent domainEvent);
}
