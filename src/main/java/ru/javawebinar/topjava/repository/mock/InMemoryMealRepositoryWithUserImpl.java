package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryWithUser;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealRepositoryWithUserImpl implements MealRepositoryWithUser {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryWithUserImpl.class);
    private Map<Integer, Meal> mealsRepository = new ConcurrentHashMap<>();
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
   /*     InMemoryUserRepositoryImpl.
        mealsRepository.forEach((integer, meal) ->
                InMemoryUserRepositoryImpl.USERS.forEach(user -> {
                    InMemoryUserRepositoryImpl::save;
                    save(meal, user);
                }
                    ));*/
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        mealsRepository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return mealsRepository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return mealsRepository.values();
    }

    @Override
    public Meal save(final Meal meal, final User user) {
        log.info("save {} User {}", meal, user);

        return meal;
    }

    @Override
    public void delete(final int id, final User user) {

    }

    @Override
    public Meal get(final int id, final User user) {
        return null;
    }

    @Override
    public Collection<Meal> getAllByUser(final User user) {
        return null;
    }

    public static void main(String[] args) {
        final InMemoryMealRepositoryWithUserImpl repositoryWithUser = new InMemoryMealRepositoryWithUserImpl();
                repositoryWithUser.repository
                .values().stream().forEach(integerMealMap -> integerMealMap.values().stream().forEach(meal -> System.out.println(meal.toString())));
        System.out.println(repositoryWithUser.repository.values().toString());
    }
}

