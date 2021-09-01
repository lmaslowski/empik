package empik.kata.restapi.users.infrastructure.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UsersResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoad() {
        assertNotNull(port);
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnUserView() throws Exception {
        final String login = "octocat";

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

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + login).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.exception", notNullValue()))
                .andExpect(jsonPath("$.message", nullValue()));
    }
}
