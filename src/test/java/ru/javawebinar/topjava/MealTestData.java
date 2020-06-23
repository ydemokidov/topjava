package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    //('2001-09-28 01:00:01',200,100000,'Еда 1'),

    public static final int testMealId = 100002;

    public static List<Meal> mealList = Arrays.asList(
            new Meal(100010,LocalDateTime.of(2001, Month.SEPTEMBER, 29, 1, 0,4), "Еда 18", 290),
            new Meal(100009,LocalDateTime.of(2001, Month.SEPTEMBER, 29, 1, 0,3), "Еда 17", 200),
            new Meal(100008,LocalDateTime.of(2001, Month.SEPTEMBER, 29, 1, 0,2), "Еда 16", 280),
            new Meal(100007,LocalDateTime.of(2001, Month.SEPTEMBER, 29, 1, 0,1), "Еда 15", 250)
            );

    public static Meal userMeal = new Meal(testMealId,LocalDateTime.of(2001,9,28,1,0,1),"Еда 1",200);

    public static Meal getNew(){
        return new Meal(null,LocalDateTime.of(2001,9,30,1,0,1),"Еда 22",300);
    }

    public static void assertMatch(Meal actual,Meal expected){
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected){
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected){
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }

    public static Meal getUpdated(){
        Meal m = getNew();
        m.setId(testMealId);
        return m;
    }
}
