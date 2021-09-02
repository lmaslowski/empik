package empik.kata.restapi;

import empik.kata.restapi.counting.CountingConfig;
import empik.kata.restapi.counting.model.domain.Counter;
import empik.kata.restapi.counting.model.port.Counters;
import empik.kata.restapi.users.UsersConfig;
import empik.kata.restapi.users.model.domain.UserData;
import empik.kata.restapi.users.model.domain.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
        App.class,
        UsersConfig.class,
        CountingConfig.class
})
@SuppressWarnings("ConstantConditions")
class AppIT {

    @LocalServerPort
    private int port;

    @Autowired
    private Counters counters;

    @Autowired
    private TestRestTemplate rest;

    @Test
    void givenServiceIsWorking_whenRequestLogin_thenCounterShouldStore() {
        final String login = "octocat";
        final ResponseEntity<UserData> forEntity = rest.getForEntity("http://localhost:" + port + "/users/" + login, UserData.class);
        final UserData body = forEntity.getBody();

        assertEquals(login, body.getLogin());
        assertEquals(Optional.of(Counter.create(login, 1)), counters.findByLogin(login));

        rest.getForEntity("http://localhost:" + port + "/users/" + login, UserData.class);

        assertEquals(Optional.of(Counter.create(login, 2)), counters.findByLogin(login));
    }

    @Test
    void givenServiceIsWorking_whenRequestLoginThatNotExist_thenCounterNotStored() {
        final String login = "octocattnotexist01";

        assertThat(rest.getForObject("http://localhost:" + port + "/users/" + login, String.class))
                .contains(UserNotFoundException.class.getName());

        assertEquals(Optional.empty(), counters.findByLogin(login));
    }
}
