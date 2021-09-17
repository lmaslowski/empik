package empik.kata.restapi.users.infrastructure.github;

import empik.kata.restapi.users.model.domain.User;
import empik.kata.restapi.users.model.port.Users;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@AllArgsConstructor
@Repository
@Slf4j
public class UserRepositoryNonBlocking implements Users<Mono<User>> {

    private final GitHubUsers<Mono<GitHubUser>> gitHubUsers;
    private final UserFactory userFactory;

    public Mono<User> find(String login) {
        return gitHubUsers
                .findByLogin(login)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(gitHubUser -> log.info("Fetched github user: {}", gitHubUser))
                .map(userFactory::build);
    }

}
