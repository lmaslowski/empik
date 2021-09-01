package empik.kata.restapi.users.infrastructure.github;

import java.util.Optional;

interface GitHubUsers {

    Optional<GitHubUser> findByLogin(String login);
}
