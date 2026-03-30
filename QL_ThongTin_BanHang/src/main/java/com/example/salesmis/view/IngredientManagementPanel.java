package com.example.salesmis.view;

import com.example.salesmis.controller.IngredientController;
import com.example.salesmis.model.entity.Ingredient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class IngredientManagementPanel extends JPanel {
    private final IngredientController ingredientController;

    private JTextField txtId;
    private JTextField txtCode;
    private JTextField txtName;
    private JTextField txtUnit;
    private JTextField txtStock;
    private JCheckBox chkActive;
    private JTextField txtSearch;

    private JTable tblIngredients;
    private DefaultTableModel tableModel;

    private Long selectedId;

    public IngredientManagementPanel(IngredientController ingredientController) {
        this.ingredientController = ingredientController;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
 
        // Form
        JPanel form = new JPanel(new GridLayout(3, 4, 8, 8));
        txtId = new JTextField();
        txtId.setEditable(false);
        txtCode = new JTextField();
        txtName = new JTextField();
        txtUnit = new JTextField();
        txtStock = new JTextField();
        chkActive = new JCheckBox("Đang hoạt động");
        chkActive.setSelected(true);
        txtSearch = new JTextField();

        form.add(new JLabel("ID")); form.add(txtId);
        form.add(new JLabel("Mã nguyên liệu")); form.add(txtCode);
        form.add(new JLabel("Tên nguyên liệu")); form.add(txtName);
        form.add(new JLabel("Đơn vị tính")); form.add(txtUnit);
        form.add(new JLabel("Tồn kho")); form.add(txtStock);
        form.add(new JLabel("Trạng thái")); form.add(chkActive);

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
        btnSave.addActionListener(e -> saveIngredient());
        btnUpdate.addActionListener(e -> updateIngredient());
        btnDelete.addActionListener(e -> deleteIngredient());
        btnSearch.addActionListener(e -> searchIngredients());

        topPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Mã nguyên liệu", "Tên nguyên liệu", "DVT", "Tồn kho", "Trạng thái"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblIngredients = new JTable(tableModel);
        tblIngredients.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblIngredients.getSelectedRow() != -1) {
                loadToForm();
            }
        });

        add(new JScrollPane(tblIngredients), BorderLayout.CENTER);
    }

    private void loadData() {
        renderTable(ingredientController.getAllIngredients());
    }

    private void renderTable(List<Ingredient> list) {
        tableModel.setRowCount(0);
        for (Ingredient i : list) {
            tableModel.addRow(new Object[]{
                i.getId(), i.getIngredientCode(), i.getIngredientName(), i.getUnit(), i.getStockQty(), i.getActive() ? "Đang hoạt động" : "Ngừng hoạt động"
            });
        }
    }

    private void loadToForm() {
        int row = tblIngredients.getSelectedRow();
        if (row == -1) return;
        selectedId = Long.valueOf(tableModel.getValueAt(row, 0).toString());
        txtId.setText(selectedId.toString());
        txtCode.setText(tableModel.getValueAt(row, 1).toString());
        txtName.setText(tableModel.getValueAt(row, 2).toString());
        txtUnit.setText(tableModel.getValueAt(row, 3).toString());
        txtStock.setText(tableModel.getValueAt(row, 4).toString());
        chkActive.setSelected("Đang hoạt động".equals(tableModel.getValueAt(row, 5).toString()));
    }

    private void clearForm() {
        selectedId = null;
        txtId.setText("");
        txtCode.setText("");
        txtName.setText("");
        txtUnit.setText("");
        txtStock.setText("");
        chkActive.setSelected(true);
        tblIngredients.clearSelection();
        loadData();
    }

    private void saveIngredient() {
        try {
            BigDecimal stock = new BigDecimal(txtStock.getText().trim());
            
            Ingredient i = ingredientController.createIngredient(
                txtCode.getText().trim(), txtName.getText().trim(), txtUnit.getText().trim(),
                stock, chkActive.isSelected()
            );
            JOptionPane.showMessageDialog(this, "Thêm nguyên liệu thành công: " + i.getIngredientCode());
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tồn kho phải là số hợp lệ.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateIngredient() {
        try {
            if (selectedId == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nguyên liệu để cập nhật.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            BigDecimal stock = new BigDecimal(txtStock.getText().trim());

            Ingredient i = ingredientController.updateIngredient(
                selectedId, txtCode.getText().trim(), txtName.getText().trim(), txtUnit.getText().trim(),
                stock, chkActive.isSelected()
            );
            JOptionPane.showMessageDialog(this, "Cập nhật thành công: " + i.getIngredientCode());
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tồn kho phải là số hợp lệ.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteIngredient() {
        try {
            if (selectedId == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nguyên liệu để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Xóa nguyên liệu này?") == JOptionPane.YES_OPTION) {
                ingredientController.deleteIngredient(selectedId);
                clearForm();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa nguyên liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchIngredients() {
        renderTable(ingredientController.searchIngredients(txtSearch.getText().trim()));
    }
}
