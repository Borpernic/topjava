package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealService {

    Meal save(Meal meal, int userId);

    void delete(int mealId, int userId) throws NotFoundException;

    Meal get(int mealId, int userId) throws NotFoundException;

    List<MealWithExceed> getByDate(LocalTime startTime, LocalTime endTime, int caloriesPerDay, int userId) throws NotFoundException;

    void update(Meal meal, int userId);

    Collection<Meal> getAllByUser(int userId);

}