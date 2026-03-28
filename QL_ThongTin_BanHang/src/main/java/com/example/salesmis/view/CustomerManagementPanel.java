package com.example.salesmis.view;

import com.example.salesmis.controller.CustomerController;
import com.example.salesmis.model.entity.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerManagementPanel extends JPanel {
    private final CustomerController customerController;

    private JTextField txtId;
    private JTextField txtCode;
    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtAddress;
    private JCheckBox chkActive;
    private JTextField txtSearch;

    private JTable tblCustomers;
    private DefaultTableModel tableModel;

    private Long selectedId;

    public CustomerManagementPanel(CustomerController customerController) {
        this.customerController = customerController;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel topPanel = new JPanel(new BorderLayout(8, 8));

        // Form
        JPanel form = new JPanel(new GridLayout(4, 4, 8, 8));
        txtId = new JTextField();
        txtId.setEditable(false);
        txtCode = new JTextField();
        txtName = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();
        txtAddress = new JTextField();
        chkActive = new JCheckBox("Hoạt động");
        chkActive.setSelected(true);
        txtSearch = new JTextField();

        form.add(new JLabel("ID")); form.add(txtId);
        form.add(new JLabel("Mã KH")); form.add(txtCode);
        form.add(new JLabel("Họ tên")); form.add(txtName);
        form.add(new JLabel("SĐT")); form.add(txtPhone);
        form.add(new JLabel("Email")); form.add(txtEmail);
        form.add(new JLabel("Cũ/Mới")); form.add(chkActive);
        form.add(new JLabel("Địa chỉ")); form.add(txtAddress);

        topPanel.add(form, BorderLayout.NORTH);

        // Buttons
        JPanel buttonsPanel = new JPanel(new BorderLayout());

        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNew = new JButton("Làm mới");
        JButton btnSave = new JButton("Thêm mới");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        leftButtons.add(btnNew);
        leftButtons.add(btnSave);
        leftButtons.add(btnUpdate);
        leftButtons.add(btnDelete);

        JPanel rightSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSearch = new JButton("Tìm kiếm");
        txtSearch.setColumns(15);
        rightSearch.add(txtSearch);
        rightSearch.add(btnSearch);

        buttonsPanel.add(leftButtons, BorderLayout.WEST);
        buttonsPanel.add(rightSearch, BorderLayout.EAST);
        
        btnNew.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> saveCustomer());
        btnUpdate.addActionListener(e -> updateCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnSearch.addActionListener(e -> searchCustomers());

        topPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Mã KH", "Họ tên", "SĐT", "Email", "Địa chỉ", "Hoạt động"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblCustomers = new JTable(tableModel);
        tblCustomers.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblCustomers.getSelectedRow() != -1) {
                loadToForm();
            }
        });

        add(new JScrollPane(tblCustomers), BorderLayout.CENTER);
    }

    private void loadData() {
        renderTable(customerController.getAllCustomers());
    }

    private void renderTable(List<Customer> list) {
        tableModel.setRowCount(0);
        for (Customer c : list) {
            tableModel.addRow(new Object[]{
                c.getId(), c.getCustomerCode(), c.getFullName(), c.getPhone(), c.getEmail(), c.getAddress(), c.getActive()
            });
        }
    }

    private void loadToForm() {
        int row = tblCustomers.getSelectedRow();
        if (row == -1) return;
        selectedId = Long.valueOf(tableModel.getValueAt(row, 0).toString());
        txtId.setText(selectedId.toString());
        txtCode.setText(tableModel.getValueAt(row, 1).toString());
        txtName.setText(tableModel.getValueAt(row, 2).toString());
        txtPhone.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
        txtEmail.setText(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "");
        txtAddress.setText(tableModel.getValueAt(row, 5) != null ? tableModel.getValueAt(row, 5).toString() : "");
        chkActive.setSelected((Boolean) tableModel.getValueAt(row, 6));
    }

    private void clearForm() {
        selectedId = null;
        txtId.setText("");
        txtCode.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        chkActive.setSelected(true);
        tblCustomers.clearSelection();
        loadData();
    }

    private void saveCustomer() {
        try {
            Customer c = customerController.createCustomer(
                txtCode.getText().trim(), txtName.getText().trim(), txtPhone.getText().trim(),
                txtEmail.getText().trim(), txtAddress.getText().trim(), chkActive.isSelected()
            );
            JOptionPane.showMessageDialog(this, "Thêm KH thành công: " + c.getCustomerCode());
            clearForm();
            loadData();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCustomer() {
        try {
            if (selectedId == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để cập nhật.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Customer c = customerController.updateCustomer(
                selectedId, txtCode.getText().trim(), txtName.getText().trim(), txtPhone.getText().trim(),
                txtEmail.getText().trim(), txtAddress.getText().trim(), chkActive.isSelected()
            );
            JOptionPane.showMessageDialog(this, "Cập nhật thành công: " + c.getCustomerCode());
            clearForm();
            loadData();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCustomer() {
        try {
            if (selectedId == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Xóa KH này?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                customerController.deleteCustomer(selectedId);
                clearForm();
                loadData();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa KH: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchCustomers() {
        renderTable(customerController.searchCustomers(txtSearch.getText().trim()));
    }
}
