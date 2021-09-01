package empik.kata.restapi.counting.model.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Counter {

    @Id
    private UUID id;

    private String login;

    private int counter;

    private Counter(String login) {
        this(login, 1);
    }

    private Counter(String login, int counter) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.counter = counter;
    }

    public Counter increment() {
        return new Counter(login, counter + 1);
    }

    public static Counter create(String login, int counter) {
        return new Counter(login, counter);
    }

    public static Counter create(String login) {
        return new Counter(login);
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Counter counter1 = (Counter) o;
        return counter == counter1.counter && Objects.equals(login, counter1.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, counter);
    }
}
