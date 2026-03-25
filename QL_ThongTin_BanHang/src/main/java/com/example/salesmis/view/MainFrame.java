package com.example.salesmis.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(OrderManagementPanel orderPanel, ReportManagementPanel reportPanel, 
                     CustomerManagementPanel customerPanel, ProductManagementPanel productPanel) {
        setTitle("MIS - Sales Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 760);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.addTab("Quản lý đơn hàng", orderPanel);
        tabbedPane.addTab("Quản lý khách hàng", customerPanel);
        tabbedPane.addTab("Quản lý sản phẩm", productPanel);
        tabbedPane.addTab("Báo cáo thống kê", reportPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }
}
