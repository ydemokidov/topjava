package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealsDB;
import ru.javawebinar.topjava.model.MealsDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MealsDatabase mealsdb = MealsDB.getDb();
        if(req.getParameter("id") == null){
            log.debug("get all meals");
            req.setAttribute("mealsList",mealsdb.getAllMeals());
        }else{
            long id = Long.parseLong(req.getParameter("id"));
            log.debug("get meal with id: "+id);
            req.setAttribute("mealsList",mealsdb.getMealById(id));
        }
        req.getRequestDispatcher("meals.jsp").forward(req,resp);
    }
}
