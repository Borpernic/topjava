package ru.javawebinar.topjava.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.env.MockPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.service.MealServiceImpl;
import org.springframework.mock.web.*;
@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)

public class MealsUtilTest {

    @Test
    public void main() throws Exception {

    }

    @Test
    public void getWithExceeded() throws Exception {
    }

    @Test
    public void getFilteredWithExceeded() throws Exception {
    }

    @Test
    public void getFilteredWithExceededByCycle() throws Exception {
    }

    @Test
    public void getFilteredWithExceededInOneReturn() throws Exception {
    }

    @Test
    public void createWithExceed() throws Exception {
    }

}