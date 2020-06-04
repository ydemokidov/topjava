package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        //System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(14, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> results = new ArrayList<>();
        //подсчет калорий по датам
        Map<LocalDate,Integer> caloriesByDate = new HashMap<>();
        Map<LocalDate,AtomicBoolean> exceeds = new HashMap<>();

        for(UserMeal meal : meals){
            LocalDate dt = meal.getDateTime().toLocalDate();
            LocalTime tm = meal.getDateTime().toLocalTime();

            caloriesByDate.put(dt,caloriesByDate.getOrDefault(dt,0)+meal.getCalories());
            AtomicBoolean wrapBoolean = exceeds.putIfAbsent(dt,new AtomicBoolean());
            if(caloriesByDate.getOrDefault(dt,0)>caloriesPerDay){
                if(wrapBoolean!=null) {
                    wrapBoolean.set(true);
                }
            }

            if((tm.equals(startTime)
                    || tm.isAfter(startTime))
                        && tm.isBefore(endTime)){
                results.add(new UserMealWithExcess(meal.getDateTime(),meal.getDescription(),meal.getCalories(),exceeds.get(dt)));
            }
        }

        return results;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
                            //собираем мап с ключом по дате
        return meals.stream().collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate())).values()
                .stream().flatMap(m -> {
                    boolean exceed = m.stream().mapToInt(UserMeal::getCalories).sum()>caloriesPerDay;
                    return m.stream().filter(e->(e.getDateTime().toLocalTime().equals(startTime) || e.getDateTime().toLocalTime().isAfter(startTime)) && e.getDateTime().toLocalTime().isBefore(endTime))
                            .map(e-> new UserMealWithExcess(e.getDateTime(),e.getDescription(),e.getCalories(),new AtomicBoolean(exceed)));
                }).collect(Collectors.toList());
    }
}
