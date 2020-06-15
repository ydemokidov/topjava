package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal){
        return repository.save(meal);
    }

    public boolean delete(int id,int userId) {
        if(repository.delete(id,userId)){
            return true;
        }else{
            throw new NotFoundException(String.format("Meal with id=%d for userId=%d is not found",id,userId));
        }
    }

    public Meal get(int id,int userId){
        Meal m = repository.get(id,userId);
        if(m!= null){
            return m;
        }else{
            throw new NotFoundException(String.format("Meal with id=%d for userId=%d is not found",id,userId));
        }
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Meal meal) {
        repository.save(meal);
    }

}