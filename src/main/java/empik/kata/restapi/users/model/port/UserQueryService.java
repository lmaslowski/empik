package empik.kata.restapi.users.model.port;

public interface UserQueryService<T> {

     T getUser(String login);
}
