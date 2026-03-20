package com.example.studentcourseapp.model.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("student-course-pu");
    private JpaUtil() {
    }
    public static EntityManagerFactory getEmf() {
        return EMF;
    }
    public static void shutdown() {
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }
}
