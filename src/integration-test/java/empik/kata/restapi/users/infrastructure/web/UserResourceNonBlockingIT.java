package empik.kata.restapi.users.infrastructure.web;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import empik.kata.restapi.users.UsersConfig;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings("ConstantConditions")
@RunWith(SpringRunner.class)
@WebFluxTest(controllers = UserResourceNonBlocking.class)
@ContextConfiguration(classes = UsersConfig.class)
class UserResourceNonBlockingIT {

    private WireMockServer wireMockServer;

    @Autowired
    private WebTestClient testClient;

    @Test
    void contextLoad() {
        assertNotNull(testClient);
    }

    @BeforeEach
    void before() {
        wireMockServer = new WireMockServer(7890);
        wireMockServer.start();
    }

    @Test
    void shouldReturnUserView() throws Exception {
        final String login = "octocat";
        final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("users/octocat.json");
        final byte[] bytes = resourceAsStream.readAllBytes();

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/users/" + login))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.ordinal())
                        .withHeader("content-type", ContentType.APPLICATION_JSON.toString())
                        .withBody(bytes)));


        testClient.get().uri("/nonblocking/users/" + login)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.login").isEqualTo(login)
                .jsonPath("$.type").isEqualTo("User")
                .jsonPath("$.avatarUrl").isNotEmpty()
                .jsonPath("$.createdAt").isNotEmpty()
                .jsonPath("$.calculations").isNotEmpty();
    }

    @Test
    void shouldThrowExceptionWhenUserNotExists() throws Exception {
        final String login = "octocattnotexist01";

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/users/" + login))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.ordinal())
                        .withHeader("content-type", ContentType.APPLICATION_JSON.toString())
                        .withBody(("nonvalidresponse").getBytes())));

        testClient.get().uri("/nonblocking/users/" + login)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.exception").isNotEmpty()
                .jsonPath("$.message").isEmpty();
    }

    @AfterEach
    void after() {
        wireMockServer.stop();
    }
}
