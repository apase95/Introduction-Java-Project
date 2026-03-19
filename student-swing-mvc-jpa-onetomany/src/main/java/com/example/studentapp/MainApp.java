package com.example.studentapp;

import com.example.studentapp.controller.StudentController;
import com.example.studentapp.model.dao.DepartmentDao;
import com.example.studentapp.model.dao.StudentDao;
import com.example.studentapp.view.StudentView;
import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        // Chạy ứng dụng giao diện trong luồng Event Dispatch Thread (EDT) của Swing
        SwingUtilities.invokeLater(() -> {
            // 1. Khởi tạo View
            StudentView view = new StudentView();

            // 2. Khởi tạo các DAO (Model)
            StudentDao studentDao = new StudentDao();
            DepartmentDao departmentDao = new DepartmentDao();

            // 3. Khởi tạo Controller và truyền View + Model vào
            // Controller sẽ tự động đăng ký các sự kiện nút bấm
            new StudentController(view, studentDao, departmentDao);

            // 4. Hiển thị cửa sổ
            view.setVisible(true);
        });
    }
}