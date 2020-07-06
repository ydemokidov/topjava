package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;

    @Autowired
    private DataJpaUserRepository userRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User u = userRepository.get(userId);
        if(meal.isNew()){
            meal.setUser(u);
            return crudRepository.save(meal);
        }else {
            Meal m = get(meal.id(), userId);
            if(m!=null){
                meal.setUser(u);
                return crudRepository.save(meal);
            }else{
                return null;
            }
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id,userId)!=0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.get(id,userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAll(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetweenHalfOpen(userId,startDateTime,endDateTime);
    }
}
