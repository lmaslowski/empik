package empik.kata.restapi.users.model.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder(access = AccessLevel.PUBLIC)
@Getter
@ToString
public class UserView {

    private final String id;

    private final String login;

    private final String name;

    private final String type;

    private final String avatarUrl;

    private final String createdAt;

    private final double calculations;

}
