package com.example.salesmis.service;

import com.example.salesmis.model.dto.*;
import com.example.salesmis.model.entity.*;
import com.example.salesmis.model.enumtype.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    /** Q1: Lấy toàn bộ đơn hàng kèm khách. */
    List<SalesOrder> q01_findAllOrdersWithCustomer();
    /** Q2: Tìm đơn hàng theo mã đơn. */
    SalesOrder q02_findOrderByOrderNo(String orderNo);
    /** Q3: Tìm đơn hàng theo từ khóa tên khách. */
    List<SalesOrder> q03_findOrdersByCustomerKeyword(String keyword);
    /** Q4: Tìm đơn hàng trong khoảng ngày. */
    List<SalesOrder> q04_findOrdersBetween(LocalDate from, LocalDate to);
    /** Q5: Tìm đơn hàng theo trạng thái. */
    List<SalesOrder> q05_findOrdersByStatus(OrderStatus status);
    /** Q6: Lấy sản phẩm tồn kho thấp. */
    List<Product> q06_findLowStockProducts(int threshold);
    /** Q7: Thống kê sản phẩm bán chạy. */
    List<ProductSalesDTO> q07_findTopSellingProducts();
    /** Q8: Doanh thu theo khách hàng. */
    List<CustomerRevenueDTO> q08_findRevenueByCustomer();
    /** Q9: Doanh thu theo tháng. */
    List<MonthlyRevenueDTO> q09_findRevenueByMonth();
    /** Q10: Đếm đơn theo trạng thái. */
    List<StatusCountDTO> q10_countOrdersByStatus();
    /** Q11: Tìm khách chưa có đơn hàng. */
    List<Customer> q11_findCustomersWithoutOrders();
    /** Q12: Tìm đơn hàng có chứa sản phẩm chỉ định. */
    List<SalesOrder> q12_findOrdersContainingProduct(Long productId);
    /** Q13: Giá trị đơn trung bình. */
    BigDecimal q13_findAverageOrderValue();
    /** Q14: Giá trị đơn lớn nhất. */
    BigDecimal q14_findMaxOrderValue();
    /** Q15: Giá trị đơn nhỏ nhất. */
    BigDecimal q15_findMinOrderValue();
}
