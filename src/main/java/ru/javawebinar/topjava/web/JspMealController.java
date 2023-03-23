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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    @GetMapping()
    public String getAll(Model model) {
        log.info("meals");
        List<Meal> all = service.getAll(SecurityUtil.authUserId());
        List<MealTo> tos = MealsUtil.getTos(all, SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", tos);
        return "meals";
    }

    @GetMapping("/filter")
    public String getByFilter(HttpServletRequest request, Model model) {
        log.info("meals filtered");
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> mealsByFilter = service.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId());
        List<MealTo> tos = MealsUtil.getFilteredTos(mealsByFilter, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        model.addAttribute("meals", tos);
        return "meals";
    }

    @GetMapping("/create")
    public String getFormCreate(Model model) {
        model.addAttribute("meal", new Meal());
        return "mealForm";
    }

    @GetMapping("/update")
    public String getFormUpdate(HttpServletRequest request, Model model) {
        int mealId = Integer.parseInt(request.getParameter("id"));
        Meal meal = service.get(mealId, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping()
    public String save(HttpServletRequest request) {
        log.info("saveMeal");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.hasLength(request.getParameter("id"))) {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            service.update(meal, SecurityUtil.authUserId());
        } else {
            service.create(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        int mealId = Integer.parseInt(request.getParameter("id"));
        log.info("deleteMeal {}", mealId);
        service.delete(mealId, SecurityUtil.authUserId());
        return "redirect:/meals";
    }
}
