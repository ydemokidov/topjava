package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealsDB;
import ru.javawebinar.topjava.model.MealsDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MealsDatabase mealsDB = MealsDB.getDb();
        //если без id - то создание

        String dttm = req.getParameter("dttm");
        String desc = req.getParameter("description");
        String calories = req.getParameter("calories");

        if(req.getParameter("id") == null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            // 2020-06-02T21:53
            LocalDateTime dateTime = LocalDateTime.parse(dttm, formatter);
            int cals = Integer.parseInt(calories);
            mealsDB.createMeal(dateTime,desc,cals);
        }
        else
        {
            //если с id то апдейт
        }
        resp.sendRedirect(req.getContextPath()+"/meals");
    }
}
