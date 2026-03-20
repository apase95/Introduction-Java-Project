package com.example.studentcourseapp.model.dao;

import com.example.studentcourseapp.model.entity.Course;
import com.example.studentcourseapp.model.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class CourseDao {
    void save(Course course) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(course);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    public Course findById(Long id) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.find(Course.class, id);
        } finally {
            em.close();
        }
    }
    public List<Course> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("""
                                     SELECT c
                                     FROM Course c
                                     ORDER BY c.courseName
                                     """, Course.class).getResultList();
        } finally {
            em.close();
        }
    }
}
