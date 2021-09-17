package empik.kata.restapi.users.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor
@Builder
@ToString
public class User {

    private final String id;

    private final UserData userData;

    private final CalculatorPolicy calculatorPolicy;

    public UserView getUserView() {
        return UserView.builder()
                .id(userData.getId())
                .login(userData.getLogin())
                .name(userData.getName())
                .type(userData.getType())
                .avatarUrl(userData.getAvatarUrl())
                .createdAt(userData.getCreatedAt())
                .calculations(getCalculations())
                .build();
    }

    private double getCalculations() {
        return calculatorPolicy.calculate(userData.getFollowers(), userData.getPublicRepos());
    }
}
