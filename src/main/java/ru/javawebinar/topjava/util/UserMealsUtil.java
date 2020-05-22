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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> results = new ArrayList<>();

        //предварительная сборка результатов по датам, пока ещё не знаем - превышены калории за день или нет
        Map<LocalDate,List<UserMealWithExcess>> MealsGrouppedByDates = new TreeMap<>();

        //подсчет калорий по датам
        Map<LocalDate,Integer> caloriesByDate = new HashMap<>();
        //если калории за день превышены - фиксируем здесь что добавили результаты из промежуточной карты в итоговый лист и для этой даты промежуточную более не используем
        Map<LocalDate,Boolean> addedToResults = new HashMap<>();

        for(UserMeal meal : meals){
            LocalDate dt = meal.getDateTime().toLocalDate();
            LocalTime tm = meal.getDateTime().toLocalTime();

            //фиксируем даты для передачи в итоговый результат
            if(!addedToResults.containsKey(dt)){
                addedToResults.put(dt,false);
            }

                //добавляем калории в расчет
            if(caloriesByDate.containsKey(dt)){
                caloriesByDate.put(dt, caloriesByDate.get(dt)+meal.getCalories());
            }else{
                caloriesByDate.put(dt, meal.getCalories());
            }
            // если калории за день превышены
            if(caloriesByDate.get(dt)>caloriesPerDay){
                if(!addedToResults.get(dt)) {
                    results.addAll(MealsGrouppedByDates.get(dt));
                    addedToResults.put(dt, true);
                }else{
                    //если было превышение и передали из карты в резeльтат и проходит по фильтру- то добавляем сразу в результат
                    if((tm.equals(startTime)|| tm.isAfter(startTime)&& tm.isBefore(endTime))) {
                        results.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
                    }
                }
            }else{
                // если проходит по фильтру времени
                if((tm.equals(startTime)|| tm.isAfter(startTime)&& tm.isBefore(endTime))){
                    if(MealsGrouppedByDates.containsKey(dt)){
                        MealsGrouppedByDates.get(dt).add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
                    }else{
                        MealsGrouppedByDates.put(dt, new ArrayList<>());
                        MealsGrouppedByDates.get(dt).add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
                    }
                }
            }
        }

        return results;
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
