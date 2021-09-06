package empik.kata.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import empik.kata.restapi.counting.CountingConfig;
import empik.kata.restapi.counting.model.domain.Counter;
import empik.kata.restapi.counting.model.port.Counters;
import empik.kata.restapi.users.UsersConfig;
import empik.kata.restapi.users.infrastructure.web.ExceptionHandlingAdvice;
import empik.kata.restapi.users.model.domain.UserData;
import empik.kata.restapi.users.model.domain.UserNotFoundException;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("ConstantConditions")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
        App.class,
        UsersConfig.class,
        CountingConfig.class
})
class AppIT {

    private WireMockServer wireMockServer;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private Counters counters;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void before() {
        wireMockServer = new WireMockServer(7890);
        wireMockServer.start();
    }

    @Test
    void contextLoad() {
        assertThat(testRestTemplate).isNotNull();
        assertThat(wireMockServer).isNotNull();
        assertThat(objectMapper).isNotNull();
    }

    @Test
    void givenServiceIsWorking_whenRequestLogin_thenCounterShouldStore() throws Exception {
        final String login = "octocat";
        final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("users/octocat.json");
        final byte[] bytes = resourceAsStream.readAllBytes();

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/users/" + login))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.ordinal())
                        .withHeader("content-type", ContentType.APPLICATION_JSON.toString())
                        .withBody(bytes)));

        assertThat(testRestTemplate.getForObject("http://localhost:" + port + "/users/" + login, String.class)).contains(login);

        assertEquals(Optional.of(Counter.create(login, 1)), counters.findByLogin(login));

        testRestTemplate.getForEntity("http://localhost:" + port + "/users/" + login, UserData.class);

        assertEquals(Optional.of(Counter.create(login, 2)), counters.findByLogin(login));
    }

    @Test
    void givenServiceIsWorking_whenRequestLoginThatNotExist_thenCounterNotStored() {
        final String login = "octocat";
      
        final ExceptionHandlingAdvice.ErrorView errorViewExpected = ExceptionHandlingAdvice.ErrorView
                .builder()
                .exception((UserNotFoundException.class.getCanonicalName())).build();

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/users/" + login))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.ordinal())
                        .withHeader("content-type", ContentType.APPLICATION_JSON.toString())
                        .withBody(("nonvalidresponse").getBytes())));

        assertThat(testRestTemplate.getForObject("http://localhost:" + port + "/users/" + login,
                ExceptionHandlingAdvice.ErrorView.class))
                .isEqualTo(errorViewExpected);

        assertEquals(Optional.empty(), counters.findByLogin(login));
    }

    @AfterEach
    void after() {
        wireMockServer.stop();
    }
}
