package empik.kata.restapi.users.infrastructure.github;

import empik.kata.restapi.users.model.domain.User;
import empik.kata.restapi.users.model.domain.UserNotFoundException;
import empik.kata.restapi.users.model.port.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserRepository implements Users {

    private final GitHubUsers gitHubUsers;
    private final UserFactory userFactory;

    @Override
    public User find(String login) {
        return gitHubUsers.findByLogin(login).map(userFactory::build).orElseThrow(UserNotFoundException::new);
    }
}
