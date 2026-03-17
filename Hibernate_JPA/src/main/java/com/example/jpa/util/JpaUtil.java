package com.example.jpa.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("mysql-jpa-demo");

    private JpaUtil() {
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }

    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
