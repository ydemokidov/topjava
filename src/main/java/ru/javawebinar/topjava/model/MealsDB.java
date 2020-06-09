package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class MealsDB implements MealsDatabase {
    private static final int CALORIES_PER_DAY = 2000;

    private static MealsDB mealsDB;

    private static List<Meal> db = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void tempData(){
        createMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        createMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        createMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        createMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        createMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        createMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        createMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    }

    public static MealsDB getDb() {
        if(mealsDB == null){
            mealsDB = new MealsDB();
        }
        return mealsDB;
    }

    private void addMeal(LocalDateTime dttm,String description,int calories){
        db.add(new Meal(dttm,description,calories));
    }

    public List<MealTo> getMealById(long id){
        Map<LocalDate, Integer> caloriesSumByDate = db.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return db.stream().filter(e -> e.getId() == id)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > 2000))
                .collect(Collectors.toList());
    }

    public void updateMeal(long id,LocalDateTime dttm,String description,int calories){
        for (Meal meal : db) {
            if (meal.getId() == id) {
                meal.setDateTime(dttm);
                meal.setDescription(description);
                meal.setCalories(calories);
                break;
            }
        }
    }

    public void deleteMeal(long id){
        for(int i=0;i< db.size();i++){
            if(db.get(i).getId()==id){
                db.remove(i);
                break;
            }
        }
    }

    @Override
    public List<MealTo> getAllMeals() {
        Map<LocalDate, Integer> caloriesSumByDate = db.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );
        //сначала новые
        return db.stream()
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > 2000))
                .sorted((o1, o2) -> {
                    if(o1.getDateTime().isBefore(o2.getDateTime())){
                        return 1;
                    }else if(o1.getDateTime().equals(o2.getDateTime())){
                        return 0;
                    }else{
                        return -1;
                    }
                })
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(),meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    @Override
    public void createMeal(LocalDateTime dttm, String description, int calories) {
        addMeal(dttm,description,calories);
    }
}
