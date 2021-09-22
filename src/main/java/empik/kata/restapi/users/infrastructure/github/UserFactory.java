package empik.kata.restapi.users.infrastructure.github;

import empik.kata.restapi.users.model.domain.CalculatorPolicy;
import empik.kata.restapi.users.model.domain.User;
import empik.kata.restapi.users.model.domain.UserData;
import empik.kata.restapi.users.model.domain.policies.DefaultCalculator;
import org.springframework.stereotype.Service;

@Service
class UserFactory {

    User build(GitHubUser gitHubUser) {
        return build(gitHubUser, new DefaultCalculator());
    }

    private User build(GitHubUser gitHubUser, CalculatorPolicy calculatorPolicy) {
        final UserData userData = map(gitHubUser);

        return User.builder()
                .id(userData.getId())
                .userData(userData)
                .calculatorPolicy(calculatorPolicy)
                .build();
    }

    private UserData map(GitHubUser gitHubUser) {
        return UserData.builder()
                .id(gitHubUser.getId())
                .login(gitHubUser.getLogin())
                .name(gitHubUser.getName())
                .type(gitHubUser.getType())
                .avatarUrl(gitHubUser.getAvatarUrl())
                .createdAt(gitHubUser.getCreatedAt())
                .followers(gitHubUser.getFollowers())
                .publicRepos(gitHubUser.getPublicRepos())
                .build();
    }
}
