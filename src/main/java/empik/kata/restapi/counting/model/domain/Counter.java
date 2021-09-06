package empik.kata.restapi.counting.model.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Counter {

    @Id
    private UUID id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "REQUEST_COUNT")
    private int requestCount;

    private Counter(String login) {
        this(login, 1);
    }

    private Counter(String login, int requestCount) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.requestCount = requestCount;
    }

    public Counter increment() {
        return new Counter(login, requestCount + 1);
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
        return requestCount == counter1.requestCount && Objects.equals(login, counter1.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, requestCount);
    }
}
