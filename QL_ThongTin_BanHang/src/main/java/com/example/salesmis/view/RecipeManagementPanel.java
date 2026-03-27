package com.example.salesmis.view;

import com.example.salesmis.controller.RecipeController;
import com.example.salesmis.model.entity.Ingredient;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.model.entity.Recipe;
import com.example.salesmis.model.entity.RecipeIngredient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class RecipeManagementPanel extends JPanel {
    private final RecipeController recipeController;

    private JList<Product> listProducts;
    private DefaultListModel<Product> productListModel;
    private JTextField txtSearchProduct;

    private JTable tblRecipes;
    private DefaultTableModel recipeTableModel;
    private JTextField txtRecipeName;
    private JCheckBox chkRecipeActive;
    private Long selectedRecipeId;

    private JTable tblIngredients;
    private DefaultTableModel ingredientTableModel;
    private JComboBox<Ingredient> cboIngredient;
    private JTextField txtQuantity;

    public RecipeManagementPanel(RecipeController recipeController) {
        this.recipeController = recipeController;
        initComponents();
        loadProducts();
        loadIngredients();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Let's divide into Left (Product Selection) and Right (Recipe & Ingredients)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buildProductPanel(), buildRightPanel());
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel buildProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("1. Chọn Sản phẩm"));

        JPanel searchPanel = new JPanel(new BorderLayout());
        txtSearchProduct = new JTextField();
        txtSearchProduct.addActionListener(e -> filterProducts());
        searchPanel.add(new JLabel("Tìm kiếm (Enter): "), BorderLayout.WEST);
        searchPanel.add(txtSearchProduct, BorderLayout.CENTER);

        productListModel = new DefaultListModel<>();
        listProducts = new JList<>(productListModel);
        listProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listProducts.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Product p = listProducts.getSelectedValue();
                if (p != null) {
                    loadRecipes(p.getId());
                } else {
                    recipeTableModel.setRowCount(0);
                    clearRecipeForm();
                    ingredientTableModel.setRowCount(0);
                }
            }
        });

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(listProducts), BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildRightPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10)); // Top: Recipes, Bottom: Ingredients
        panel.add(buildRecipePanel());
        panel.add(buildIngredientPanel());
        return panel;
    }

    private JPanel buildRecipePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("2. Các Size / Biến thể (Công thức)"));

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtRecipeName = new JTextField(15);
        chkRecipeActive = new JCheckBox("Hoạt động", true);
        JButton btnSaveRecipe = new JButton("Lưu");
        JButton btnDeleteRecipe = new JButton("Xóa");
        JButton btnClearRecipe = new JButton("Tạo Code Mới / Clear");

        formPanel.add(new JLabel("Tên Size (VD: Size M): "));
        formPanel.add(txtRecipeName);
        formPanel.add(chkRecipeActive);
        formPanel.add(btnSaveRecipe);
        formPanel.add(btnDeleteRecipe);
        formPanel.add(btnClearRecipe);

        btnSaveRecipe.addActionListener(e -> saveRecipe());
        btnDeleteRecipe.addActionListener(e -> deleteRecipe());
        btnClearRecipe.addActionListener(e -> clearRecipeForm());

        recipeTableModel = new DefaultTableModel(new String[]{"ID", "Tên Size/Biến thể", "Hoạt động"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblRecipes = new JTable(recipeTableModel);
        tblRecipes.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblRecipes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadRecipeToForm();
            }
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblRecipes), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildIngredientPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("3. Định mức Nguyên liệu"));

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cboIngredient = new JComboBox<>();
        txtQuantity = new JTextField(8);
        JButton btnAddIngredient = new JButton("Thêm Nguyên Liệu");
        JButton btnRemoveIngredient = new JButton("Xóa dòng chọn");

        formPanel.add(new JLabel("Nguyên liệu:"));
        formPanel.add(cboIngredient);
        formPanel.add(new JLabel("Định lượng:"));
        formPanel.add(txtQuantity);
        formPanel.add(btnAddIngredient);
        formPanel.add(btnRemoveIngredient);

        btnAddIngredient.addActionListener(e -> addIngredientToRecipe());
        btnRemoveIngredient.addActionListener(e -> removeIngredientFromRecipe());

        ingredientTableModel = new DefaultTableModel(new String[]{"ID NL", "Tên Nguyên liệu", "Đvt", "Định lượng"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblIngredients = new JTable(ingredientTableModel);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblIngredients), BorderLayout.CENTER);
        return panel;
    }

    private void loadProducts() {
        List<Product> products = recipeController.getAllProducts();
        productListModel.clear();
        for (Product p : products) {
            productListModel.addElement(p);
        }
    }

    private void filterProducts() {
        String keyword = txtSearchProduct.getText().toLowerCase();
        List<Product> products = recipeController.getAllProducts();
        productListModel.clear();
        for (Product p : products) {
            if (p.getProductName().toLowerCase().contains(keyword) || p.getSku().toLowerCase().contains(keyword)) {
                productListModel.addElement(p);
            }
        }
    }

    private void loadIngredients() {
        cboIngredient.removeAllItems();
        for (Ingredient i : recipeController.getAllIngredients()) {
            cboIngredient.addItem(i);
        }
    }

    private void loadRecipes(Long productId) {
        recipeTableModel.setRowCount(0);
        clearRecipeForm();
        ingredientTableModel.setRowCount(0);

        List<Recipe> recipes = recipeController.getRecipesByProduct(productId);
        for (Recipe r : recipes) {
            recipeTableModel.addRow(new Object[]{
                    r.getId(),
                    r.getVariationName(),
                    r.getActive()
            });
        }
    }

    private void clearRecipeForm() {
        selectedRecipeId = null;
        txtRecipeName.setText("");
        chkRecipeActive.setSelected(true);
        tblRecipes.clearSelection();
        ingredientTableModel.setRowCount(0);
    }

    private void loadRecipeToForm() {
        int row = tblRecipes.getSelectedRow();
        if (row != -1) {
            selectedRecipeId = Long.valueOf(tblRecipes.getValueAt(row, 0).toString());
            txtRecipeName.setText(tblRecipes.getValueAt(row, 1).toString());
            chkRecipeActive.setSelected((Boolean) tblRecipes.getValueAt(row, 2));
            
            loadRecipeIngredients(selectedRecipeId);
        }
    }

    private void loadRecipeIngredients(Long recipeId) {
        ingredientTableModel.setRowCount(0);
        Product p = listProducts.getSelectedValue();
        if (p == null) return;
        
        List<Recipe> recipes = recipeController.getRecipesByProduct(p.getId());
        for (Recipe r : recipes) {
            if (r.getId().equals(recipeId)) {
                for (RecipeIngredient ri : r.getRecipeIngredients()) {
                    ingredientTableModel.addRow(new Object[]{
                            ri.getIngredient().getId(),
                            ri.getIngredient().getIngredientName(),
                            ri.getIngredient().getUnit(),
                            ri.getQuantity()
                    });
                }
                break;
            }
        }
    }

    private void saveRecipe() {
        try {
            Product p = listProducts.getSelectedValue();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn Sản phẩm bên trái.");
                return;
            }

            recipeController.saveRecipe(p.getId(), selectedRecipeId, txtRecipeName.getText().trim(), chkRecipeActive.isSelected());
            JOptionPane.showMessageDialog(this, "Lưu Công thức thành công.");
            loadRecipes(p.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRecipe() {
        if (selectedRecipeId == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Công thức để xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa Công thức này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                recipeController.deleteRecipe(selectedRecipeId);
                Product p = listProducts.getSelectedValue();
                if (p != null) loadRecipes(p.getId());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addIngredientToRecipe() {
        if (selectedRecipeId == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một Công thức/Size ở bảng trên.");
            return;
        }
        Ingredient ing = (Ingredient) cboIngredient.getSelectedItem();
        if (ing == null) return;

        try {
            BigDecimal qty = new BigDecimal(txtQuantity.getText().trim());
            recipeController.addIngredientToRecipe(selectedRecipeId, ing.getId(), qty);
            loadRecipeIngredients(selectedRecipeId);
            txtQuantity.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Định lượng phải là số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeIngredientFromRecipe() {
        if (selectedRecipeId == null) return;
        int row = tblIngredients.getSelectedRow();
        if (row != -1) {
            Long ingId = Long.valueOf(tblIngredients.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa Bỏ nguyên liệu này khỏi công thức?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    recipeController.removeIngredientFromRecipe(selectedRecipeId, ingId);
                    loadRecipeIngredients(selectedRecipeId);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nguyên liệu cần xóa.");
        }
    }
}
