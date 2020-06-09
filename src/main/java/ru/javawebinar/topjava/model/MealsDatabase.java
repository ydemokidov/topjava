package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.List;

public interface MealsDatabase {
    List<MealTo> getMealById(long id);
    List<MealTo> getAllMeals();
    void deleteMeal(long id);
    void updateMeal(long id, LocalDateTime dttm, String description, int calories);
    void createMeal(LocalDateTime dttm,String description,int calories);
    void tempData();
}
