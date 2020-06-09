package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.Main;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class MealsUtil {
    /*private static List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );*/

    private static final int CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {

        /*final LocalTime startTime = LocalTime.of(7, 0);
        final LocalTime endTime = LocalTime.of(12, 0);

        List<MealTo> mealsTo = filteredByStreams(meals, startTime, endTime, CALORIES_PER_DAY);
        mealsTo.forEach(System.out::println);*/
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(meal -> isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(),meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static List<MealTo> getAllMealTo(){
        /*Map<LocalDate, Integer> caloriesSumByDate = Main.db.getAllMeals().stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(MealTo::getCalories))
                );

        return Main.db.getAllMeals().stream()
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.) > CALORIES_PER_DAY))
                .collect(Collectors.toList());

         */
        return null;
    }


}
