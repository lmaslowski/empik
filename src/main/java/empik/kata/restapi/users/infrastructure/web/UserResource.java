package empik.kata.restapi.users.infrastructure.web;

import empik.kata.restapi.users.model.domain.UserView;
import empik.kata.restapi.users.model.port.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
class UserResource {

    private final UserQueryService<UserView> userQueryServiceBlocking;

    @GetMapping(value = "/users/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserView get(@PathVariable("login") String login) {
        return userQueryServiceBlocking.getUser(login);
    }
}