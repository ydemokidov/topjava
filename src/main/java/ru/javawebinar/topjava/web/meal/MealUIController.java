package ru.javawebinar.topjava.web.meal;

import com.sun.istack.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/ui/meals")
public class MealUIController extends AbstractMealController{

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void upsert(@RequestParam Integer id,
                       @RequestParam String dateTime,
                       @RequestParam String description,
                       @RequestParam Integer calories){

        LocalDateTime created = DateTimeUtil.parseLocalDateTime(dateTime);

        Meal meal = new Meal(id,created,description,calories);
        if(meal.isNew()){
            super.create(meal);
        }else{
            super.update(meal,id);
        }
    }

    @GetMapping(value = "/filter",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(@RequestParam @Nullable String startDate,
                                   @RequestParam @Nullable String startTime,
                                   @RequestParam @Nullable String endDate,
                                   @RequestParam @Nullable String endTime) {
        LocalDate startDt = DateTimeUtil.parseLocalDate(startDate);
        LocalDate endDt = DateTimeUtil.parseLocalDate(endDate);
        LocalTime startTm = DateTimeUtil.parseLocalTime(startTime);
        LocalTime endTm = DateTimeUtil.parseLocalTime(endTime);

        return super.getBetween(startDt, startTm, endDt, endTm);
    }
}
