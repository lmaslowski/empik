package empik.kata.restapi.users.application;

import empik.kata.restapi.users.model.domain.User;
import empik.kata.restapi.users.model.domain.UserNotFoundException;
import empik.kata.restapi.users.model.domain.UserView;
import empik.kata.restapi.users.model.port.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserQueryService {

    private final Users users;

    public UserView getUser(String login) {
        return users.find(login).map(User::getUserView).orElseThrow(UserNotFoundException::new);
    }
}
