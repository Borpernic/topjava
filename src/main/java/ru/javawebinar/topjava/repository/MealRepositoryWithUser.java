package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;

public interface MealRepositoryWithUser /*extends  MealRepository*/ {

    Meal save(Meal meal, User user);

    void delete(int id, User user);

    Meal get(int id, User user);

    Collection<Meal> getAllByUser(User user);
}
