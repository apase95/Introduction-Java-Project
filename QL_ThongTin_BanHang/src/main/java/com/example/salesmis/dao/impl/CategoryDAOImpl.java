package com.example.salesmis.dao.impl;

import com.example.salesmis.config.JpaUtil;
import com.example.salesmis.dao.CategoryDAO;
import com.example.salesmis.model.entity.Category;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public List<Category> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c ORDER BY c.categoryName", Category.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Category> findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Category.class, id));
        } finally {
            em.close();
        }
    }
}
