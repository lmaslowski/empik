package empik.kata.restapi.users.infrastructure.web;

import empik.kata.restapi.users.model.domain.UserView;
import empik.kata.restapi.users.model.port.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
class UserResourceNonBlocking {

    private final UserQueryService<Mono<UserView>> userQueryServiceNonBlocking;

    @GetMapping(value = "/nonblocking/users/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<UserView> getReactive(@PathVariable("login") String login) {
        return userQueryServiceNonBlocking.getUser(login);
    }
}
