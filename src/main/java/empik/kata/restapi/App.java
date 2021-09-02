package empik.kata.restapi;

import empik.kata.restapi.counting.CountingConfig;
import empik.kata.restapi.users.UsersConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootConfiguration
@EnableAutoConfiguration
public class App {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(App.class)
                .child(UsersConfig.class).web(WebApplicationType.SERVLET)
                .sibling(CountingConfig.class).web(WebApplicationType.NONE)
                .run(args);
    }
}
