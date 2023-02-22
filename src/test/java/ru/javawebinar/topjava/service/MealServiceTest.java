package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL1_ID_USER, USER_ID);
        assertMatchMeal(meal, mealUser1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(MEAL1_ID_USER, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL1_ID_USER, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID_USER, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL1_ID_USER, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
    }

    @Test
    public void getAll() {
        assertMatchMeal(service.getAll(USER_ID), meals);
    }

    @Test
    public void update() {
        Meal updated = getUpdate();
        service.update(updated, USER_ID);
        assertMatchMeal(service.get(MEAL1_ID_USER, USER_ID), getUpdate());
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID_USER, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getCreate(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getCreate();
        newMeal.setId(newId);
        assertMatchMeal(created, newMeal);
        assertMatchMeal(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create((new Meal(LocalDateTime.of(2023, Month.FEBRUARY, 19, 13, 0, 0),
                        "обед2 User", 900)), USER_ID));
    }
}