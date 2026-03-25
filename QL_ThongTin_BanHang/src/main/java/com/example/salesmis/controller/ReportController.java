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

    public ReportController() {
        this.reportService = new ReportServiceImpl();
    }

    public List<SalesOrder> q01_findAllOrdersWithCustomer() { return reportService.q01_findAllOrdersWithCustomer(); }
    public SalesOrder q02_findOrderByOrderNo(String orderNo) { return reportService.q02_findOrderByOrderNo(orderNo); }
    public List<SalesOrder> q03_findOrdersByCustomerKeyword(String keyword) { return reportService.q03_findOrdersByCustomerKeyword(keyword); }
    public List<SalesOrder> q04_findOrdersBetween(LocalDate from, LocalDate to) { return reportService.q04_findOrdersBetween(from, to); }
    public List<SalesOrder> q05_findOrdersByStatus(OrderStatus status) { return reportService.q05_findOrdersByStatus(status); }
    public List<Product> q06_findLowStockProducts(int threshold) { return reportService.q06_findLowStockProducts(threshold); }
    public List<ProductSalesDTO> q07_findTopSellingProducts() { return reportService.q07_findTopSellingProducts(); }
    public List<CustomerRevenueDTO> q08_findRevenueByCustomer() { return reportService.q08_findRevenueByCustomer(); }
    public List<MonthlyRevenueDTO> q09_findRevenueByMonth() { return reportService.q09_findRevenueByMonth(); }
    public List<StatusCountDTO> q10_countOrdersByStatus() { return reportService.q10_countOrdersByStatus(); }
    public List<Customer> q11_findCustomersWithoutOrders() { return reportService.q11_findCustomersWithoutOrders(); }
    public List<SalesOrder> q12_findOrdersContainingProduct(Long productId) { return reportService.q12_findOrdersContainingProduct(productId); }
    public BigDecimal q13_findAverageOrderValue() { return reportService.q13_findAverageOrderValue(); }
    public BigDecimal q14_findMaxOrderValue() { return reportService.q14_findMaxOrderValue(); }
    public BigDecimal q15_findMinOrderValue() { return reportService.q15_findMinOrderValue(); }
}
