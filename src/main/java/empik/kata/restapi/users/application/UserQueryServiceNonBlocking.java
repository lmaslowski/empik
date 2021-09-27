package empik.kata.restapi.users.application;

import empik.kata.restapi.users.model.domain.User;
import empik.kata.restapi.users.model.domain.UserView;
import empik.kata.restapi.users.model.port.UserQueryService;
import empik.kata.restapi.users.model.port.Users;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@AllArgsConstructor
@Service
@Slf4j
class UserQueryServiceNonBlocking implements UserQueryService<Mono<UserView>> {

    private final Users<Mono<User>> users;

    @Override
    public Mono<UserView> getUser(String login) {
        return users
                .find(login)
                .log()
                .publishOn(Schedulers.parallel())
                .doOnNext(user -> log.info("Fetched User: {}", user))
                .map(User::getUserView)
                .doOnNext(userView -> log.info("Mapped to UserView: {}", userView))
                .doOnError(throwable -> log.error("{} {}", throwable.getClass().getCanonicalName(), throwable.getMessage()));
    }
}
