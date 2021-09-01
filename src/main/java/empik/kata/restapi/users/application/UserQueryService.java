package empik.kata.restapi.users.application;

import empik.kata.restapi.common.EventPublisher;
import empik.kata.restapi.users.model.domain.UserView;
import empik.kata.restapi.users.model.domain.events.UserVisited;
import empik.kata.restapi.users.model.port.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserQueryService {

    private final Users users;
    private final EventPublisher eventPublisher;

    public UserView getUser(String login) {
        final UserView userView = users.find(login).getUserView();
        eventPublisher.publish(new UserVisited(UUID.randomUUID(), login));
        return userView;
    }

}
