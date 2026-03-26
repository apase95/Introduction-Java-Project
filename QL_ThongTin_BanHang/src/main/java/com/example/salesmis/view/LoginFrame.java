package com.example.salesmis.view;

import com.example.salesmis.model.entity.Account;
import com.example.salesmis.service.AccountService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final AccountService accountService;
    private final Runnable onSuccess;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;

    public LoginFrame(AccountService accountService, Runnable onSuccess) {
        this.accountService = accountService;
        this.onSuccess = onSuccess;
        initComponents();
    }

    private void initComponents() {
        setTitle("Đăng nhập - Hệ thống quản lý thông tin bán hàng");
        setSize(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        txtUsername = new JTextField(20);
        panel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Mật khẩu:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtPassword = new JPasswordField(20);
        panel.add(txtPassword, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnLogin = new JButton("Đăng nhập");
        btnExit = new JButton("Thoát");
        btnPanel.add(btnLogin);
        btnPanel.add(btnExit);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        add(panel);

        btnLogin.addActionListener(e -> performLogin());
        btnExit.addActionListener(e -> System.exit(0));
        
        getRootPane().setDefaultButton(btnLogin);
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Account account = accountService.login(username, password);
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công với quyền " + account.getRole() + "!");
            this.dispose(); 
            if (onSuccess != null) {
                onSuccess.run();
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Đăng nhập thất bại", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
