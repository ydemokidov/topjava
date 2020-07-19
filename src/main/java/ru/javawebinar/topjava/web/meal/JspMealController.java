package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private MealService mealService;

    @Autowired
    public JspMealController(MealService mealService){
        this.mealService = mealService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()),SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        mealService.delete(id,SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/update/{id}")
    public String goToUpdate(@PathVariable int id,Model model){
        model.addAttribute("meal",mealService.get(id,SecurityUtil.authUserId()));
        return "mealForm";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String update(@RequestParam HashMap<String, String> formData){
        Meal m;
        LocalDateTime dttm = LocalDateTime.parse(formData.get("dateTime"));
        int calories = Integer.parseInt(formData.get("calories"));
        String description = formData.get("description");
        description = new String(description.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
        if(formData.get("id").isEmpty()){
            m = new Meal(dttm,description,calories);
            mealService.create(m,SecurityUtil.authUserId());
        }else{
            int mealId = Integer.parseInt(formData.get("id"));
            m = new Meal(mealId,dttm,description,calories);
            mealService.update(m,SecurityUtil.authUserId());
        }
        return "redirect:meals";
    }

    @GetMapping("/create")
    public String goToCreate(Model model){
        model.addAttribute("meal",new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }
}
