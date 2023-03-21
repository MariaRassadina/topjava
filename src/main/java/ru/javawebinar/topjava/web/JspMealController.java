package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    @GetMapping()
    public String getMeals(Model model) {
        log.info("meals");
        List<Meal> all = service.getAll(SecurityUtil.authUserId());
        List<MealTo> tos = MealsUtil.getTos(all, SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", tos);
        return "meals";
    }

    @GetMapping("/create")
    public String getMealFormCreate(Model model) {
        model.addAttribute("meal", new Meal());
        return "mealForm";
    }

    @GetMapping("/update")
    public String getMealFormUpdate(HttpServletRequest request, Model model) {
        int mealId = Integer.parseInt(request.getParameter("id"));
        Meal meal = service.get(mealId, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }


//    @PostMapping()
//    public String saveMeal(@ModelAttribute ("meal") Meal meal) {
//        log.info("saveMeal");
//        return "redirect:/meals";
//    }

    @PostMapping()
    public String saveMeal(HttpServletRequest request) {
        log.info("saveMeal");
        Meal meal = new Meal();
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        meal.setDateTime(dateTime);
        meal.setDescription(description);
        meal.setCalories(calories);
        if (StringUtils.hasLength(request.getParameter("id"))) {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            service.update(meal, SecurityUtil.authUserId());
        } else {
            service.create(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }

    @GetMapping("/delete")
    public String deleteMeal(HttpServletRequest request, Model model) {
        int mealId = Integer.parseInt(request.getParameter("id"));
        log.info("deleteMeal {}", mealId);
        service.delete(mealId, SecurityUtil.authUserId());
        return "redirect:/meals";
    }
}
