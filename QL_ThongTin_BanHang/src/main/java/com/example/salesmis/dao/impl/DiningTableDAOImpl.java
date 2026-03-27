package com.example.salesmis.dao.impl;

import com.example.salesmis.config.JpaUtil;
import com.example.salesmis.dao.DiningTableDAO;
import com.example.salesmis.model.entity.DiningTable;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class DiningTableDAOImpl implements DiningTableDAO {
    @Override
    public List<DiningTable> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT t FROM DiningTable t LEFT JOIN FETCH t.zone ORDER BY t.tableName", DiningTable.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<DiningTable> findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            List<DiningTable> list = em.createQuery("SELECT t FROM DiningTable t LEFT JOIN FETCH t.zone WHERE t.id = :id", DiningTable.class)
                     .setParameter("id", id)
                     .getResultList();
            return list.stream().findFirst();
        } finally {
            em.close();
        }
    }
}
