package empik.kata.restapi.users.infrastructure.web;

import empik.kata.restapi.users.UsersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;

@WebFluxTest(controllers = UserResourceNonBlocking.class)
@ContextConfiguration(classes = UsersConfig.class)
class UserRepositoryNonBlockingTest {

    @Test
    void contextLoad() {}
}
