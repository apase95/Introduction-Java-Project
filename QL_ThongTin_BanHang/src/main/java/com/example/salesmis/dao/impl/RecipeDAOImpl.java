package com.example.salesmis.dao.impl;

import com.example.salesmis.config.JpaUtil;
import com.example.salesmis.dao.RecipeDAO;
import com.example.salesmis.model.entity.Recipe;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class RecipeDAOImpl implements RecipeDAO {
    @Override
    public List<Recipe> findByProductId(Long productId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Recipe r LEFT JOIN FETCH r.recipeIngredients ri LEFT JOIN FETCH ri.ingredient WHERE r.product.id = :productId ORDER BY r.variationName", Recipe.class)
                     .setParameter("productId", productId)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Recipe r LEFT JOIN FETCH r.recipeIngredients ri LEFT JOIN FETCH ri.ingredient WHERE r.id = :id", Recipe.class)
                     .setParameter("id", id)
                     .getResultList()
                     .stream().findFirst();
        } finally {
            em.close();
        }
    }

    @Override
    public Recipe save(Recipe recipe) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Recipe saved = (recipe.getId() == null) ? em.merge(recipe) : em.merge(recipe);
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
            Recipe r = em.find(Recipe.class, id);
            if (r != null) em.remove(r);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
