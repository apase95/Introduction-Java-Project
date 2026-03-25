package com.example.salesmis.service.impl;

import com.example.salesmis.dao.ReportDAO;
import com.example.salesmis.dao.impl.ReportDAOImpl;
import com.example.salesmis.model.dto.*;
import com.example.salesmis.model.entity.*;
import com.example.salesmis.model.enumtype.OrderStatus;
import com.example.salesmis.service.ReportService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    private final ReportDAO reportDAO;

    public ReportServiceImpl() {
        this.reportDAO = new ReportDAOImpl();
    }

    @Override public List<SalesOrder> q01_findAllOrdersWithCustomer() { return reportDAO.q01_findAllOrdersWithCustomer(); }
    @Override public SalesOrder q02_findOrderByOrderNo(String orderNo) { return reportDAO.q02_findOrderByOrderNo(orderNo); }
    @Override public List<SalesOrder> q03_findOrdersByCustomerKeyword(String keyword) { return reportDAO.q03_findOrdersByCustomerKeyword(keyword); }
    @Override public List<SalesOrder> q04_findOrdersBetween(LocalDate from, LocalDate to) { return reportDAO.q04_findOrdersBetween(from, to); }
    @Override public List<SalesOrder> q05_findOrdersByStatus(OrderStatus status) { return reportDAO.q05_findOrdersByStatus(status); }
    @Override public List<Product> q06_findLowStockProducts(int threshold) { return reportDAO.q06_findLowStockProducts(threshold); }
    @Override public List<ProductSalesDTO> q07_findTopSellingProducts() { return reportDAO.q07_findTopSellingProducts(); }
    @Override public List<CustomerRevenueDTO> q08_findRevenueByCustomer() { return reportDAO.q08_findRevenueByCustomer(); }
    @Override public List<MonthlyRevenueDTO> q09_findRevenueByMonth() { return reportDAO.q09_findRevenueByMonth(); }
    @Override public List<StatusCountDTO> q10_countOrdersByStatus() { return reportDAO.q10_countOrdersByStatus(); }
    @Override public List<Customer> q11_findCustomersWithoutOrders() { return reportDAO.q11_findCustomersWithoutOrders(); }
    @Override public List<SalesOrder> q12_findOrdersContainingProduct(Long productId) { return reportDAO.q12_findOrdersContainingProduct(productId); }
    @Override public BigDecimal q13_findAverageOrderValue() { return reportDAO.q13_findAverageOrderValue(); }
    @Override public BigDecimal q14_findMaxOrderValue() { return reportDAO.q14_findMaxOrderValue(); }
    @Override public BigDecimal q15_findMinOrderValue() { return reportDAO.q15_findMinOrderValue(); }
}
