package empik.kata.restapi.users.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UserData {

    private final String id;

    private final String login;

    private final String name;

    private final String type;

    private final String avatarUrl;

    private final String createdAt;

    private final int followers;

    private final int publicRepos;
}
