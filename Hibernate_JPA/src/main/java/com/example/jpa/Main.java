package com.example.jpa;

import com.example.jpa.dao.StudentDao;
import com.example.jpa.entity.Student;
import com.example.jpa.util.JpaUtil;

public class Main {

    public static void main(String[] args) {
        StudentDao dao = new StudentDao();

        // CREATE
        Student s1 = new Student("Nguyen Van A", "a@gmail.com", 8.2);
        Student s2 = new Student("Tran Thi B", "b@gmail.com", 9.0);
        dao.save(s1);
        dao.save(s2);

        // READ ALL
        System.out.println("=== DANH SACH SAU KHI THEM ===");
        dao.findAll().forEach(System.out::println);

        // READ ONE
        Student found = dao.findById(1L);
        System.out.println("=== TIM THEO ID = 1 ===");
        System.out.println(found);

        // UPDATE
        if (found != null) {
            found.setGpa(8.8);
            found.setFullName("Nguyen Van A Updated");
            dao.update(found);
        }

        System.out.println("=== SAU KHI UPDATE ===");
        dao.findAll().forEach(System.out::println);

        // DELETE
        dao.deleteById(2L);

        System.out.println("=== SAU KHI DELETE ===");
        dao.findAll().forEach(System.out::println);

        JpaUtil.shutdown();
    }
}
