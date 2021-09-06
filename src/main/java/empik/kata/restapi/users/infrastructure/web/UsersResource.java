package empik.kata.restapi.users.infrastructure.web;

import empik.kata.restapi.users.application.UserQueryService;
import empik.kata.restapi.users.model.domain.UserView;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
class UsersResource {

    private final UserQueryService userQueryService;

    @GetMapping(value = "/users/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserView get(@PathVariable("login") String login) {
        return userQueryService.getUser(login);
    }

}