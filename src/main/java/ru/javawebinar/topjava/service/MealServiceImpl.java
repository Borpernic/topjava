package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryWithUser;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepositoryWithUser repository;

    @Override
    public Meal save(final Meal meal, final int userId) {

        return repository.save(meal, userId);
    }

    @Override
    public void delete(final int mealId, final int userId) throws NotFoundException {

        checkUser(userId);
        repository.delete(mealId, userId);
    }

    @Override
    public Meal get(final int mealId, final int userId) throws NotFoundException {
        checkUser(userId);
        return repository.get(mealId, userId);
    }

    @Override
    public List<MealWithExceed> getByDate(LocalTime startTime, LocalTime endTime, int caloriesPerDay, final int userId) throws NotFoundException {
        checkUser(userId);

        return getFilteredWithExceeded(repository.getAllByUser(userId), startTime, endTime, caloriesPerDay);

    }

    @Override
    public void update(final Meal meal, final int userId) {
        checkUser(userId);
        repository.save(meal, userId);
    }

    @Override
    public Collection<Meal> getAllByUser(final int userId) {

        checkUser(userId);

        return repository.getAllByUser(userId);
    }

    private void checkUser(final int userId) {
        checkNotFoundWithId(userId != AuthorizedUser.id(), userId);
        /*if (userId != AuthorizedUser.id()) throw new NotFoundException("User не залогинен");*/
    }
}