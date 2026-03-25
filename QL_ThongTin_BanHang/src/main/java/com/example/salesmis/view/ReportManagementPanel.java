package com.example.salesmis.view;

import com.example.salesmis.controller.ReportController;
import com.example.salesmis.model.dto.*;
import com.example.salesmis.model.entity.*;
import com.example.salesmis.model.enumtype.OrderStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReportManagementPanel extends JPanel {
    private final ReportController reportController;

    private JComboBox<String> cboReportType;
    private JTextField txtParam1;
    private JTextField txtParam2;
    private JLabel lblParam1;
    private JLabel lblParam2;
    private JButton btnRunReport;
    
    private JTable tblResults;
    private DefaultTableModel tableModel;

    public ReportManagementPanel(ReportController reportController) {
        this.reportController = reportController;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        String[] reportTypes = {
            "Tất cả đơn hàng kèm khách hàng",
            "Tìm đơn theo mã đơn",
            "Tìm đơn theo tên khách hàng",
            "Tìm đơn theo khoảng ngày",
            "Tìm đơn theo trạng thái",
            "Sản phẩm sắp hết hàng",
            "Sản phẩm bán chạy nhất",
            "Đơn hàng chứa một sản phẩm cụ thể",
            "Doanh thu theo khách hàng",
            "Doanh thu theo tháng",
            "Giá trị đơn trung bình",
            "Giá trị đơn lớn nhất",
            "Giá trị đơn nhỏ nhất",
            "Đếm số đơn theo trạng thái",
            "Khách hàng chưa từng mua hàng"
        };
        
        cboReportType = new JComboBox<>(reportTypes);
        lblParam1 = new JLabel("Tham số 1:");
        txtParam1 = new JTextField(15);
        lblParam2 = new JLabel("Tham số 2:");
        txtParam2 = new JTextField(15);
        btnRunReport = new JButton("Thống kê / Báo cáo");

        form.add(new JLabel("Loại báo cáo:"));
        form.add(cboReportType);
        form.add(lblParam1);
        form.add(txtParam1);
        form.add(lblParam2);
        form.add(txtParam2);
        form.add(btnRunReport);

        topPanel.add(form, BorderLayout.NORTH);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblResults = new JTable(tableModel);
        add(new JScrollPane(tblResults), BorderLayout.CENTER);

        cboReportType.addActionListener(e -> updateParamLabels());
        btnRunReport.addActionListener(e -> runReport());

        updateParamLabels();
    }

    private void updateParamLabels() {
        int index = cboReportType.getSelectedIndex();
        lblParam1.setVisible(false);
        txtParam1.setVisible(false);
        lblParam2.setVisible(false);
        txtParam2.setVisible(false);
        
        switch (index) {
            case 1:
                lblParam1.setText("Mã đơn:");
                lblParam1.setVisible(true);
                txtParam1.setVisible(true);
                break;
            case 2:
                lblParam1.setText("Tên khách hàng:");
                lblParam1.setVisible(true);
                txtParam1.setVisible(true);
                break;
            case 3:
                lblParam1.setText("Từ ngày (yyyy-MM-dd):");
                lblParam2.setText("Đến ngày (yyyy-MM-dd):");
                lblParam1.setVisible(true);
                txtParam1.setVisible(true);
                lblParam2.setVisible(true);
                txtParam2.setVisible(true);
                break;
            case 4:
                lblParam1.setText("Trạng thái (NEW, CONFIRMED, COMPLETED, CANCELLED):");
                lblParam1.setVisible(true);
                txtParam1.setVisible(true);
                break;
            case 5:
                lblParam1.setText("Ngưỡng tồn kho (số nguyên):");
                lblParam1.setVisible(true);
                txtParam1.setVisible(true);
                break;
            case 7:
                lblParam1.setText("ID Sản phẩm (số nguyên):");
                lblParam1.setVisible(true);
                txtParam1.setVisible(true);
                break;
        }
        revalidate();
        repaint();
    }

    private void runReport() {
        int index = cboReportType.getSelectedIndex();
        try {
            switch (index) {
                case 0:
                    displayOrders(reportController.q01_findAllOrdersWithCustomer());
                    break;
                case 1:
                    SalesOrder order = reportController.q02_findOrderByOrderNo(txtParam1.getText().trim());
                    displayOrders(order != null ? List.of(order) : List.of());
                    break;
                case 2:
                    displayOrders(reportController.q03_findOrdersByCustomerKeyword(txtParam1.getText().trim()));
                    break;
                case 3:
                    LocalDate from = LocalDate.parse(txtParam1.getText().trim());
                    LocalDate to = LocalDate.parse(txtParam2.getText().trim());
                    displayOrders(reportController.q04_findOrdersBetween(from, to));
                    break;
                case 4:
                    OrderStatus status = OrderStatus.valueOf(txtParam1.getText().trim().toUpperCase());
                    displayOrders(reportController.q05_findOrdersByStatus(status));
                    break;
                case 5:
                    int threshold = Integer.parseInt(txtParam1.getText().trim());
                    displayProducts(reportController.q06_findLowStockProducts(threshold));
                    break;
                case 6:
                    displayTopSelling(reportController.q07_findTopSellingProducts());
                    break;
                case 7:
                    Long productId = Long.parseLong(txtParam1.getText().trim());
                    displayOrders(reportController.q12_findOrdersContainingProduct(productId));
                    break;
                case 8:
                    displayRevenueByCustomer(reportController.q08_findRevenueByCustomer());
                    break;
                case 9:
                    displayRevenueByMonth(reportController.q09_findRevenueByMonth());
                    break;
                case 10:
                    displaySingleValue("Giá trị đơn trung bình", reportController.q13_findAverageOrderValue());
                    break;
                case 11:
                    displaySingleValue("Giá trị đơn lớn nhất", reportController.q14_findMaxOrderValue());
                    break;
                case 12:
                    displaySingleValue("Giá trị đơn nhỏ nhất", reportController.q15_findMinOrderValue());
                    break;
                case 13:
                    displayStatusCount(reportController.q10_countOrdersByStatus());
                    break;
                case 14:
                    displayCustomers(reportController.q11_findCustomersWithoutOrders());
                    break;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi chạy báo cáo: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayOrders(List<SalesOrder> orders) {
        tableModel.setColumnIdentifiers(new String[]{"ID", "Order No", "Date", "Customer", "Status", "Total"});
        tableModel.setRowCount(0);
        for (SalesOrder o : orders) {
            if (o != null) {
                tableModel.addRow(new Object[]{
                        o.getId(),
                        o.getOrderNo(),
                        o.getOrderDate(),
                        o.getCustomer() != null ? o.getCustomer().getFullName() : "",
                        o.getStatus(),
                        o.getTotalAmount()
                });
            }
        }
    }

    private void displayProducts(List<Product> products) {
        tableModel.setColumnIdentifiers(new String[]{"ID", "SKU", "Tên sản phẩm", "Tồn kho", "Đơn giá"});
        tableModel.setRowCount(0);
        for (Product p : products) {
            if (p != null) {
                tableModel.addRow(new Object[]{p.getId(), p.getSku(), p.getProductName(), p.getStockQty(), p.getUnitPrice()});
            }
        }
    }

    private void displayTopSelling(List<ProductSalesDTO> items) {
        tableModel.setColumnIdentifiers(new String[]{"SKU", "Tên sản phẩm", "Tổng SL bán", "Tổng doanh thu"});
        tableModel.setRowCount(0);
        for (ProductSalesDTO dto : items) {
            if (dto != null) {
                tableModel.addRow(new Object[]{dto.sku(), dto.productName(), dto.totalQty(), dto.revenue()});
            }
        }
    }

    private void displayRevenueByCustomer(List<CustomerRevenueDTO> items) {
        tableModel.setColumnIdentifiers(new String[]{"Mã KH", "Tên khách hàng", "Tổng doanh thu"});
        tableModel.setRowCount(0);
        for (CustomerRevenueDTO c : items) {
            if (c != null) {
                tableModel.addRow(new Object[]{c.customerCode(), c.customerName(), c.revenue()});
            }
        }
    }

    private void displayRevenueByMonth(List<MonthlyRevenueDTO> items) {
        tableModel.setColumnIdentifiers(new String[]{"Năm", "Tháng", "Doanh thu"});
        tableModel.setRowCount(0);
        for (MonthlyRevenueDTO m : items) {
            if (m != null) {
                tableModel.addRow(new Object[]{m.year(), m.month(), m.revenue()});
            }
        }
    }

    private void displaySingleValue(String title, BigDecimal value) {
        tableModel.setColumnIdentifiers(new String[]{"Chỉ số", "Giá trị"});
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{title, value});
    }

    private void displayStatusCount(List<StatusCountDTO> items) {
        tableModel.setColumnIdentifiers(new String[]{"Trạng thái", "Số lượng đơn"});
        tableModel.setRowCount(0);
        for (StatusCountDTO s : items) {
            if (s != null) {
                tableModel.addRow(new Object[]{s.status(), s.totalOrders()});
            }
        }
    }

    private void displayCustomers(List<Customer> customers) {
        tableModel.setColumnIdentifiers(new String[]{"ID", "Mã KH", "Tên khách hàng", "Email", "Phone"});
        tableModel.setRowCount(0);
        for (Customer c : customers) {
            if (c != null) {
                tableModel.addRow(new Object[]{c.getId(), c.getCustomerCode(), c.getFullName(), c.getEmail(), c.getPhone()});
            }
        }
    }
}
