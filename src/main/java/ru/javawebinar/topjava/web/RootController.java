package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        //SecurityUtil.setAuthUserId(userId);
        return "redirect:meals";
    }

    @GetMapping("/meals")
    public String getMeals() {
        return "meals";
    }
}
