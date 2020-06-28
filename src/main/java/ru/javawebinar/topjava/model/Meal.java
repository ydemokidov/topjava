package ru.javawebinar.topjava.model;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m where m.id=:id and m.user.id =:user_id"),
        @NamedQuery(name = Meal.ALL_SORTED,query = "SELECT m FROM Meal m where m.user.id=:user_id"),
        @NamedQuery(name = Meal.FILTERED_BY_DATE,query = "SELECT m from Meal m where m.user.id=:user_id and m.dateTime>=:startDt and m.dateTime<:endDt"),
        @NamedQuery(name = Meal.SINGLE_MEAL,query = "SELECT m from Meal m where m.id=:id and m.user.id =:user_id")
})
@Entity
@Table(name = "meals",uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","date_time"},name="meals_unique_user_datetime_idx"))
public class Meal extends AbstractBaseEntity {
    @Column(name = "date_time",nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name="description",nullable = false)
    @NotNull
    private String description;

    @Column(name = "calories",nullable = false)
    @NotNull
    @Min(value = 0, message = "The value must be positive")
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public static final String DELETE = "Meal.delete";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String FILTERED_BY_DATE = "Meal.getFilteredByDate";
    public static final String SINGLE_MEAL = "Meal.getSingleMeal";

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
