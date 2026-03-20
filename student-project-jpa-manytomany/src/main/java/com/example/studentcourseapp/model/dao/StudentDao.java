package com.example.studentcourseapp.model.dao;

import com.example.studentcourseapp.model.entity.Course;
import com.example.studentcourseapp.model.entity.Student;
import com.example.studentcourseapp.model.util.JpaUtil;
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
                    SELECT DISTINCT s
                    FROM Student s
                    LEFT JOIN FETCH s.courses
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
                 SELECT DISTINCT s
                 FROM Student s
                 LEFT JOIN FETCH s.courses
                 WHERE LOWER(s.fullName) LIKE LOWER(:kw)
                 ORDER BY s.fullName
                 """, Student.class)
                    .setParameter("kw", "%" + keyword + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> findByCourse(Course course) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("""
                 SELECT DISTINCT s
                 FROM Student s
                 JOIN s.courses c
                 LEFT JOIN FETCH s.courses
                 WHERE c = :course
                 ORDER BY s.fullName
                 """, Student.class)
                    .setParameter("course", course)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> findTop5ByGpa() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("""
                 SELECT s
                 FROM Student s
                 ORDER BY s.gpa DESC, s.fullName ASC
                 """, Student.class)
                    .setMaxResults(5)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    public void enrollCourse(Long studentId, Long courseId) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Student student = em.find(Student.class, studentId);
            Course course = em.find(Course.class, courseId);
            if (student == null || course == null) {
                throw new IllegalArgumentException("Student or Course not found");
            }
            student.addCourse(course);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void unenrollCourse(Long studentId, Long courseId) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Student student = em.find(Student.class, studentId);
            Course course = em.find(Course.class, courseId);
            if (student == null || course == null) {
                throw new IllegalArgumentException("Student or Course not found");
            }
            student.removeCourse(course);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
