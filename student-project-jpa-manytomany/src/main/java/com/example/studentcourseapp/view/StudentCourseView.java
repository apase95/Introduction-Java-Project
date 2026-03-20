package com.example.studentcourseapp.view;
import com.example.studentcourseapp.model.entity.Course;
import javax.swing.*;
import java.awt.*;
public class StudentCourseView extends JFrame {
    private final JTextField txtId = new JTextField(10);
    private final JTextField txtName = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final JTextField txtGpa = new JTextField(10);
    private final JTextField txtSearch = new JTextField(20);
    private final JComboBox<Course> cboCourse = new JComboBox<>();
    private final JButton btnAddStudent = new JButton("Add Student");
    private final JButton btnLoadAll = new JButton("Load All");
    private final JButton btnSearch = new JButton("Search Name");
    private final JButton btnFilterCourse = new JButton("Filter Course");
    private final JButton btnEnroll = new JButton("Enroll Course");
    private final JButton btnUnenroll = new JButton("Unenroll Course");
    private final JButton btnTop5 = new JButton("Top 5 GPA");
    private final StudentTableModel tableModel = new StudentTableModel();
    private final JTable table = new JTable(tableModel);
    public StudentCourseView() {
        setTitle("Student - Course ManyToMany");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        txtId.setEditable(false);
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
        formPanel.add(new JLabel("Course"));
        formPanel.add(cboCourse);
        formPanel.add(new JLabel("Search Name"));
        formPanel.add(txtSearch);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnAddStudent);
        buttonPanel.add(btnLoadAll);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnFilterCourse);
        buttonPanel.add(btnEnroll);
        buttonPanel.add(btnUnenroll);
        buttonPanel.add(btnTop5);
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JTextField getTxtGpa() { return txtGpa; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JComboBox<Course> getCboCourse() { return cboCourse; }
    public JButton getBtnAddStudent() { return btnAddStudent; }
    public JButton getBtnLoadAll() { return btnLoadAll; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnFilterCourse() { return btnFilterCourse; }
    public JButton getBtnEnroll() { return btnEnroll; }
    public JButton getBtnUnenroll() { return btnUnenroll; }
    public JButton getBtnTop5() { return btnTop5; }
    public JTable getTable() { return table; }
    public StudentTableModel getTableModel() { return tableModel; }
}