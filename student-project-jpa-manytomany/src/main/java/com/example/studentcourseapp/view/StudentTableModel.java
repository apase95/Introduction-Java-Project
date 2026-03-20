package com.example.studentcourseapp.view;

import com.example.studentcourseapp.model.entity.Student;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentTableModel extends AbstractTableModel {
    private final String[] columns = {"ID", "Full Name", "Email", "GPA", "Courses"};
    private List<Student> students = new ArrayList<>();
    public void setStudents(List<Student> students) {
        this.students = students;
        fireTableDataChanged();
    }
    public Student getStudentAt(int row) {
        if (row < 0 || row >= students.size()) return null;
        return students.get(row);
    }
    @Override
    public int getRowCount() {
        return students.size();
    }
    @Override
    public int getColumnCount() {
        return columns.length;
    }
    @Override
    public String getColumnName(int column) {
        return columns[ column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student s = students.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> s.getId();
            case 1 -> s.getFullName();
            case 2 -> s.getEmail();
            case 3 -> s.getGpa();
            case 4 -> s.getCourses().stream()
                    .map(course -> course.getCourseName())
                    .sorted()
                    .collect(Collectors.joining(", "));
            default -> null;
        };
    }
}
