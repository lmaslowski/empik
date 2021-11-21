package empik.kata.restapi.users.infrastructure.web

import empik.kata.restapi.users.model.domain.UserNotFoundException
import empik.kata.restapi.users.model.domain.UserView
import empik.kata.restapi.users.model.port.UserQueryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserResource.class])
@ContextConfiguration(classes = WebConfiguration.class)
class UserResourceSpockIT extends Specification {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserQueryService<UserView> userQueryService;

    def "application should start properly"() {
        expect:
        null != mvc
        null != userQueryService
        println(userQueryService)
        println(mvc)
    }

    def "should return user view by login from application domain component and return 'ok' status"() {
        given:
        userQueryService.getUser("unclebob") >> UserView.builder()
                .id("id")
                .name("unclebob")
                .login("unclebob")
                .build();
        when:
        def actions = mvc.perform(MockMvcRequestBuilders.get("/users/" + "unclebob").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        then:
        actions.andDo(print())
        actions.andExpect(jsonPath('$.id').value("id"))
        actions.andExpect(jsonPath('$.id').isNotEmpty())
        actions.andExpect(jsonPath('$.name').value("unclebob"))
        actions.andExpect(jsonPath('$.login').value("unclebob"))
        actions.andExpect(jsonPath('$.type').value(null))
        actions.andExpect(jsonPath('$.avatarUrl').value(null))
        actions.andExpect(jsonPath('$.createdAt').value(null))
        actions.andExpect(jsonPath('$.calculations').value(0D))
        actions.andExpect(jsonPath('$.notexists').doesNotExist())
        actions.andExpect(jsonPath('$.notexists').doesNotHaveJsonPath())
        actions.andExpect(status().isOk())
    }

    def "should request user view by login to application domain component and return 'not found' status" () {
        given:
        userQueryService.getUser(_) >> {throw new UserNotFoundException()}
        when:
        def actions = mvc.perform(MockMvcRequestBuilders.get("/users/unclebob").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        then:
        actions.andDo(print())
        actions.andExpect(status().isNotFound())
    }

    def "should request user view without name param to domain component and return 'not found' status" () {
        when:
        def actions = mvc.perform(MockMvcRequestBuilders.get("/users/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        then:
        actions.andDo(print())
        actions.andExpect(status().isNotFound())
    }

    @TestConfiguration
    static class MockFactory {

        private DetachedMockFactory detachedMockFactory = new DetachedMockFactory();

        @Bean
        UserQueryService<UserView> userQueryService() {
            return detachedMockFactory.Stub(UserQueryService<UserView>.class)
        }

        /**
         * Need to mock all generics
         */
        @Bean
        UserQueryService<Mono<UserView>> monoUserQueryService() {
            return detachedMockFactory.Stub(UserQueryService<Mono<UserView>>.class)
        }
    }
}
