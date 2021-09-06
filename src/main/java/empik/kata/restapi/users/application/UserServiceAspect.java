package empik.kata.restapi.users.application;

import empik.kata.restapi.common.EventPublisher;
import empik.kata.restapi.users.model.domain.events.UserVisited;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Aspect
@Service
@AllArgsConstructor
public class UserServiceAspect {

    private final EventPublisher eventPublisher;

    @After("execution(* UserQueryService.getUser(..))")
    public void publish(JoinPoint joinPoint) {
        final Object[] args = joinPoint.getArgs();
        final String login = (String) args[0];
        eventPublisher.publish(new UserVisited(UUID.randomUUID(), login));
    }
}
