package com.example.studentapp.view;

import com.example.studentapp.model.entity.Department;
import javax.swing.*;
import java.awt.*;

public class StudentView extends JFrame {
    private final JTextField txtId = new JTextField(10);
    private final JTextField txtName = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final JTextField txtGpa = new JTextField(10);
    private final JTextField txtSearch = new JTextField(20);
    private final JComboBox<Department> cboDepartment = new JComboBox<>();

    private final JButton btnAdd = new JButton("Add");
    private final JButton btnUpdate = new JButton("Update");
    private final JButton btnDelete = new JButton("Delete");
    private final JButton btnLoadAll = new JButton("Load All");
    private final JButton btnSearch = new JButton("Search");
    private final JButton btnFilterGpa = new JButton("GPA >= 8");
    private final JButton btnFilterDept = new JButton("Filter Department");
    private final JButton btnStats = new JButton("Statistics");

    private final JLabel lblTotal = new JLabel("Total: 0");
    private final JLabel lblAvg = new JLabel("Avg GPA: 0.00");
    private final JLabel lblExcellent = new JLabel("Excellent: 0");

    private final StudentTableModel tableModel = new StudentTableModel();
    private final JTable table = new JTable(tableModel);

    public StudentView() {
        setTitle("Student Management - Swing MVC + JPA OneToMany");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ID không được phép sửa vì là khóa chính tự tăng
        txtId.setEditable(false);

        // Panel nhập liệu
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        formPanel.add(new JLabel("ID"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Full Name"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Email"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("GPA"));
        formPanel.add(txtGpa);
        formPanel.add(new JLabel("Department"));
        formPanel.add(cboDepartment);
        formPanel.add(new JLabel("Search Name"));
        formPanel.add(txtSearch);

        // Panel nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnLoadAll);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnFilterGpa);
        buttonPanel.add(btnFilterDept);
        buttonPanel.add(btnStats);

        // Panel thống kê
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        statsPanel.add(lblTotal);
        statsPanel.add(lblAvg);
        statsPanel.add(lblExcellent);

        // Sắp xếp các Panel chính
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(formPanel, BorderLayout.CENTER);
        northPanel.add(statsPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JTextField getTxtGpa() { return txtGpa; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JComboBox<Department> getCboDepartment() { return cboDepartment; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnLoadAll() { return btnLoadAll; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnFilterGpa() { return btnFilterGpa; }
    public JButton getBtnFilterDept() { return btnFilterDept; }
    public JButton getBtnStats() { return btnStats; }
    public JLabel getLblTotal() { return lblTotal; }
    public JLabel getLblAvg() { return lblAvg; }
    public JLabel getLblExcellent() { return lblExcellent; }
    public JTable getTable() { return table; }
    public StudentTableModel getTableModel() { return tableModel; }
}