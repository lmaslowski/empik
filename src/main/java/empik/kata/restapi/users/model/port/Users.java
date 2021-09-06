package empik.kata.restapi.users.model.port;

import empik.kata.restapi.users.model.domain.User;

import java.util.Optional;

public interface Users {

    Optional<User> find(String login);
}
