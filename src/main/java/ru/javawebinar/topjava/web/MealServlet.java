package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealsUtil mealsUtil = new MealsUtil();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            mealsUtil.delete(Integer.parseInt(request.getParameter("id")));
        } else if ("edit".equals(action)) {
            Meal meal = mealsUtil.getById(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
            response.sendRedirect("mealEdit.jsp");
        } else if ("insert".equals(action)) {
            Meal meal = new Meal();
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
            response.sendRedirect("mealEdit.jsp");
        }
        List<MealTo> mealsTo = mealsUtil.getAll();
        request.setAttribute("listMeal", mealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
        response.sendRedirect("meals.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("mealId");
        if (userId == null) {
            Meal meal = new Meal(MealsUtil.countId(),
                    LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
            mealsUtil.add(meal);
        } else {
            Meal meal = new Meal(Integer.parseInt(request.getParameter("mealId")),
                    LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
            mealsUtil.update(meal);
        }
        List<MealTo> mealsTo = mealsUtil.getAll();
        request.setAttribute("listMeal", mealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
        response.sendRedirect("meals.jsp");
    }
}




