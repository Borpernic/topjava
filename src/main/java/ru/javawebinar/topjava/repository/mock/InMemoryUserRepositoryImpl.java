package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = getLogger(InMemoryUserRepositoryImpl.class);

    public static final List<User> USERS = Arrays.asList(
            new User("Max_Admin", "Max_Admin@gmail.com", "123", Role.ROLE_ADMIN),
            new User("Max_User", "Max_User@gmail.com", "456", Role.ROLE_USER)
    );


    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        USERS.forEach(this::save);
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);


        log.info("save {}", user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        repository.remove(id);

        log.info("delete {}", id);
        return !(repository.remove(id).equals( null));
    }

    @Override
    public User get(int id) {

        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);

        return repository.values().stream().filter(user -> user.getEmail()==email).findFirst().get();
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        InMemoryUserRepositoryImpl inMemoryUserRepository = new InMemoryUserRepositoryImpl();
        inMemoryUserRepository.repository.values().stream().forEach(System.out::println);

    }
}
