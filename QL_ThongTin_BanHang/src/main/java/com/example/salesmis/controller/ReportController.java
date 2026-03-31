package com.example.salesmis.controller;

import com.example.salesmis.model.dto.*;
import com.example.salesmis.model.entity.*;
import com.example.salesmis.model.enumtype.OrderStatus;
import com.example.salesmis.service.ReportService;
import com.example.salesmis.service.impl.ReportServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReportController {
    private final ReportService reportService;

    /** Constructor khởi tạo ReportService. */
    public ReportController() {
        this.reportService = new ReportServiceImpl();
    }

    /** Q1: Lấy toàn bộ đơn hàng kèm thông tin khách. */
    public List<SalesOrder> q01_findAllOrdersWithCustomer() { return reportService.q01_findAllOrdersWithCustomer(); }
    /** Q2: Tìm đơn hàng theo mã. */
    public SalesOrder q02_findOrderByOrderNo(String orderNo) { return reportService.q02_findOrderByOrderNo(orderNo); }
    /** Q3: Tìm đơn hàng theo tên khách. */
    public List<SalesOrder> q03_findOrdersByCustomerKeyword(String keyword) { return reportService.q03_findOrdersByCustomerKeyword(keyword); }
    /** Q4: Tìm đơn hàng trong khoảng ngày. */
    public List<SalesOrder> q04_findOrdersBetween(LocalDate from, LocalDate to) { return reportService.q04_findOrdersBetween(from, to); }
    /** Q5: Tìm đơn hàng theo trạng thái. */
    public List<SalesOrder> q05_findOrdersByStatus(OrderStatus status) { return reportService.q05_findOrdersByStatus(status); }
    /** Q6: Lấy sản phẩm tồn kho thấp. */
    public List<Product> q06_findLowStockProducts(int threshold) { return reportService.q06_findLowStockProducts(threshold); }
    /** Q7: Thống kê sản phẩm bán chạy. */
    public List<ProductSalesDTO> q07_findTopSellingProducts() { return reportService.q07_findTopSellingProducts(); }
    /** Q8: Doanh thu theo khách hàng. */
    public List<CustomerRevenueDTO> q08_findRevenueByCustomer() { return reportService.q08_findRevenueByCustomer(); }
    /** Q9: Doanh thu theo tháng. */
    public List<MonthlyRevenueDTO> q09_findRevenueByMonth() { return reportService.q09_findRevenueByMonth(); }
    /** Q10: Đếm đơn hàng theo trạng thái. */
    public List<StatusCountDTO> q10_countOrdersByStatus() { return reportService.q10_countOrdersByStatus(); }
    /** Q11: Tìm khách chưa có đơn hàng. */
    public List<Customer> q11_findCustomersWithoutOrders() { return reportService.q11_findCustomersWithoutOrders(); }
    /** Q12: Tìm đơn hàng chứa sản phẩm chỉ định. */
    public List<SalesOrder> q12_findOrdersContainingProduct(Long productId) { return reportService.q12_findOrdersContainingProduct(productId); }
    /** Q13: Giá trị đơn hàng trung bình. */
    public BigDecimal q13_findAverageOrderValue() { return reportService.q13_findAverageOrderValue(); }
    /** Q14: Giá trị đơn hàng lớn nhất. */
    public BigDecimal q14_findMaxOrderValue() { return reportService.q14_findMaxOrderValue(); }
    /** Q15: Giá trị đơn hàng nhỏ nhất. */
    public BigDecimal q15_findMinOrderValue() { return reportService.q15_findMinOrderValue(); }
}
