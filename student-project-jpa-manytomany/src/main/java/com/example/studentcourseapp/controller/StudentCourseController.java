package com.example.studentcourseapp.controller;

import com.example.studentcourseapp.model.dao.CourseDao;
import com.example.studentcourseapp.model.dao.StudentDao;
import com.example.studentcourseapp.model.entity.Course;
import com.example.studentcourseapp.model.entity.Student;
import com.example.studentcourseapp.view.StudentCourseView;
import javax.swing.*;
import java.util.List;

public class StudentCourseController {
    private final StudentCourseView view;
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    public StudentCourseController(StudentCourseView view, StudentDao studentDao, CourseDao
            courseDao) {
        this.view = view;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        initActions();
        loadCourses();
        loadAllStudentsAsync();
    }
    private void initActions() {
        view.getBtnAddStudent().addActionListener(e -> addStudent());
        view.getBtnLoadAll().addActionListener(e -> loadAllStudentsAsync());
        view.getBtnSearch().addActionListener(e -> searchByNameAsync());
        view.getBtnFilterCourse().addActionListener(e -> filterByCourseAsync());
        view.getBtnEnroll().addActionListener(e -> enrollSelectedStudent());
        view.getBtnUnenroll().addActionListener(e -> unenrollSelectedStudent());
        view.getBtnTop5().addActionListener(e -> loadTop5GpaAsync());
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromSelectedRow();
            }
        });
    }
    private void loadCourses() {
        view.getCboCourse().removeAllItems();
        courseDao.findAll().forEach(view.getCboCourse()::addItem);
    }
    private void addStudent() {
        try {
            String name = view.getTxtName().getText().trim();
            String email = view.getTxtEmail().getText().trim();
            double gpa = Double.parseDouble(view.getTxtGpa().getText().trim());
            if (name.isBlank()) throw new IllegalArgumentException("Name is empty");
            if (email.isBlank()) throw new IllegalArgumentException("Email is empty");
            if (gpa < 0 || gpa > 10) throw new IllegalArgumentException("GPA must be from 0 to 10");
            studentDao.save(new Student(name, email, gpa));
            clearForm();
            loadAllStudentsAsync();
            JOptionPane.showMessageDialog(view, "Student added");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Add failed: " + ex.getMessage());
        }
    }
    private void loadAllStudentsAsync() {
        new SwingWorker<List<Student>, Void>() {
            @Override
            protected List<Student> doInBackground() {
                return studentDao.findAll();
            }
            @Override
            protected void done() {
                try {
                    view.getTableModel().setStudents(get());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Load failed: " + ex.getMessage());
                }
            }
        }.execute();
    }
    private void searchByNameAsync() {
        String keyword = view.getTxtSearch().getText().trim();
        new SwingWorker<List<Student>, Void>() {
            @Override
            protected List<Student> doInBackground() {
                return studentDao.searchByName(keyword);
            }
            @Override
            protected void done() {
                try {
                    view.getTableModel().setStudents(get());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Search failed: " + ex.getMessage());
                }
            }
        }.execute();
    }
    private void filterByCourseAsync() {
        Course course = (Course) view.getCboCourse().getSelectedItem();
        if (course == null) {
            JOptionPane.showMessageDialog(view, "Please choose a course");
            return;
        }
        new SwingWorker<List<Student>, Void>() {
            @Override
            protected List<Student> doInBackground() {
                return studentDao.findByCourse(course);
            }
            @Override
            protected void done() {
                try {
                    view.getTableModel().setStudents(get());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Filter failed: " + ex.getMessage());
                }
            }
        }.execute();
    }
    private void loadTop5GpaAsync() {
        new SwingWorker<List<Student>, Void>() {
            @Override
            protected List<Student> doInBackground() {
                return studentDao.findTop5ByGpa();
            }
            @Override
            protected void done() {
                try {
                    view.getTableModel().setStudents(get());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Top 5 failed: " + ex.getMessage());
                }
            }
        }.execute();
    }
    private void enrollSelectedStudent() {
        try {
            if (view.getTxtId().getText().isBlank()) {
                JOptionPane.showMessageDialog(view, "Please select a student");
                return;
            }
            Course course = (Course) view.getCboCourse().getSelectedItem();
            if (course == null) {
                JOptionPane.showMessageDialog(view, "Please choose a course");
                return;
            }
            Long studentId = Long.parseLong(view.getTxtId().getText().trim());
            studentDao.enrollCourse(studentId, course.getId());
            loadAllStudentsAsync();
            JOptionPane.showMessageDialog(view, "Enroll success");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Enroll failed: " + ex.getMessage());
        }
    }
    private void unenrollSelectedStudent() {
        try {
            if (view.getTxtId().getText().isBlank()) {
                JOptionPane.showMessageDialog(view, "Please select a student");
                return;
            }
            Course course = (Course) view.getCboCourse().getSelectedItem();
            if (course == null) {
                JOptionPane.showMessageDialog(view, "Please choose a course");
                return;
            }
            Long studentId = Long.parseLong(view.getTxtId().getText().trim());
            studentDao.unenrollCourse(studentId, course.getId());
            loadAllStudentsAsync();
            JOptionPane.showMessageDialog(view, "Unenroll success");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Unenroll failed: " + ex.getMessage());
        }
    }
    private void fillFormFromSelectedRow() {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            Student s = view.getTableModel().getStudentAt(row);
            if (s != null) {
                view.getTxtId().setText(String.valueOf(s.getId()));
                view.getTxtName().setText(s.getFullName());
                view.getTxtEmail().setText(s.getEmail());
                view.getTxtGpa().setText(String.valueOf(s.getGpa()));
            }
        }
    }
    private void clearForm() {
        view.getTxtId().setText("");
        view.getTxtName().setText("");
        view.getTxtEmail().setText("");
        view.getTxtGpa().setText("");
    }
}