package com.example.salesmis.dao.impl;

import com.example.salesmis.config.JpaUtil;
import com.example.salesmis.dao.ZoneDAO;
import com.example.salesmis.model.entity.Zone;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ZoneDAOImpl implements ZoneDAO {
    @Override
    public List<Zone> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT z FROM Zone z ORDER BY z.zoneName", Zone.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Zone> findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Zone.class, id));
        } finally {
            em.close();
        }
    }
}
