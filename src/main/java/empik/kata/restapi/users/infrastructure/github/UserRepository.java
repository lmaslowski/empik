package empik.kata.restapi.users.infrastructure.github;

import empik.kata.restapi.users.model.domain.User;
import empik.kata.restapi.users.model.port.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepository implements Users<Optional<User>> {

    private final GitHubUsers<Optional<GitHubUser>> gitHubUsers;
    private final UserFactory userFactory;

    @Override
    public Optional<User> find(String login) {
        return gitHubUsers.findByLogin(login).map(userFactory::build);
    }
}
