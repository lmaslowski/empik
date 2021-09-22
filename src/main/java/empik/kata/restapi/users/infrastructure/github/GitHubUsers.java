package empik.kata.restapi.users.infrastructure.github;

interface GitHubUsers<T> {

    T findByLogin(String login);
}
