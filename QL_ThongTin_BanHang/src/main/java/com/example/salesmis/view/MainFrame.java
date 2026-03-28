package com.example.salesmis.view;

import com.example.salesmis.model.entity.Account;
import com.example.salesmis.model.enumtype.AccountRole;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(Account account, OrderProductPanel orderProductPanel, OrderManagementPanel orderPanel, ReportManagementPanel reportPanel, 
                     CustomerManagementPanel customerPanel, ProductManagementPanel productPanel,
                     RecipeManagementPanel recipePanel) {
        setTitle("MIS - Sales Management [" + account.getRole().name() + " : " + account.getUsername() + "]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 760);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.addTab("Bán Hàng (POS)", orderProductPanel);
        tabbedPane.addTab("Quản lý đơn hàng", orderPanel);
        tabbedPane.addTab("Quản lý khách hàng", customerPanel);
        tabbedPane.addTab("Công thức & Kích cỡ", recipePanel);

        if (account.getRole() == AccountRole.ADMIN) {
            tabbedPane.addTab("Quản lý sản phẩm", productPanel);
            tabbedPane.addTab("Báo cáo thống kê", reportPanel);
        }

        add(tabbedPane, BorderLayout.CENTER);
    }
}
