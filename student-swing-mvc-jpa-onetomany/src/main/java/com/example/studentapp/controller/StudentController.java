package com.example.studentapp.controller;

import com.example.studentapp.model.dao.DepartmentDao;
import com.example.studentapp.model.dao.StudentDao;
import com.example.studentapp.model.dto.StudentStatsDto;
import com.example.studentapp.model.entity.Department;
import com.example.studentapp.model.entity.Student;
import com.example.studentapp.view.StudentView;
import javax.swing.*;
import java.util.Comparator;
import java.util.List;

public class StudentController {
    private final StudentView view;
    private final StudentDao studentDao;
    private final DepartmentDao departmentDao;

    public StudentController(StudentView view, StudentDao studentDao, DepartmentDao departmentDao) {
        this.view = view;
        this.studentDao = studentDao;
        this.departmentDao = departmentDao;
        initActions();
        loadDepartments();
        loadAllStudentsAsync();
        loadStatistics();
    }

    private void initActions() {
        view.getBtnAdd().addActionListener(e -> addStudent());
        view.getBtnUpdate().addActionListener(e -> updateStudent());
        view.getBtnDelete().addActionListener(e -> deleteStudent());
        view.getBtnLoadAll().addActionListener(e -> loadAllStudentsAsync());
        view.getBtnSearch().addActionListener(e -> searchStudentsAsync());
        view.getBtnFilterGpa().addActionListener(e -> filterExcellentStudentsAsync());
        view.getBtnFilterDept().addActionListener(e -> filterByDepartmentAsync());
        view.getBtnStats().addActionListener(e -> loadStatistics());

        // Sự kiện khi click vào một dòng trên bảng
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromSelectedRow();
            }
        });
    }

    private void loadDepartments() {
        view.getCboDepartment().removeAllItems();
        departmentDao.findAll().forEach(view.getCboDepartment()::addItem);
    }

    private void addStudent() {
        try {
            String name = view.getTxtName().getText().trim();
            String email = view.getTxtEmail().getText().trim();
            double gpa = Double.parseDouble(view.getTxtGpa().getText().trim());
            Department dept = (Department) view.getCboDepartment().getSelectedItem();

            validateInput(name, email, gpa, dept);
            studentDao.save(new Student(name, email, gpa, dept));

            clearForm();
            loadAllStudentsAsync();
            loadStatistics();
            showMessage("Added successfully");
        } catch (Exception ex) {
            showMessage("Add failed: " + ex.getMessage());
        }
    }

    private void updateStudent() {
        try {
            if (view.getTxtId().getText().isBlank()) {
                showMessage("Please select a student first");
                return;
            }
            Long id = Long.parseLong(view.getTxtId().getText().trim());
            Student student = studentDao.findById(id);
            if (student == null) {
                showMessage("Student not found");
                return;
            }

            String name = view.getTxtName().getText().trim();
            String email = view.getTxtEmail().getText().trim();
            double gpa = Double.parseDouble(view.getTxtGpa().getText().trim());
            Department dept = (Department) view.getCboDepartment().getSelectedItem();

            validateInput(name, email, gpa, dept);
            student.setFullName(name);
            student.setEmail(email);
            student.setGpa(gpa);
            student.setDepartment(dept);

            studentDao.update(student);
            clearForm();
            loadAllStudentsAsync();
            loadStatistics();
            showMessage("Updated successfully");
        } catch (Exception ex) {
            showMessage("Update failed: " + ex.getMessage());
        }
    }

    private void deleteStudent() {
        try {
            if (view.getTxtId().getText().isBlank()) {
                showMessage("Please select a student first");
                return;
            }
            Long id = Long.parseLong(view.getTxtId().getText().trim());
            studentDao.delete(id);
            clearForm();
            loadAllStudentsAsync();
            loadStatistics();
            showMessage("Deleted successfully");
        } catch (Exception ex) {
            showMessage("Delete failed: " + ex.getMessage());
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
                    showMessage("Load failed: " + ex.getMessage());
                }
            }
        }.execute();
    }

    private void searchStudentsAsync() {
        String keyword = view.getTxtSearch().getText().trim();
        new SwingWorker<List<Student>, Void>() {
            @Override
            protected List<Student> doInBackground() {
                return studentDao.searchByName(keyword);
            }
            @Override
            protected void done() {
                try {
                    List<Student> sorted = get().stream()
                            .sorted(Comparator.comparing(Student::getFullName))
                            .toList();
                    view.getTableModel().setStudents(sorted);
                } catch (Exception ex) {
                    showMessage("Search failed: " + ex.getMessage());
                }
            }
        }.execute();
    }

    private void filterExcellentStudentsAsync() {
        new SwingWorker<List<Student>, Void>() {
            @Override
            protected List<Student> doInBackground() {
                return studentDao.findByMinGpa(8.0);
            }
            @Override
            protected void done() {
                try {
                    view.getTableModel().setStudents(get());
                } catch (Exception ex) {
                    showMessage("Filter GPA failed: " + ex.getMessage());
                }
            }
        }.execute();
    }

    private void filterByDepartmentAsync() {
        Department dept = (Department) view.getCboDepartment().getSelectedItem();
        if (dept == null) {
            showMessage("Please choose a department");
            return;
        }
        new SwingWorker<List<Student>, Void>() {
            @Override
            protected List<Student> doInBackground() {
                return studentDao.findByDepartment(dept);
            }
            @Override
            protected void done() {
                try {
                    view.getTableModel().setStudents(get());
                } catch (Exception ex) {
                    showMessage("Filter department failed: " + ex.getMessage());
                }
            }
        }.execute();
    }

    private void loadStatistics() {
        new SwingWorker<StudentStatsDto, Void>() {
            @Override
            protected StudentStatsDto doInBackground() {
                return studentDao.getStatistics();
            }
            @Override
            protected void done() {
                try {
                    StudentStatsDto stats = get();
                    view.getLblTotal().setText("Total: " + stats.getTotalStudents());
                    view.getLblAvg().setText(String.format("Avg GPA: %.2f", stats.getAvgGpa()));
                    view.getLblExcellent().setText("Excellent: " + stats.getExcellentStudents());
                } catch (Exception ex) {
                    showMessage("Statistics failed: " + ex.getMessage());
                }
            }
        }.execute();
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
                view.getCboDepartment().setSelectedItem(s.getDepartment());
            }
        }
    }

    private void validateInput(String name, String email, double gpa, Department dept) {
        if (name.isBlank()) throw new IllegalArgumentException("Name must not be empty");
        if (email.isBlank()) throw new IllegalArgumentException("Email must not be empty");
        if (gpa < 0 || gpa > 10) throw new IllegalArgumentException("GPA must be from 0 to 10");
        if (dept == null) throw new IllegalArgumentException("Department must be selected");
    }

    private void clearForm() {
        view.getTxtId().setText("");
        view.getTxtName().setText("");
        view.getTxtEmail().setText("");
        view.getTxtGpa().setText("");
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(view, message);
    }
}