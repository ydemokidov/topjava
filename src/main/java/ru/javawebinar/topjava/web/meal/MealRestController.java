package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class MealRestController {
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        return service.get(id, SecurityUtil.authUserId());
    }

    public Collection<Meal> getAll() {
        return service.getAll(SecurityUtil.authUserId());
    }

    public boolean delete(int id) {
        return service.delete(id,SecurityUtil.authUserId());
    }

    public void update(Meal meal){
        Meal m = service.get(meal.getId(),SecurityUtil.authUserId());
        if(m!=null){
            service.update(meal);
        }
    }

    public Meal create(Meal meal){
        return service.create(meal);
    }

    public Collection<MealTo> getFilteredMeals(LocalDate beginDt, LocalDate endDt, LocalTime beginTm, LocalTime endTm){
        Stream<Meal> result = getAll().stream();
        if(beginDt!=null){
            result = result.filter(m-> m.getDate().isAfter(beginDt) || m.getDate().isEqual(beginDt));
        }
        if(endDt!= null){
            result = result.filter(m-> m.getDate().isBefore(endDt));
        }
        if(beginTm!=null && endTm!=null){
            result = result.filter(m-> DateTimeUtil.isBetweenHalfOpen(m.getTime(),beginTm,endTm));
        }
        return MealsUtil.getTos(result.collect(Collectors.toList()), SecurityUtil.authUserCaloriesPerDay());
    }
}