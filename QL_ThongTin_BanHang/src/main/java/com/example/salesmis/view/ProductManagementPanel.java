package com.example.salesmis.view;

import com.example.salesmis.controller.ProductController;
import com.example.salesmis.model.entity.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class ProductManagementPanel extends JPanel {
    private final ProductController productController;

    private JTextField txtId;
    private JTextField txtSku;
    private JTextField txtName;
    private JTextField txtCategory;
    private JTextField txtPrice;
    private JTextField txtStock;
    private JCheckBox chkActive;
    private JTextField txtSearch;

    private JTable tblProducts;
    private DefaultTableModel tableModel;

    private Long selectedId;

    public ProductManagementPanel(ProductController productController) {
        this.productController = productController;
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
        txtSku = new JTextField();
        txtName = new JTextField();
        txtCategory = new JTextField();
        txtPrice = new JTextField();
        txtStock = new JTextField();
        chkActive = new JCheckBox("Đang bán");
        chkActive.setSelected(true);
        txtSearch = new JTextField();

        form.add(new JLabel("ID")); form.add(txtId);
        form.add(new JLabel("SKU")); form.add(txtSku);
        form.add(new JLabel("Tên SP")); form.add(txtName);
        form.add(new JLabel("Danh mục")); form.add(txtCategory);
        form.add(new JLabel("Đơn giá")); form.add(txtPrice);
        form.add(new JLabel("Tồn kho")); form.add(txtStock);
        form.add(new JLabel("Trạng thái")); form.add(chkActive);

        topPanel.add(form, BorderLayout.NORTH);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNew = new JButton("Làm mới");
        JButton btnSave = new JButton("Thêm mới");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnSearch = new JButton("Tìm");

        buttons.add(btnNew);
        buttons.add(btnSave);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(txtSearch); txtSearch.setColumns(15);
        buttons.add(btnSearch);
        
        btnNew.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> saveProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnSearch.addActionListener(e -> searchProducts());

        topPanel.add(buttons, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "SKU", "Tên SP", "Danh mục", "Đơn giá", "Tồn kho", "Đang bán"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblProducts = new JTable(tableModel);
        tblProducts.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblProducts.getSelectedRow() != -1) {
                loadToForm();
            }
        });

        add(new JScrollPane(tblProducts), BorderLayout.CENTER);
    }

    private void loadData() {
        renderTable(productController.getAllProducts());
    }

    private void renderTable(List<Product> list) {
        tableModel.setRowCount(0);
        for (Product p : list) {
            tableModel.addRow(new Object[]{
                p.getId(), p.getSku(), p.getProductName(), p.getCategory(), p.getUnitPrice(), p.getStockQty(), p.getActive()
            });
        }
    }

    private void loadToForm() {
        int row = tblProducts.getSelectedRow();
        if (row == -1) return;
        selectedId = Long.valueOf(tableModel.getValueAt(row, 0).toString());
        txtId.setText(selectedId.toString());
        txtSku.setText(tableModel.getValueAt(row, 1).toString());
        txtName.setText(tableModel.getValueAt(row, 2).toString());
        txtCategory.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
        txtPrice.setText(tableModel.getValueAt(row, 4).toString());
        txtStock.setText(tableModel.getValueAt(row, 5).toString());
        chkActive.setSelected((Boolean) tableModel.getValueAt(row, 6));
    }

    private void clearForm() {
        selectedId = null;
        txtId.setText("");
        txtSku.setText("");
        txtName.setText("");
        txtCategory.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        chkActive.setSelected(true);
        tblProducts.clearSelection();
    }

    private void saveProduct() {
        try {
            BigDecimal price = new BigDecimal(txtPrice.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());
            Product p = productController.createProduct(
                txtSku.getText().trim(), txtName.getText().trim(), txtCategory.getText().trim(),
                price, stock, chkActive.isSelected()
            );
            JOptionPane.showMessageDialog(this, "Thêm SP thành công: " + p.getSku());
            clearForm();
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm SP: " + ex.getMessage());
        }
    }

    private void updateProduct() {
        try {
            if (selectedId == null) return;
            BigDecimal price = new BigDecimal(txtPrice.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());
            Product p = productController.updateProduct(
                selectedId, txtSku.getText().trim(), txtName.getText().trim(), txtCategory.getText().trim(),
                price, stock, chkActive.isSelected()
            );
            JOptionPane.showMessageDialog(this, "Cập nhật thành công: " + p.getSku());
            clearForm();
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật SP: " + ex.getMessage());
        }
    }

    private void deleteProduct() {
        try {
            if (selectedId == null) return;
            if (JOptionPane.showConfirmDialog(this, "Xóa SP này?") == JOptionPane.YES_OPTION) {
                productController.deleteProduct(selectedId);
                clearForm();
                loadData();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa SP: " + ex.getMessage());
        }
    }

    private void searchProducts() {
        renderTable(productController.searchProducts(txtSearch.getText().trim()));
    }
}
