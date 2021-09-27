package empik.kata.restapi.users.infrastructure.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import empik.kata.restapi.users.UsersConfig;
import empik.kata.restapi.users.model.domain.User;
import empik.kata.restapi.users.model.domain.UserNotFoundException;
import empik.kata.restapi.users.model.port.Users;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static reactor.test.StepVerifier.create;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UsersConfig.class)
@TestPropertySource(locations = "classpath:application.properties")
class UserRepositoryNonBlockingTest {

    @Autowired
    private Users<Mono<User>> users;

    @Autowired
    private ObjectMapper objectMapper;

    private WireMockServer wireMockServer;

    @BeforeEach
    void before() {
        wireMockServer = new WireMockServer(7890);
        wireMockServer.start();
    }

    @Test
    void contextLoad() {
        assertNotNull(users);
    }

    @Test
    void getUser() throws IOException {
        String login = "octocat";
        byte[] body = this.getClass().getClassLoader().getResourceAsStream("users/octocat.json").readAllBytes();
        wireMockServer
                .stubFor(WireMock.get(WireMock.urlEqualTo("/users/" + login)).willReturn(
                        WireMock.aResponse().withStatus(HttpStatus.OK.ordinal())
                                .withHeader("content-type", ContentType.APPLICATION_JSON.toString())
                                .withBody(body)
                ));

        final Mono<User> userMono = users.find(login);

        create(userMono)
                .assertNext(user -> assertThat(user.getUserView().getLogin()).isEqualTo(login))
                .verifyComplete();
    }

    @Test
    void shouldThrowException_whenUserNotFound() throws IOException {
        String login = "octocat";
        byte[] body = this.getClass().getClassLoader().getResourceAsStream("users/octocat1.json").readAllBytes();
        wireMockServer
                .stubFor(WireMock.get(WireMock.urlEqualTo("/users/" + login)).willReturn(
                        WireMock.aResponse().withStatus(HttpStatus.OK.ordinal())
                                .withHeader("content-type", ContentType.APPLICATION_JSON.toString())
                                .withBody(body)
                ));

        final Mono<User> userMono = users.find(login);

        create(userMono)
                .expectError(UserNotFoundException.class)
                .verify();
    }

    @AfterEach
    void after() {
        wireMockServer.stop();
    }
}
