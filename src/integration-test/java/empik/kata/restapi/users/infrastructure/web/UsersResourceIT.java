package empik.kata.restapi.users.infrastructure.web;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import empik.kata.restapi.users.UsersConfig;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SuppressWarnings("ConstantConditions")
@SpringBootTest(classes = UsersConfig.class)
@AutoConfigureMockMvc
class UsersResourceIT {

    private WireMockServer wireMockServer;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoad() {
        assertNotNull(mockMvc);
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


        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + login).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.login", is(login)))
                .andExpect(jsonPath("$.type", is("User")))
                .andExpect(jsonPath("$.avatarUrl", notNullValue()))
                .andExpect(jsonPath("$.createdAt", notNullValue()))
                .andExpect(jsonPath("$.calculations", notNullValue()));
    }

    @Test
    void shouldThrowExceptionWhenUserNotExists() throws Exception {
        final String login = "octocattnotexist01";

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/users/" + login))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.ordinal())
                        .withHeader("content-type", ContentType.APPLICATION_JSON.toString())
                        .withBody(("nonvalidresponse").getBytes())));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + login).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.exception", notNullValue()))
                .andExpect(jsonPath("$.message", nullValue()));
    }

    @AfterEach
    void after() {
        wireMockServer.stop();
    }
}
