package com.example.studentapp.model.dao;

import com.example.studentapp.model.dto.StudentStatsDto;
import com.example.studentapp.model.entity.Department;
import com.example.studentapp.model.entity.Student;
import com.example.studentapp.model.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class StudentDao {

    public void save(Student student) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(student);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Student findById(Long id) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    public List<Student> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("""
                SELECT s
                FROM Student s
                LEFT JOIN FETCH s.department
                ORDER BY s.id
                """, Student.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> searchByName(String keyword) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("""
                SELECT s
                FROM Student s
                LEFT JOIN FETCH s.department
                WHERE LOWER(s.fullName) LIKE LOWER(:kw)
                ORDER BY s.fullName
                """, Student.class)
                    .setParameter("kw", "%" + keyword + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> findByDepartment(Department department) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("""
                SELECT s
                FROM Student s
                LEFT JOIN FETCH s.department
                WHERE s.department = :dept
                ORDER BY s.fullName
                """, Student.class)
                    .setParameter("dept", department)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> findByMinGpa(double minGpa) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("""
                SELECT s
                FROM Student s
                LEFT JOIN FETCH s.department
                WHERE s.gpa >= :minGpa
                ORDER BY s.gpa DESC
                """, Student.class)
                    .setParameter("minGpa", minGpa)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public StudentStatsDto getStatistics() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            Long total = em.createQuery("""
                SELECT COUNT(s)
                FROM Student s
                """, Long.class).getSingleResult();

            Double avg = em.createQuery("""
                SELECT AVG(s.gpa)
                FROM Student s
                """, Double.class).getSingleResult();

            Long excellent = em.createQuery("""
                SELECT COUNT(s)
                FROM Student s
                WHERE s.gpa >= 8.0
                """, Long.class).getSingleResult();

            return new StudentStatsDto(
                    total != null ? total : 0L,
                    avg != null ? avg : 0.0,
                    excellent != null ? excellent : 0L
            );
        } finally {
            em.close();
        }
    }

    public void update(Student student) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(student);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}