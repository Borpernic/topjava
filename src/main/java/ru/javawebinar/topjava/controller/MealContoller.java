package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealContoller extends List<Meal>{

    public List<Meal> addMeal(Meal meal);
    public List<Meal> deleteMeal(Meal meal);
    public Meal editMeal(LocalDateTime meal);
    public Meal getMeal(Meal meal);
}
