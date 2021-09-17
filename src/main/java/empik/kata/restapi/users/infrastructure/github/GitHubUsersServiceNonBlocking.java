package empik.kata.restapi.users.infrastructure.github;

import empik.kata.restapi.users.model.domain.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class GitHubUsersServiceNonBlocking implements GitHubUsers<Mono<GitHubUser>> {

    private final String httpApiGithubUsers;
    private final RestTemplate restTemplate;

    public GitHubUsersServiceNonBlocking(@Value("${api.github.url}") String httpApiGithubUsers, RestTemplate restTemplate) {
        this.httpApiGithubUsers = httpApiGithubUsers;
        this.restTemplate = restTemplate;
    }

    @Override
    public Mono<GitHubUser> findByLogin(String login) {
        return Mono.fromCallable(() -> this.a(login));
    }

    private GitHubUser a(String login) {
        try {
            return restTemplate.getForEntity(String.format(httpApiGithubUsers, login), GitHubUser.class).getBody();
        } catch (RestClientException e) {
            throw new UserNotFoundException();
        }
    }

    private Optional<GitHubUser> aOptional(String login) {
        try {
            return Optional.ofNullable(restTemplate.getForEntity(String.format(httpApiGithubUsers, login), GitHubUser.class).getBody());
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }
}
