package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User userRef = em.getReference(User.class,userId);
        meal.setUser(userRef);
        if(meal.isNew()){
            em.persist(meal);
            return meal;
        }else{
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE,Meal.class)
                .setParameter("id",id)
                .setParameter("user_id",userId)
                .executeUpdate()!=0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals =  em.createNamedQuery(Meal.SINGLE_MEAL,Meal.class)
                .setParameter("id",id)
                .setParameter("user_id",userId).getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED,Meal.class)
                .setParameter("user_id",userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.FILTERED_BY_DATE,Meal.class)
                .setParameter("user_id",userId)
                .setParameter("startDt",startDateTime)
                .setParameter("endDt",endDateTime)
                .getResultList();
    }
}