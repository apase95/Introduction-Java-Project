package org.quanly.gui;

import org.quanly.bll.ProductBLL;
import org.quanly.dal.CategoryDAL;
import org.quanly.dto.CategoryDTO;
import org.quanly.dto.ProductDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtName, txtPrice, txtQuantity;
    private JComboBox<CategoryDTO> cbCategory;
    private JButton btnAdd, btnUpdate, btnDelete;

    private ProductBLL productBLL = new ProductBLL();
    private CategoryDAL categoryDAL = new CategoryDAL(); // Dùng DAL hoặc BLL đều được cho Category đơn giản

    private int selectedProductId = -1;

    public MainForm() {
        setTitle("Quản Lý Kho - Mô Hình 3 Lớp");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadCategories();
        loadTableData();
    }
    private void initComponents() {
        JPanel panelInput = new JPanel(new GridLayout(4, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelInput.add(new JLabel("Tên sản phẩm:"));
        txtName = new JTextField();
        panelInput.add(txtName);

        panelInput.add(new JLabel("Giá bán:"));
        txtPrice = new JTextField();
        panelInput.add(txtPrice);

        panelInput.add(new JLabel("Số lượng:"));
        txtQuantity = new JTextField();
        panelInput.add(txtQuantity);

        panelInput.add(new JLabel("Loại sản phẩm:"));
        cbCategory = new JComboBox<>();
        panelInput.add(cbCategory);

        btnAdd = new JButton("Thêm Sản Phẩm");
        btnUpdate = new JButton("Sửa Sản Phẩm");
        btnDelete = new JButton("Xóa Sản Phẩm");

        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelInput, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Tên", "Giá", "Số Lượng", "Loại"}, 0);
        table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                selectedProductId = (int) tableModel.getValueAt(row, 0);
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtPrice.setText(tableModel.getValueAt(row, 2).toString());
                txtQuantity.setText(tableModel.getValueAt(row, 3).toString());

                String catName = tableModel.getValueAt(row, 4).toString();
                for (int i = 0; i < cbCategory.getItemCount(); i++) {
                    if (cbCategory.getItemAt(i).getName().equals(catName)) {
                        cbCategory.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadCategories() {
        List<CategoryDTO> categories = categoryDAL.getAll();
        for (CategoryDTO c : categories) {
            cbCategory.addItem(c);
        }
    }

    private void loadTableData() {
        tableModel.setRowCount(0); // Clear old data
        List<ProductDTO> products = productBLL.getAllProducts();
        for (ProductDTO p : products) {
            tableModel.addRow(new Object[]{
                    p.getId(), p.getName(), p.getPrice(), p.getQuantity(), p.getCategoryName()
            });
        }
    }
    private void addProduct() {
        try {
            ProductDTO p = new ProductDTO();
            p.setName(txtName.getText());
            p.setPrice(Double.parseDouble(txtPrice.getText()));
            p.setQuantity(Integer.parseInt(txtQuantity.getText()));

            CategoryDTO selectedCat = (CategoryDTO) cbCategory.getSelectedItem();
            p.setCategoryId(selectedCat.getId());

            String msg = productBLL.addProduct(p);
            JOptionPane.showMessageDialog(this, msg);
            loadTableData(); // Tải lại bảng sau khi thêm

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá và Số lượng phải là số hợp lệ!");
        }
    }

    private void updateProduct() {
        if (selectedProductId <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm từ bảng để sửa!");
            return;
        }
        try {
            ProductDTO p = new ProductDTO();
            p.setId(selectedProductId);
            p.setName(txtName.getText());
            p.setPrice(Double.parseDouble(txtPrice.getText()));
            p.setQuantity(Integer.parseInt(txtQuantity.getText()));

            CategoryDTO selectedCat = (CategoryDTO) cbCategory.getSelectedItem();
            p.setCategoryId(selectedCat.getId());

            String msg = productBLL.updateProduct(p);
            JOptionPane.showMessageDialog(this, msg);
            loadTableData();
            resetFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá và Số lượng phải là số hợp lệ!");
        }
    }

    private void deleteProduct() {
        if (selectedProductId <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm từ bảng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String msg = productBLL.deleteProduct(selectedProductId);
            JOptionPane.showMessageDialog(this, msg);
            loadTableData();
            resetFields();
        }
    }

    private void resetFields() {
        selectedProductId = -1;
        txtName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        if (cbCategory.getItemCount() > 0) cbCategory.setSelectedIndex(0);
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainForm().setVisible(true);
        });
    }
}
