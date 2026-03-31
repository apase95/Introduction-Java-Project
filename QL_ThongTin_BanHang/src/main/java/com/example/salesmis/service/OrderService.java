package com.example.salesmis.service;

import com.example.salesmis.model.dto.OrderLineInput;
import com.example.salesmis.model.entity.SalesOrder;
import com.example.salesmis.model.enumtype.OrderStatus;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    /** Lấy danh sách tất cả đơn hàng. */
    List<SalesOrder> getAllOrders();
    /** Tìm kiếm đơn hàng theo từ khóa. */
    List<SalesOrder> searchOrders(String keyword);
    /** Lấy đơn hàng theo ID; ném ngoại lệ nếu không tìm thấy. */
    SalesOrder getOrderById(Long id);

    /** Tạo mới đơn hàng: xác thực dữ liệu, trừ kho nếu COMPLETED, lưu vào CSDL. */
    SalesOrder createOrder(String orderNo,
                           LocalDate orderDate,
                           Long customerId,
                           Long tableId,
                           OrderStatus status,
                           String note,
                           List<OrderLineInput> lines);

    /** Cập nhật đơn hàng: điều chỉnh tồn kho nếu cần, ghi lại chi tiết. */
    SalesOrder updateOrder(Long id,
                           String orderNo,
                           LocalDate orderDate,
                           Long customerId,
                           Long tableId,
                           OrderStatus status,
                           String note,
                           List<OrderLineInput> lines);

    /** Xóa đơn hàng và hoàn kho nếu trạng thái là COMPLETED. */
    void deleteOrder(Long id);
}
