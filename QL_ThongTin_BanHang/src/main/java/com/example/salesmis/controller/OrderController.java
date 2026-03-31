package com.example.salesmis.controller;

import com.example.salesmis.model.dto.OrderLineInput;
import com.example.salesmis.model.entity.Customer;
import com.example.salesmis.model.entity.DiningTable;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.model.entity.Recipe;
import com.example.salesmis.model.entity.SalesOrder;
import com.example.salesmis.model.enumtype.OrderStatus;
import com.example.salesmis.service.LookupService;
import com.example.salesmis.service.OrderService;
import java.time.LocalDate;
import java.util.List;

public class OrderController {
    private final OrderService orderService;
    private final LookupService lookupService;

    public OrderController(OrderService orderService, LookupService lookupService) {
        this.orderService = orderService;
        this.lookupService = lookupService;
    }

    /** Lấy danh sách tất cả đơn hàng. */
    public List<SalesOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    /** Tìm kiếm đơn hàng theo từ khóa. */
    public List<SalesOrder> searchOrders(String keyword) {
        return orderService.searchOrders(keyword);
    }

    /** Lấy đơn hàng theo ID. */
    public SalesOrder getOrderById(Long id) {
        return orderService.getOrderById(id);
    }

    /** Lấy danh sách khách hàng để điền vào ComboBox. */
    public List<Customer> getAllCustomers() {
        return lookupService.getAllCustomers();
    }

    /** Lấy danh sách bàn ăn để điền vào ComboBox. */
    public List<DiningTable> getAllDiningTables() {
        return lookupService.getAllDiningTables();
    }

    /** Lấy danh sách sản phẩm để điền vào ComboBox. */
    public List<Product> getAllProducts() {
        return lookupService.getAllProducts();
    }

    /** Lấy danh sách công thức (size/biến thể) của một sản phẩm. */
    public List<Recipe> getRecipesByProductId(Long productId) {
        return lookupService.getRecipesByProductId(productId);
    }

    /** Tạo mới đơn hàng từ các tham số của UI (chuỗi ngày và trạng thái được parse tự động). */
    public SalesOrder createOrder(String orderNo, String orderDateText, Long customerId, Long tableId,
                                  String statusText, String note, List<OrderLineInput> lines) {
        return orderService.createOrder(
                orderNo,
                LocalDate.parse(orderDateText),
                customerId,
                tableId,
                OrderStatus.valueOf(statusText),
                note,
                lines
        );
    }

    /** Cập nhật đơn hàng theo ID từ các tham số của UI. */
    public SalesOrder updateOrder(Long id, String orderNo, String orderDateText, Long customerId, Long tableId,
                                  String statusText, String note, List<OrderLineInput> lines) {
        return orderService.updateOrder(
                id,
                orderNo,
                LocalDate.parse(orderDateText),
                customerId,
                tableId,
                OrderStatus.valueOf(statusText),
                note,
                lines
        );
    }

    /** Xóa đơn hàng theo ID. */
    public void deleteOrder(Long id) {
        orderService.deleteOrder(id);
    }
}
