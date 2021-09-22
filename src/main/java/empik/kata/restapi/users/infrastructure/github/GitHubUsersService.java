package empik.kata.restapi.users.infrastructure.github;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
class GitHubUsersService implements GitHubUsers<Optional<GitHubUser>> {

    private final String httpApiGithubUsers;
    private final RestTemplate restTemplate;

    public GitHubUsersService(@Value("${api.github.url}") String httpApiGithubUsers, RestTemplate restTemplate) {
        this.httpApiGithubUsers = httpApiGithubUsers;
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<GitHubUser> findByLogin(String login) {
        try {
            return Optional.ofNullable(restTemplate.getForEntity(String.format(httpApiGithubUsers, login), GitHubUser.class)
                    .getBody());
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }
}
