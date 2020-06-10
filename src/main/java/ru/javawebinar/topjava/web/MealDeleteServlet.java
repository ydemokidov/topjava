package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealsDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MealDeleteServlet extends HttpServlet {

    MealsDB db = MealsDB.getDb();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("id")!=null){
            db.deleteMeal(Long.parseLong(req.getParameter("id")));
        }
        resp.sendRedirect(req.getContextPath()+"/meals");
    }
}
