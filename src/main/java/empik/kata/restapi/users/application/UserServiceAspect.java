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
class UserServiceAspect {

    private final EventPublisher eventPublisher;

    @After("execution(* UserQueryServiceBlocking.getUser(..))")
    public void publish(JoinPoint joinPoint) {
        doExecute(joinPoint);
    }

    @After("execution(* UserQueryServiceNonBlocking.getUser(..))")
    public void publishNonBlocking(JoinPoint joinPoint) {
        doExecute(joinPoint);
    }

    private void doExecute(JoinPoint joinPoint) {
        final Object[] args = joinPoint.getArgs();
        final String login = (String) args[0];
        eventPublisher.publish(new UserVisited(UUID.randomUUID(), login));
    }
}
