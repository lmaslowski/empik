package empik.kata.restapi.users.application;


import empik.kata.restapi.users.UsersConfig;
import empik.kata.restapi.users.model.domain.User;
import empik.kata.restapi.users.model.domain.UserData;
import empik.kata.restapi.users.model.domain.UserNotFoundException;
import empik.kata.restapi.users.model.domain.UserView;
import empik.kata.restapi.users.model.domain.policies.DefaultCalculator;
import empik.kata.restapi.users.model.port.UserQueryService;
import empik.kata.restapi.users.model.port.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UsersConfig.class)
@TestPropertySource(properties = {"api.github.url=http://localhost:7890/users/%s"})
class UserQueryServiceNonBlockingTest {

    @MockBean
    private Users<Mono<User>> users;

    @Autowired
    private UserQueryService<Mono<UserView>> userQueryServiceNonBlocking;

    @Test
    void contextLoad() {
        assertNotNull(userQueryServiceNonBlocking);
    }

    @Test
    void getUser() {
        final User user = User
                .builder()
                .id("id")
                .userData(UserData.builder().build())
                .calculatorPolicy(new DefaultCalculator())
                .build();

        when(users.find(anyString())).thenReturn(Mono.just(user));

        create(userQueryServiceNonBlocking.getUser("login"))
                .expectNext(user.getUserView())
                .verifyComplete();
    }


    @Test
    void shouldThrowException_whenUserNotFound() {
        when(users.find(anyString())).thenReturn(Mono.error(new UserNotFoundException()));

        create(userQueryServiceNonBlocking.getUser("login"))
                .expectError(UserNotFoundException.class)
                .verify();
    }

}
