package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;

public interface MealRepositoryWithUser /*extends  MealRepository*/ {

    Meal save(Meal meal, int userId);

    Boolean delete(int mealId, int userId);

    Meal get(int mealId, int userId);

    Collection<Meal> getAllByUser(int userId);
}
