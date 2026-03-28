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
        JPanel pnlRight = new JPanel(new BorderLayout(5, 5));
        pnlRight.setBorder(BorderFactory.createTitledBorder("Danh Sách Sản Phẩm (Click đúp vào sản phẩm)"));

        pnlProductGrid = new JPanel(new GridLayout(0, 2, 10, 10)); // 2 columns exactly
        pnlProductGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(pnlProductGrid);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        pnlRight.add(scrollPane, BorderLayout.CENTER);

        // Split Pane chia 2 nửa
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlCart, pnlRight);
        splitPane.setResizeWeight(0.4);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel pnlProductGrid; // Added member for the grid

    private void loadProducts() {
        pnlProductGrid.removeAll();
        for (Product p : orderController.getAllProducts()) {
            if (Boolean.TRUE.equals(p.getActive())) {
                pnlProductGrid.add(createProductCard(p));
            }
        }
        pnlProductGrid.revalidate();
        pnlProductGrid.repaint();
    }

    private JPanel createProductCard(Product p) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        card.setBackground(Color.WHITE);

        // Top: Image
        String imgPath = p.getImagePath();
        if (imgPath == null || imgPath.trim().isEmpty()) {
            imgPath = "default.png";
        }
        
        JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        try {
            java.net.URL imgUrl = getClass().getResource("/images/" + imgPath);
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                // Resize if too large
                Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));
            } else {
                lblImage.setText("[Hình ảnh]"); // Fallback text
            }
        } catch (Exception ex) {
            lblImage.setText("[Lỗi Hình ảnh]");
        }
        card.add(lblImage, BorderLayout.CENTER);

        // Bottom: 2 lines (Name, Price)
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel lblName = new JLabel(p.getProductName(), SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblPrice = new JLabel(p.getUnitPrice().toPlainString() + " VND", SwingConstants.CENTER);
        lblPrice.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrice.setForeground(new Color(0, 102, 51));

        infoPanel.add(lblName);
        infoPanel.add(lblPrice);
        card.add(infoPanel, BorderLayout.SOUTH);

        // Click Event (Double click or single click? Single is better for cards, but prompt says "double click" in title, we can support both here, we will just use 2 clicks to match previous style, or single click to be pos friendly)
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // For POS, 1 click is much faster. Let's make it 1 click.
                showAddToCartDialog(p);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(240, 240, 240));
                infoPanel.setBackground(new Color(240, 240, 240));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                infoPanel.setBackground(Color.WHITE);
            }
        });

        return card;
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
