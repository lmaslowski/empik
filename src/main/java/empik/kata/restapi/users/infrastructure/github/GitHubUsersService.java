package empik.kata.restapi.users.infrastructure.github;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@AllArgsConstructor
@Service
class GitHubUsersService implements GitHubUsers {

    private static final String HTTPS_API_GITHUB_COM_USERS_S = "https://api.github.com/users/%s";
    private final RestTemplate restTemplate;

    @Override
    public Optional<GitHubUser> findByLogin(String login) {
        try {
            return Optional.ofNullable(restTemplate.getForEntity(String.format(HTTPS_API_GITHUB_COM_USERS_S, login), GitHubUser.class).getBody());
        } catch (RestClientException e) {
//            throw e;
            return Optional.empty();
        }
    }
}
