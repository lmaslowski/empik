package empik.kata.restapi.users.model.port;

public interface Users<T> {

    T find(String login);
}
