package com.example.studentapp.view;

import com.example.studentapp.model.entity.Student;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {

    private final String[] columns = {"ID", "Full Name", "Email", "GPA", "Department"};
    private List<Student> students = new ArrayList<>();

    public void setStudents(List<Student> students) {
        this.students = students != null ? students : new ArrayList<>();
        fireTableDataChanged();
    }

    public Student getStudentAt(int row) {
        if (row < 0 || row >= students.size()) {
            return null;
        }
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
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student s = students.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> s.getId();
            case 1 -> s.getFullName();
            case 2 -> s.getEmail();
            case 3 -> s.getGpa();
            case 4 -> s.getDepartment() != null ? s.getDepartment().getName() : "";
            default -> null;
        };
    }
}