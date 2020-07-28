package ru.javawebinar.topjava.web.meal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_URL;

class MealRestControllerTest extends AbstractControllerTest {

    @Autowired
    MealService mealService;

    @Autowired
    private ObjectMapper objectMapper;

    void login() throws Exception {
        perform(MockMvcRequestBuilders.post("/users")
                .param("userId",String.valueOf(USER_ID)))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("meals"));
    }

    @Test
    void getTest() throws Exception {
        perform(get(REST_URL+"/"+MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(mealService.get(MEAL1_ID,SecurityUtil.authUserId())));
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() throws Exception {
        MvcResult result = perform(get(REST_URL))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andReturn();
        List<Meal> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Meal>>() {});

        AssertionMatcher<List<Meal>> allMealToMatcher = new AssertionMatcher<List<Meal>>() {
            @Override
            public void assertion(List<Meal> actual) throws AssertionError {
                MEAL_MATCHER.assertMatch(actual,mealService.getAll(SecurityUtil.authUserId()));
            }
        };

        allMealToMatcher.assertion(actual);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void getBetween() {
    }
}