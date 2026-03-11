package org.quanly.gui;

import org.quanly.bll.CustomerBLL;
import org.quanly.dto.CustomerDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtName, txtAddress, txtPhone;
    private JButton btnAdd, btnUpdate, btnDelete;

    private CustomerBLL customerBLL = new CustomerBLL();
    private int selectedCustomerId = -1;

    public CustomerForm() {
        setTitle("Quản Lý Khách Hàng");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form này không làm tắt cả app
        setLocationRelativeTo(null);
        initComponents();
        loadTableData();
    }

    private void initComponents() {
        JPanel panelInput = new JPanel(new GridLayout(3, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelInput.add(new JLabel("Tên khách hàng:"));
        txtName = new JTextField();
        panelInput.add(txtName);

        panelInput.add(new JLabel("Địa chỉ:"));
        txtAddress = new JTextField();
        panelInput.add(txtAddress);

        panelInput.add(new JLabel("Số điện thoại:"));
        txtPhone = new JTextField();
        panelInput.add(txtPhone);

        btnAdd = new JButton("Thêm Khách Hàng");
        btnUpdate = new JButton("Sửa Khách Hàng");
        btnDelete = new JButton("Xóa Khách Hàng");

        btnAdd.addActionListener(e -> addCustomer());
        btnUpdate.addActionListener(e -> updateCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelInput, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Tên", "Địa Chỉ", "SĐT"}, 0);
        table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                selectedCustomerId = (int) tableModel.getValueAt(row, 0);
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtAddress.setText(tableModel.getValueAt(row, 2) != null ? tableModel.getValueAt(row, 2).toString() : "");
                txtPhone.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
            }
        });

        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<CustomerDTO> customers = customerBLL.getAllCustomers();
        for (CustomerDTO c : customers) {
            tableModel.addRow(new Object[]{
                    c.getId(), c.getName(), c.getAddress(), c.getPhone()
            });
        }
    }

    private void addCustomer() {
        CustomerDTO c = new CustomerDTO();
        c.setName(txtName.getText());
        c.setAddress(txtAddress.getText());
        c.setPhone(txtPhone.getText());

        String msg = customerBLL.addCustomer(c);
        JOptionPane.showMessageDialog(this, msg);
        loadTableData();
        resetFields();
    }

    private void updateCustomer() {
        if (selectedCustomerId <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng từ bảng để sửa!");
            return;
        }
        CustomerDTO c = new CustomerDTO();
        c.setId(selectedCustomerId);
        c.setName(txtName.getText());
        c.setAddress(txtAddress.getText());
        c.setPhone(txtPhone.getText());

        String msg = customerBLL.updateCustomer(c);
        JOptionPane.showMessageDialog(this, msg);
        loadTableData();
        resetFields();
    }

    private void deleteCustomer() {
        if (selectedCustomerId <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng từ bảng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String msg = customerBLL.deleteCustomer(selectedCustomerId);
            JOptionPane.showMessageDialog(this, msg);
            loadTableData();
            resetFields();
        }
    }

    private void resetFields() {
        selectedCustomerId = -1;
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CustomerForm().setVisible(true);
        });
    }
}