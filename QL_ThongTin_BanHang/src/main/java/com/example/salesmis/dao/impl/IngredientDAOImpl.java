package com.example.salesmis.dao.impl;

import com.example.salesmis.config.JpaUtil;
import com.example.salesmis.dao.IngredientDAO;
import com.example.salesmis.model.entity.Ingredient;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class IngredientDAOImpl implements IngredientDAO {
    @Override
    public List<Ingredient> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Ingredient i ORDER BY i.ingredientName", Ingredient.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Ingredient> findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Ingredient.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Ingredient saved = (ingredient.getId() == null) ? em.merge(ingredient) : em.merge(ingredient);
            em.getTransaction().commit();
            return saved;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Ingredient i = em.find(Ingredient.class, id);
            if (i != null) em.remove(i);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
