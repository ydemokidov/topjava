package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void Get() {
        Meal m = service.get(MealTestData.testMealId, USER_ID);
        assertMatch(m, MealTestData.userMeal);
    }

    @Test
    public void Delete() throws NotFoundException{
        service.delete(MealTestData.testMealId,USER_ID);
        assertThrows(NotFoundException.class, ()-> service.get(MealTestData.testMealId,USER_ID));
    }

    @Test
    public void otherUserMealGet() throws NotFoundException{
        assertThrows(NotFoundException.class,()->service.get(MealTestData.testMealId,ADMIN_ID));
    }

    @Test
    public void otherUserMealDelete() throws NotFoundException{
        assertThrows(NotFoundException.class, ()-> service.delete(MealTestData.testMealId,ADMIN_ID));
    }

    @Test
    public void otherUserMealUpdate() throws NotFoundException{
        Meal expected = MealTestData.getUpdated();
        assertThrows(NotFoundException.class,()->service.update(expected,ADMIN_ID));
    }

    @Test
    public void GetBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2001, Month.SEPTEMBER,29), LocalDate.of(2001, Month.SEPTEMBER, 30),USER_ID);
        assertMatch(meals,MealTestData.mealList);
    }

    public void GetAll() {

    }

    @Test
    public void Update() {
        Meal expected = MealTestData.getUpdated();
        service.update(expected,USER_ID);
        Meal actual = service.get(MealTestData.testMealId,USER_ID);
        assertMatch(actual,expected);
    }

    @Test
    public void Create() {
        Meal expected = MealTestData.getNew();
        Meal actual = service.create(expected,USER_ID);
        expected.setId(actual.getId());
        assertMatch(actual,expected);
        assertMatch(service.get(expected.getId(),USER_ID),expected);
    }
}