package com.example.salesmis.view;

import com.example.salesmis.controller.CustomerController;
import com.example.salesmis.controller.OrderController;
import com.example.salesmis.model.dto.OrderLineInput;
import com.example.salesmis.model.entity.Customer;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.model.entity.Recipe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderProductPanel extends JPanel {
    private final OrderController orderController;
    private final CustomerController customerController;

    private JTable tblProducts;
    private DefaultTableModel productTableModel;

    private JTable tblCart;
    private DefaultTableModel cartTableModel;
    private JLabel lblTotalAmount;

    public OrderProductPanel(OrderController orderController, CustomerController customerController) {
        this.orderController = orderController;
        this.customerController = customerController;
        initComponents();
        loadProducts();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // West: Giỏ hàng
        JPanel pnlCart = new JPanel(new BorderLayout(5, 5));
        pnlCart.setPreferredSize(new Dimension(500, 0));
        pnlCart.setBorder(BorderFactory.createTitledBorder("Giỏ Hàng"));

        cartTableModel = new DefaultTableModel(
                new Object[]{"Product ID", "Recipe ID", "Tên SP", "Size", "SL", "Giá", "Thành tiền"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblCart = new JTable(cartTableModel);
        tblCart.getColumnModel().getColumn(0).setMinWidth(0);
        tblCart.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCart.getColumnModel().getColumn(1).setMinWidth(0);
        tblCart.getColumnModel().getColumn(1).setMaxWidth(0);

        pnlCart.add(new JScrollPane(tblCart), BorderLayout.CENTER);

        // Nút xoá món khỏi giỏ
        JButton btnRemoveCart = new JButton("Xoá món chọn");
        btnRemoveCart.addActionListener(e -> removeSelectedCartItem());

        JPanel pnlCartSouth = new JPanel(new BorderLayout(5, 5));
        lblTotalAmount = new JLabel("Tổng cộng: 0 VND");
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalAmount.setForeground(Color.RED);

        JButton btnCheckout = new JButton("Đặt Hàng (NEW)");
        btnCheckout.setFont(new Font("Arial", Font.BOLD, 14));
        btnCheckout.setPreferredSize(new Dimension(150, 40));
        btnCheckout.addActionListener(e -> checkoutCart());

        JPanel pnlTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlTotal.add(lblTotalAmount);

        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlActions.add(btnRemoveCart);
        pnlActions.add(btnCheckout);

        pnlCartSouth.add(pnlTotal, BorderLayout.NORTH);
        pnlCartSouth.add(pnlActions, BorderLayout.SOUTH);

        pnlCart.add(pnlCartSouth, BorderLayout.SOUTH);

        // Center: Danh sách sản phẩm
        JPanel pnlProducts = new JPanel(new BorderLayout(5, 5));
        pnlProducts.setBorder(BorderFactory.createTitledBorder("Danh Sách Sản Phẩm (Click đúp để chọn)"));

        productTableModel = new DefaultTableModel(
                new Object[]{"ID", "Danh mục", "Mã SP", "Tên SP", "Giá", "Tồn kho"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProducts = new JTable(productTableModel);
        tblProducts.getColumnModel().getColumn(0).setMinWidth(0);
        tblProducts.getColumnModel().getColumn(0).setMaxWidth(0);

        tblProducts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblProducts.getSelectedRow();
                    if (row != -1) {
                        Long productId = (Long) productTableModel.getValueAt(row, 0);
                        Product product = orderController.getAllProducts().stream()
                                .filter(p -> p.getId().equals(productId)).findFirst().orElse(null);
                        if (product != null) {
                            showAddToCartDialog(product);
                        }
                    }
                }
            }
        });

        pnlProducts.add(new JScrollPane(tblProducts), BorderLayout.CENTER);

        // Split Pane chia 2 nửa
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlCart, pnlProducts);
        splitPane.setResizeWeight(0.4);
        add(splitPane, BorderLayout.CENTER);
    }

    private void loadProducts() {
        productTableModel.setRowCount(0);
        for (Product p : orderController.getAllProducts()) {
            if (Boolean.TRUE.equals(p.getActive())) {
                String catName = p.getCategory() != null ? p.getCategory().getCategoryName() : "N/A";
                productTableModel.addRow(new Object[]{
                        p.getId(),
                        catName,
                        p.getSku(),
                        p.getProductName(),
                        p.getUnitPrice(),
                        p.getStockQty()
                });
            }
        }
    }

    private void showAddToCartDialog(Product product) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm vào giỏ hàng", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        List<Recipe> recipes = orderController.getRecipesByProductId(product.getId());

        JComboBox<Recipe> cboRecipe = new JComboBox<>();
        cboRecipe.addItem(null); // No size by default
        for (Recipe r : recipes) {
            cboRecipe.addItem(r);
        }

        JTextField txtQty = new JTextField("1", 10);

        gbc.gridy = 0; gbc.gridx = 0; dialog.add(new JLabel("Sản phẩm: "), gbc);
        gbc.gridx = 1; dialog.add(new JLabel(product.getProductName()), gbc);

        gbc.gridy = 1; gbc.gridx = 0; dialog.add(new JLabel("Kích cỡ/Biến thể: "), gbc);
        gbc.gridx = 1; dialog.add(cboRecipe, gbc);

        gbc.gridy = 2; gbc.gridx = 0; dialog.add(new JLabel("Số lượng: "), gbc);
        gbc.gridx = 1; dialog.add(txtQty, gbc);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.addActionListener(e -> {
            try {
                int qty = Integer.parseInt(txtQty.getText().trim());
                if (qty <= 0) throw new IllegalArgumentException("Số lượng phải > 0");
                Recipe selectedRecipe = (Recipe) cboRecipe.getSelectedItem();
                Long recipeId = selectedRecipe != null ? selectedRecipe.getId() : null;
                String sizeName = selectedRecipe != null ? selectedRecipe.getVariationName() : "";
                
                BigDecimal unitPrice = product.getUnitPrice();
                BigDecimal lineTotal = unitPrice.multiply(new BigDecimal(qty));

                cartTableModel.addRow(new Object[]{
                        product.getId(),
                        recipeId,
                        product.getProductName(),
                        sizeName,
                        qty,
                        unitPrice,
                        lineTotal
                });
                updateTotal();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = 3; gbc.gridx = 1; dialog.add(btnAdd, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void removeSelectedCartItem() {
        int row = tblCart.getSelectedRow();
        if (row != -1) {
            cartTableModel.removeRow(row);
            updateTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món để xoá khỏi giỏ.");
        }
    }

    private void updateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            BigDecimal lineTotal = (BigDecimal) cartTableModel.getValueAt(i, 6);
            total = total.add(lineTotal);
        }
        lblTotalAmount.setText("Tổng cộng: " + total.toPlainString() + " VND");
    }

    private void checkoutCart() {
        if (cartTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!");
            return;
        }

        try {
            Customer defaultCustomer = customerController.ensureDefaultCustomer();
            
            String orderNo = "ORD" + System.currentTimeMillis();
            
            List<OrderLineInput> lines = new ArrayList<>();
            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                Long productId = (Long) cartTableModel.getValueAt(i, 0);
                Long recipeId = (Long) cartTableModel.getValueAt(i, 1);
                int qty = (int) cartTableModel.getValueAt(i, 4);
                BigDecimal unitPrice = (BigDecimal) cartTableModel.getValueAt(i, 5);
                lines.add(new OrderLineInput(productId, recipeId, qty, unitPrice));
            }

            orderController.createOrder(
                    orderNo,
                    LocalDate.now().toString(),
                    defaultCustomer.getId(),
                    null, // no table by default
                    "NEW",
                    "Khách vãng lai order mới",
                    lines
            );

            JOptionPane.showMessageDialog(this, "Lên đơn hàng thành công!\nMã đơn: " + orderNo);
            
            cartTableModel.setRowCount(0);
            updateTotal();
            loadProducts(); // reload to update stock

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tạo đơn hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
