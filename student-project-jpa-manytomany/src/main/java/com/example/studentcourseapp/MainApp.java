package com.example.studentcourseapp;

import com.example.studentcourseapp.controller.StudentCourseController;
import com.example.studentcourseapp.model.dao.CourseDao;
import com.example.studentcourseapp.model.dao.StudentDao;
import com.example.studentcourseapp.view.StudentCourseView;
import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentCourseView view = new StudentCourseView();
            StudentDao studentDao = new StudentDao();
            CourseDao courseDao = new CourseDao();
            new StudentCourseController(view, studentDao, courseDao);
            view.setVisible(true);
        });
    }
}