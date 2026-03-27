package com.example.salesmis.view;

import com.example.salesmis.controller.OrderController;
import com.example.salesmis.model.dto.OrderLineInput;
import com.example.salesmis.model.entity.Customer;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.model.entity.SalesOrder;
import com.example.salesmis.model.enumtype.OrderStatus;
import com.example.salesmis.util.PdfExportUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderManagementPanel extends JPanel {
    private final OrderController orderController;

    private JTextField txtOrderId;
    private JTextField txtOrderNo;
    private JTextField txtOrderDate;
    private JTextField txtSearch;
    private JTextField txtNote;

    private JComboBox<Customer> cboCustomer;
    private JComboBox<String> cboStatus;

    private JComboBox<Product> cboProduct;
    private JTextField txtQty;
    private JTextField txtUnitPrice;

    private JTable tblOrders;
    private JTable tblDetails;
    private DefaultTableModel orderTableModel;
    private DefaultTableModel detailTableModel;

    private Long selectedOrderId;

    public OrderManagementPanel(OrderController orderController) {
        this.orderController = orderController;
        initComponents();
        loadCustomers();
        loadProducts();
        loadOrders();
    }
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.add(buildHeaderForm(), BorderLayout.NORTH);
        top.add(buildDetailEntryPanel(), BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buildOrderTablePanel(), buildDetailTablePanel());
        splitPane.setResizeWeight(0.55);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel buildHeaderForm() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridLayout(3, 4, 8, 8));

        txtOrderId = new JTextField();
        txtOrderId.setEditable(false);

        txtOrderNo = new JTextField();
        txtOrderDate = new JTextField("2026-03-24");
        txtSearch = new JTextField();
        txtNote = new JTextField();

        cboCustomer = new JComboBox<>();
        cboStatus = new JComboBox<>(new String[]{
                OrderStatus.NEW.name(),
                OrderStatus.CONFIRMED.name(),
                OrderStatus.COMPLETED.name(),
                OrderStatus.CANCELLED.name()
        });

        form.add(new JLabel("Order ID"));
        form.add(txtOrderId);
        form.add(new JLabel("Order No"));
        form.add(txtOrderNo);

        form.add(new JLabel("Order Date (yyyy-MM-dd)"));
        form.add(txtOrderDate);
        form.add(new JLabel("Customer"));
        form.add(cboCustomer);

        form.add(new JLabel("Status"));
        form.add(cboStatus);
        form.add(new JLabel("Note"));
        form.add(txtNote);

        panel.add(form, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new BorderLayout());

        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNew = new JButton("Làm mới");
        JButton btnSave = new JButton("Lưu");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnExportPdf = new JButton("Xuất PDF");
        
        leftButtons.add(btnNew);
        leftButtons.add(btnSave);
        leftButtons.add(btnUpdate);
        leftButtons.add(btnDelete);
        leftButtons.add(btnExportPdf);

        JPanel rightSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSearch = new JButton("Tìm kiếm");
        rightSearch.add(new JLabel("Từ khóa:"));
        txtSearch.setColumns(15);
        rightSearch.add(txtSearch);
        rightSearch.add(btnSearch);

        buttonsPanel.add(leftButtons, BorderLayout.WEST);
        buttonsPanel.add(rightSearch, BorderLayout.EAST);

        btnNew.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> saveOrder());
        btnUpdate.addActionListener(e -> updateOrder());
        btnDelete.addActionListener(e -> deleteOrder());
        btnExportPdf.addActionListener(e -> exportPdf());
        btnSearch.addActionListener(e -> searchOrders());

        panel.add(buttonsPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildDetailEntryPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cboProduct = new JComboBox<>();
        txtQty = new JTextField(6);
        txtUnitPrice = new JTextField(10);
        txtUnitPrice.setEditable(false);

        JButton btnAddLine = new JButton("Thêm sản phẩm");
        JButton btnRemoveLine = new JButton("Xóa sản phẩm");

        cboProduct.addActionListener(e -> {
            Product p = (Product) cboProduct.getSelectedItem();
            if (p != null) {
                txtUnitPrice.setText(p.getUnitPrice().toPlainString());
            }
        });
        btnAddLine.addActionListener(e -> addLine());
        btnRemoveLine.addActionListener(e -> removeSelectedLine());

        panel.add(new JLabel("Product"));
        panel.add(cboProduct);
        panel.add(new JLabel("Qty"));
        panel.add(txtQty);
        panel.add(new JLabel("Unit Price"));
        panel.add(txtUnitPrice);
        panel.add(btnAddLine);
        panel.add(btnRemoveLine);

        return panel;
    }

    private JScrollPane buildOrderTablePanel() {
        orderTableModel = new DefaultTableModel(
                new String[]{"ID", "Order No", "Date", "Customer", "Status", "Total", "Note"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tblOrders = new JTable(orderTableModel);
        tblOrders.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblOrders.getSelectedRow() != -1) {
                loadOrderToForm();
            }
        });

        return new JScrollPane(tblOrders);
    }

    private JScrollPane buildDetailTablePanel() {
        detailTableModel = new DefaultTableModel(
                new String[]{"Product ID", "SKU", "Product", "Qty", "Unit Price", "Line Total"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tblDetails = new JTable(detailTableModel);
        return new JScrollPane(tblDetails);
    }

    private void loadCustomers() {
        cboCustomer.removeAllItems();
        for (Customer c : orderController.getAllCustomers()) {
            cboCustomer.addItem(c);
        }
    }

    private void loadProducts() {
        cboProduct.removeAllItems();
        for (Product p : orderController.getAllProducts()) {
            cboProduct.addItem(p);
        }
        if (cboProduct.getItemCount() > 0) {
            Product p = (Product) cboProduct.getItemAt(0);
            txtUnitPrice.setText(p.getUnitPrice().toPlainString());
        }
    }

    private void loadOrders() {
        renderOrders(orderController.getAllOrders());
    }

    private void renderOrders(List<SalesOrder> orders) {
        orderTableModel.setRowCount(0);
        for (SalesOrder o : orders) {
            orderTableModel.addRow(new Object[]{
                    o.getId(),
                    o.getOrderNo(),
                    o.getOrderDate(),
                    o.getCustomer() != null ? o.getCustomer().getFullName() : "",
                    o.getStatus(),
                    o.getTotalAmount(),
                    o.getNote()
            });
        }
    }

    private void addLine() {
        try {
            Product p = (Product) cboProduct.getSelectedItem();
            if (p == null) throw new IllegalArgumentException("Chưa chọn sản phẩm.");

            int qty = Integer.parseInt(txtQty.getText().trim());
            if (qty <= 0) throw new IllegalArgumentException("Số lượng phải > 0.");

            BigDecimal price = new BigDecimal(txtUnitPrice.getText().trim());
            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(qty));

            detailTableModel.addRow(new Object[]{
                    p.getId(),
                    p.getSku(),
                    p.getProductName(),
                    qty,
                    price,
                    lineTotal
            });

            txtQty.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên hợp lệ.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelectedLine() {
        int row = tblDetails.getSelectedRow();
        if (row != -1) detailTableModel.removeRow(row);
    }
    private List<OrderLineInput> buildLines() {
        List<OrderLineInput> lines = new ArrayList<>();
        for (int i = 0; i < detailTableModel.getRowCount(); i++) {
            Long productId = Long.valueOf(detailTableModel.getValueAt(i, 0).toString());
            int qty = Integer.parseInt(detailTableModel.getValueAt(i, 3).toString());
            BigDecimal price = new BigDecimal(detailTableModel.getValueAt(i, 4).toString());
            lines.add(new OrderLineInput(productId, qty, price));
        }
        return lines;
    }

    private void saveOrder() {
        try {
            Customer customer = (Customer) cboCustomer.getSelectedItem();
            if (customer == null) throw new IllegalArgumentException("Chưa chọn khách hàng.");

            SalesOrder saved = orderController.createOrder(
                    txtOrderNo.getText(),
                    txtOrderDate.getText(),
                    customer.getId(),
                    cboStatus.getSelectedItem().toString(),
                    txtNote.getText(),
                    buildLines()
            );

            JOptionPane.showMessageDialog(this, "Lưu đơn hàng thành công: " + saved.getOrderNo());
            clearForm();
            loadOrders();
        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ (yyyy-MM-dd).", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi lưu đơn hàng", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateOrder() {
        try {
            if (selectedOrderId == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn hàng cần cập nhật.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Customer customer = (Customer) cboCustomer.getSelectedItem();
            SalesOrder updated = orderController.updateOrder(
                    selectedOrderId,
                    txtOrderNo.getText(),
                    txtOrderDate.getText(),
                    customer.getId(),
                    cboStatus.getSelectedItem().toString(),
                    txtNote.getText(),
                    buildLines()
            );

            JOptionPane.showMessageDialog(this, "Cập nhật thành công: " + updated.getOrderNo());
            clearForm();
            loadOrders();
        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ (yyyy-MM-dd).", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi cập nhật", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteOrder() {
        try {
            if (selectedOrderId == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn hàng cần xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Xóa đơn hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                orderController.deleteOrder(selectedOrderId);
                clearForm();
                loadOrders();
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi xóa", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchOrders() {
        try {
            renderOrders(orderController.searchOrders(txtSearch.getText()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi tìm kiếm", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportPdf() {
        if (selectedOrderId == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn hàng cần xuất PDF.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn vị trí lưu file PDF");
        fileChooser.setSelectedFile(new File("HoaDon_" + txtOrderNo.getText() + ".pdf"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String destPath = fileToSave.getAbsolutePath();
            if (!destPath.toLowerCase().endsWith(".pdf")) {
                destPath += ".pdf";
            }

            try {
                SalesOrder order = orderController.getOrderById(selectedOrderId);
                PdfExportUtil.exportInvoice(order, destPath);
                JOptionPane.showMessageDialog(this, "Xuất PDF thành công!\n" + destPath, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void loadOrderToForm() {
        int row = tblOrders.getSelectedRow();
        if (row == -1) return;

        selectedOrderId = Long.valueOf(tblOrders.getValueAt(row, 0).toString());
        SalesOrder order = orderController.getOrderById(selectedOrderId);

        txtOrderId.setText(order.getId().toString());
        txtOrderNo.setText(order.getOrderNo());
        txtOrderDate.setText(order.getOrderDate().toString());
        txtNote.setText(order.getNote() == null ? "" : order.getNote());
        cboStatus.setSelectedItem(order.getStatus().name());

        for (int i = 0; i < cboCustomer.getItemCount(); i++) {
            Customer c = cboCustomer.getItemAt(i);
            if (c.getId().equals(order.getCustomer().getId())) {
                cboCustomer.setSelectedIndex(i);
                break;
            }
        }

        detailTableModel.setRowCount(0);
        order.getOrderDetails().forEach(d -> detailTableModel.addRow(new Object[]{
                d.getProduct().getId(),
                d.getProduct().getSku(),
                d.getProduct().getProductName(),
                d.getQuantity(),
                d.getUnitPrice(),
                d.getLineTotal()
        }));
    }

    private void clearForm() {
        selectedOrderId = null;
        txtOrderId.setText("");
        txtOrderNo.setText("");
        txtOrderDate.setText("2026-03-24");
        txtNote.setText("");
        txtSearch.setText("");
        detailTableModel.setRowCount(0);
        tblOrders.clearSelection();
    }
}
