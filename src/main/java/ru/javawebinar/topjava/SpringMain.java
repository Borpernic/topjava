package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.service.UserServiceImpl;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.ADMIN_USER_ID;
import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.USER_USER_ID;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("\nBean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames())+"\n");
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));

            final MealServiceImpl m = appCtx.getBean(MealServiceImpl.class);
            m.getAllByUser(USER_USER_ID).stream().forEach(meal -> System.out.println(meal.getDescription()));
           // m.getAllByUser(ADMIN_USER_ID).stream().forEach(meal -> System.out.println(meal.getDescription()));
            final UserServiceImpl m1 = appCtx.getBean(UserServiceImpl.class);
            m1.getAll().stream().forEach(user -> System.out.println(user.getName()));
        }
    }
}
