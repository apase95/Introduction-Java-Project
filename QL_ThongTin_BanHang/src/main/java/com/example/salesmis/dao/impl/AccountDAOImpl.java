package com.example.salesmis.dao.impl;

import com.example.salesmis.config.JpaUtil;
import com.example.salesmis.dao.AccountDAO;
import com.example.salesmis.model.entity.Account;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public Optional<Account> findByUsername(String username) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            List<Account> accounts = em.createQuery(
                    "SELECT a FROM Account a WHERE a.username = :username", Account.class)
                    .setParameter("username", username)
                    .getResultList();
            return accounts.stream().findFirst();
        } finally {
            em.close();
        }
    }

    @Override
    public Account save(Account account) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Account saved;
            if (account.getId() == null) {
                em.persist(account);
                saved = account;
            } else {
                saved = em.merge(account);
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
    public long countAccounts() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(a) FROM Account a", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
