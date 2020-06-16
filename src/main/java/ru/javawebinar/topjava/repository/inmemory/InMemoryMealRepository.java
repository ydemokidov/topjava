package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if(repository.get(id).getUserId() == userId) {
            return repository.remove(id) != null;
        }else{
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        if(repository.get(id).getUserId() == userId) {
            return repository.get(id);
        }else{
            return null;
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream().filter(u -> u.getUserId()==userId)
                .sorted((Meal o1, Meal o2) -> {
            if(o1.getDateTime().isBefore(o2.getDateTime())){
                return 1;
            }else if(o1.getDateTime().equals(o2.getDateTime())){
                return 0;
            }else{
                return -1;
            }
        }).collect(Collectors.toList());
    }

    public Collection<Meal> getFilteredMeals(int userId,LocalDate beginDt, LocalDate endDt, LocalTime beginTm, LocalTime endTm){
        Stream<Meal> result = getAll(userId).stream();
        if(beginDt!=null){
            result = result.filter(m-> m.getDate().isAfter(beginDt) || m.getDate().isEqual(beginDt));
        }
        if(endDt!= null){
            result = result.filter(m-> m.getDate().isBefore(endDt) || m.getDate().isEqual(endDt));
        }
        if(beginTm!=null && endTm!=null){
            result = result.filter(m-> DateTimeUtil.isBetweenHalfOpen(m.getTime(),beginTm,endTm));
        }
        return result.collect(Collectors.toList());
    }
}

