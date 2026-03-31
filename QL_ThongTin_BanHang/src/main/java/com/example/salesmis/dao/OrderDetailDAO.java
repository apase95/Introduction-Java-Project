package com.example.salesmis.dao;

import com.example.salesmis.model.entity.OrderDetail;
import java.util.List;

public interface OrderDetailDAO {
    /** Lấy danh sách chi tiết của một đơn hàng theo ID đơn hàng. */
    List<OrderDetail> findByOrderId(Long orderId);
}
