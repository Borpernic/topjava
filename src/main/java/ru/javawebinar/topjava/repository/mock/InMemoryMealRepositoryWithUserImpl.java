package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepositoryWithUser;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.ADMIN_USER_ID;
import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.USER_USER_ID;

@Repository
public class InMemoryMealRepositoryWithUserImpl implements MealRepositoryWithUser {

    private static final Logger log = getLogger(InMemoryMealRepositoryWithUserImpl.class);
    Comparator<Meal> mealComparator = (m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime());
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2017, Month.MAY, 30, 10, 0), "Завтрак User", 500), USER_USER_ID);
        save(new Meal(LocalDateTime.of(2017, Month.MAY, 30, 10, 0), "Обед User", 500), USER_USER_ID);
        save(new Meal(LocalDateTime.of(2017, Month.MAY, 30, 10, 0), "Ужин User", 500), USER_USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак Admin", 1500), ADMIN_USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Обед Admin", 1500), ADMIN_USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Ужин Admin", 1500), ADMIN_USER_ID);

    }

    @Override
    public Meal save(final Meal meal, final int userId) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (get(meal.getId(), userId) == null) {
            return null;
        }

        final Map<Integer, Meal> userMealMap = repository.computeIfAbsent(userId, ConcurrentHashMap::new);

        userMealMap.put(meal.getId(), meal);
        //repository.get(userId).put(userMealMap.size() + 1, meal);


        log.info("save {} User {} ", meal.getDescription(), userId);

        return meal;
    }

    @Override
    public void delete(final int mealId, final int userId) {

        repository.get(userId).remove(mealId);

    }

    @Override
    public Meal get(final int mealId, final int userId) {
        if (repository.get(userId) == null) return null;
        else {
            final Meal userMeal = repository.get(userId).get(mealId);
            if (userMeal == null) return null;
            else return userMeal;
        }

    }

    @Override
    public Collection<Meal> getAllByUser(final int userId) {


        return repository.get(userId).values().stream().sorted(mealComparator).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        final InMemoryMealRepositoryWithUserImpl repositoryWithUser = new InMemoryMealRepositoryWithUserImpl();
        repositoryWithUser.repository
                .values().stream().forEach(integerMealMap -> integerMealMap.values().stream().forEach(meal -> System.out.println(meal.toString())));
        System.out.println("" + repositoryWithUser.repository.values().toString());
    }
}

