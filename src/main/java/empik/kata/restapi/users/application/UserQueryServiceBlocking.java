package empik.kata.restapi.users.application;

import empik.kata.restapi.users.model.domain.User;
import empik.kata.restapi.users.model.domain.UserNotFoundException;
import empik.kata.restapi.users.model.domain.UserView;
import empik.kata.restapi.users.model.port.UserQueryService;
import empik.kata.restapi.users.model.port.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserQueryServiceBlocking implements UserQueryService<UserView> {

    private final Users<Optional<User>> users;

    @Override
    public UserView getUser(String login) {
        return users.find(login).map(User::getUserView).orElseThrow(UserNotFoundException::new);
    }
}
