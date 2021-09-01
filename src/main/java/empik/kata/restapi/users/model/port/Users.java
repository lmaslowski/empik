package empik.kata.restapi.users.model.port;

import empik.kata.restapi.users.model.domain.User;

public interface Users {

    User find(String login);
}
