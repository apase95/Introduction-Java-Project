package com.example.salesmis.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("salesPU");

    /** Ngăn khởi tạo trực tiếp lớp tiện ích này. */
    private JpaUtil() {
    }

    /** Tạo và trả về một EntityManager mới từ factory dùng chung. */
    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    /** Đóng EntityManagerFactory khi ứng dụng tắt. */
    public static void shutdown() {
        if (EMF.isOpen()) {
            EMF.close();
        }
    }
}
