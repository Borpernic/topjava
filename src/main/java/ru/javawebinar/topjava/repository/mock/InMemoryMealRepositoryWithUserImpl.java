package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepositoryWithUser;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepositoryWithUserImpl implements MealRepositoryWithUser {

    private static final Logger log = getLogger(InMemoryMealRepositoryWithUserImpl.class);
    Comparator<Meal> mealComparator = (m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime());
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {

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
        repository.get(userId).put(userMealMap.size() + 1, meal);


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


        return repository.get(userId).values();
    }

    /*@Override
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
    }*/
    public static void main(String[] args) {
        final InMemoryMealRepositoryWithUserImpl repositoryWithUser = new InMemoryMealRepositoryWithUserImpl();
        repositoryWithUser.repository
                .values().stream().forEach(integerMealMap -> integerMealMap.values().stream().forEach(meal -> System.out.println(meal.toString())));
        System.out.println(repositoryWithUser.repository.values().toString());
    }
}

