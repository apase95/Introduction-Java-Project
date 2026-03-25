package com.example.salesmis.dao.impl;

import com.example.salesmis.config.JpaUtil;
import com.example.salesmis.dao.CustomerDAO;
import com.example.salesmis.model.entity.Customer;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public List<Customer> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Customer c ORDER BY c.customerCode", Customer.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Customer> findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Customer.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public Customer save(Customer customer) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Customer saved;
            if (customer.getId() == null) {
                em.persist(customer);
                saved = customer;
            } else {
                saved = em.merge(customer);
            }
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
            Customer c = em.find(Customer.class, id);
            if (c != null) {
                em.remove(c);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Customer> searchByKeyword(String keyword) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String kw = "%" + keyword.toLowerCase() + "%";
            return em.createQuery("SELECT c FROM Customer c WHERE LOWER(c.customerCode) LIKE :kw OR LOWER(c.fullName) LIKE :kw", Customer.class)
                    .setParameter("kw", kw)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
