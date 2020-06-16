package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

@Controller
public class MealRestController {
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        return service.get(id, SecurityUtil.authUserId());
    }

    public Collection<MealTo> getAll() {
        return  MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()),SecurityUtil.authUserCaloriesPerDay());
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
}