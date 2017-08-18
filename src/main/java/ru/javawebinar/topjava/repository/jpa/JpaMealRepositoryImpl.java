package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {

        if (!meal.isNew() && get(meal.getId(), userId) == null) return null;
        User user = new User();
        user.setId(userId);
        meal.setUser(user);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return em.merge(meal);
        }


    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {


        Query query = em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId);

        return query.executeUpdate() != 0;

    }

    @Override
    public Meal get(int id, int userId) {

        final Query meals = em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId);

        return DataAccessUtils.singleResult((List<Meal>) meals.getResultList());
    }

    @Override
    public List<Meal> getAll(int userId) {
        final Query meals = em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("user_id", userId);

        return (List<Meal>) meals.getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        final Query meals = em.createNamedQuery(Meal.GETBETWEEN, Meal.class)
                .setParameter("user_id", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate);

        return (List<Meal>) meals.getResultList();
    }
}