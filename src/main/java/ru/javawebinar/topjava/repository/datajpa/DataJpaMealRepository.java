package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;
    private final DataJpaUserRepository dataJpaUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository, DataJpaUserRepository dataJpaUserRepository) {
        this.crudRepository = crudRepository;
        this.dataJpaUserRepository = dataJpaUserRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = dataJpaUserRepository.get(userId);
        meal.setUser(user);
        if (meal.isNew()) {
            return crudRepository.save(meal);
        } else {
            return get(meal.id(), userId) == null ? null : crudRepository.save(meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudRepository.findById(id).orElse(null);
        if (meal != null) {
            if (meal.getUser().getId() == userId) {
                return meal;
            }
            return null;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserId(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetweenHalfOpen(userId, startDateTime, endDateTime);
    }
}
