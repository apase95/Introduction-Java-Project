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

    // Hình ảnh sản phẩm
    private JLabel lblImagePreview;
    private JComboBox<String> cboImageFile;

    // Danh sách file ảnh có sẵn trong resources/images
    private static final String[] AVAILABLE_IMAGES = {
        "default.png",
        "bac-xiu.png",
        "cf_sua.png",
        "matcha-da-xay.png",
        "sinh-to-bo.png",
        "sinh-to-dau.png",
        "socola-da-xay.png",
        "tra-dao-cam-xa.png",
        "tra-sua-Oolong.png",
        "tra-sua-tran-chau.png",
        "tra-vai-hat-chia.png"
    };

    public RecipeManagementPanel(RecipeController recipeController) {
        this.recipeController = recipeController;
        initComponents();
        loadProducts();
        loadIngredients();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Chia làm 2: Left (Sản phẩm + Ảnh) và Right (Công thức + Nguyên liệu)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buildProductPanel(), buildRightPanel());
        splitPane.setDividerLocation(320);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel buildProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("1. Chọn Sản phẩm & Hình ảnh"));

        // Thanh tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout());
        txtSearchProduct = new JTextField();
        txtSearchProduct.addActionListener(e -> filterProducts());
        searchPanel.add(new JLabel("Tìm kiếm (Enter): "), BorderLayout.WEST);
        searchPanel.add(txtSearchProduct, BorderLayout.CENTER);

        // Danh sách sản phẩm
        productListModel = new DefaultListModel<>();
        listProducts = new JList<>(productListModel);
        listProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listProducts.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Product p = listProducts.getSelectedValue();
                if (p != null) {
                    loadRecipes(p.getId());
                    updateImagePreview(p);
                } else {
                    recipeTableModel.setRowCount(0);
                    clearRecipeForm();
                    ingredientTableModel.setRowCount(0);
                    clearImagePreview();
                }
            }
        });

        // Panel ảnh sản phẩm
        JPanel imagePanel = buildImagePanel();

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(listProducts), BorderLayout.CENTER);
        panel.add(imagePanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildImagePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Hình ảnh Sản phẩm"));
        panel.setPreferredSize(new Dimension(0, 230));

        // Preview ảnh
        lblImagePreview = new JLabel("(Chọn sản phẩm để xem ảnh)", SwingConstants.CENTER);
        lblImagePreview.setPreferredSize(new Dimension(150, 150));
        lblImagePreview.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        lblImagePreview.setBackground(new Color(248, 248, 248));
        lblImagePreview.setOpaque(true);
        lblImagePreview.setFont(new Font("Arial", Font.ITALIC, 11));
        lblImagePreview.setForeground(Color.GRAY);
        panel.add(lblImagePreview, BorderLayout.CENTER);

        // Phần chọn và cập nhật ảnh
        JPanel updatePanel = new JPanel(new BorderLayout(4, 4));
        updatePanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        cboImageFile = new JComboBox<>(AVAILABLE_IMAGES);
        cboImageFile.setFont(new Font("Arial", Font.PLAIN, 11));

        // JButton btnUpdateImage = new JButton("Cập nhật Ảnh");
        // btnUpdateImage.setFont(new Font("Arial", Font.BOLD, 12));
        // btnUpdateImage.setBackground(new Color(0, 120, 215));
        // btnUpdateImage.setForeground(Color.WHITE);
        // btnUpdateImage.setOpaque(true);
        // btnUpdateImage.addActionListener(e -> saveProductImage());

        // updatePanel.add(new JLabel("Chọn ảnh:"), BorderLayout.WEST);
        // updatePanel.add(cboImageFile, BorderLayout.CENTER);
        // updatePanel.add(btnUpdateImage, BorderLayout.EAST);

        panel.add(updatePanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateImagePreview(Product p) {
        String imgPath = p.getImagePath();
        if (imgPath == null || imgPath.trim().isEmpty()) {
            imgPath = "default.png";
        }

        // Chọn đúng item trong combobox
        for (int i = 0; i < cboImageFile.getItemCount(); i++) {
            if (cboImageFile.getItemAt(i).equals(imgPath)) {
                cboImageFile.setSelectedIndex(i);
                break;
            }
        }

        // Load và scale ảnh
        try {
            java.net.URL imgUrl = getClass().getResource("/images/" + imgPath);
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblImagePreview.setIcon(new ImageIcon(img));
                lblImagePreview.setText(null);
            } else {
                lblImagePreview.setIcon(null);
                lblImagePreview.setText("[Không tìm thấy: " + imgPath + "]");
            }
        } catch (Exception ex) {
            lblImagePreview.setIcon(null);
            lblImagePreview.setText("[Lỗi tải ảnh]");
        }
    }

    private void clearImagePreview() {
        lblImagePreview.setIcon(null);
        lblImagePreview.setText("(Chọn sản phẩm để xem ảnh)");
        if (cboImageFile.getItemCount() > 0) {
            cboImageFile.setSelectedIndex(0);
        }
    }

    private void saveProductImage() {
        Product p = listProducts.getSelectedValue();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trước.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String selectedImage = (String) cboImageFile.getSelectedItem();
        if (selectedImage == null) return;

        try {
            recipeController.updateProductImage(p.getId(), selectedImage);
            // Cập nhật in-memory và refresh preview
            p.setImagePath(selectedImage);
            updateImagePreview(p);
            JOptionPane.showMessageDialog(this, "Đã cập nhật hình ảnh sản phẩm \"" + p.getProductName() + "\" thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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
