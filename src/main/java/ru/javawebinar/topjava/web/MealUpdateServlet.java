package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.MealsDB;
import ru.javawebinar.topjava.model.MealsDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealUpdateServlet extends HttpServlet {
    MealsDatabase db = MealsDB.getDb();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("id") != null && req.getParameter("datetime")==null) {
            Long id = Long.parseLong(req.getParameter("id"));
            MealTo mealTo = db.getMealById(id).get(0);

            resp.sendRedirect(req.getContextPath()+"/update.jsp?id="+mealTo.getId()
                    +"&dttm="+mealTo.getDateTime()
                    +"&desc="+mealTo.getDescription()
                    +"&calories="+mealTo.getCalories());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String dttm = req.getParameter("dttm");
        String desc = req.getParameter("desc");
        String calories = req.getParameter("calories");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        // 2020-06-02T21:53
        LocalDateTime dateTime = LocalDateTime.parse(dttm, formatter);
        int cals = Integer.parseInt(calories);
        db.updateMeal(Long.parseLong(id),dateTime,desc,cals);
        resp.sendRedirect(req.getContextPath()+"/meals");
    }
}
