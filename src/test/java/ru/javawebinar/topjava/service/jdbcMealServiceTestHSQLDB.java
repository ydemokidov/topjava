package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"hsqldb","jdbc"})
public class jdbcMealServiceTestHSQLDB extends MealServiceTest{
}
