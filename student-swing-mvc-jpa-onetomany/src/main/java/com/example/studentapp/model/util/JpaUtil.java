package com.example.studentapp.model.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("student-pu");

    private JpaUtil() {}

    public static EntityManagerFactory getEmf() {
        return EMF;
    }

    public static void shutdown(){
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }
}
