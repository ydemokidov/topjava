package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        /*List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);*/

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        return null;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

                            //собираем мап с ключом по дате
        return meals.stream().collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate()))

                            //фильтруем по превышению суммой калорий за дату
                            .values().stream().filter(userMeals -> userMeals.stream().map(UserMeal::getCalories).reduce(0, Integer::sum) > caloriesPerDay)

                            //т.к. value мапы был список UserMeal - переходим от Stream<List<UserMeal>> к Stream<UserMeal>
                            .flatMap(List<UserMeal>::stream)

                            //фильтруем UserMeal по времени startTime включительно и endTime строго
                            .filter(e->(e.getDateTime().toLocalTime().equals(startTime) || e.getDateTime().toLocalTime().isAfter(startTime)) && e.getDateTime().toLocalTime().isBefore(endTime))

                            //каждый UserMeal преобразовываем в UserMealWIthExcess
                            .map(e-> new UserMealWithExcess(e.getDateTime(),e.getDescription(),e.getCalories(),true))

                            //собираем List
                            .collect(Collectors.toList());
    }
}
