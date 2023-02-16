package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public void update(int userId, Meal meal) {
        repository.save(userId, meal);
    }

    public boolean delete(int userId, int id) {
      return   checkNotFoundWithId(repository.delete(userId, id));
    }

    public Meal get(int userId, int id) {
        return repository.get(userId, id);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<MealTo> getAllTo(int userId, int caloriesPerDay) {
        List<Meal> meals = repository.getAll(userId);
        return MealsUtil.getTos(meals, caloriesPerDay);
    }

    public List<MealTo> getByDateTime(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<Meal> list = repository.getByDateTime(userId, startDate, startTime, endDate, endTime);
        return MealsUtil.getTos(list, authUserCaloriesPerDay());
    }
}