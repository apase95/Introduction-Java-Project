package com.example.salesmis.dao;

import com.example.salesmis.model.entity.SalesOrder;

import java.util.List;
import java.util.Optional;

public interface SalesOrderDAO {
    /** Lấy danh sách tất cả đơn hàng. */
    List<SalesOrder> findAll();
    /** Tìm đơn hàng theo ID. */
    Optional<SalesOrder> findById(Long id);
    /** Tìm đơn hàng theo mã đơn. */
    Optional<SalesOrder> findByOrderNo(String orderNo);
    /** Tìm kiếm đơn hàng theo từ khóa (mã đơn hoặc tên khách). */
    List<SalesOrder> searchByKeyword(String keyword);
    /** Tạo mới đơn hàng vào CSDL. */
    SalesOrder save(SalesOrder order);
    /** Cập nhật đơn hàng đã tồn tại. */
    SalesOrder update(SalesOrder order);
    /** Xóa đơn hàng theo ID. */
    void deleteById(Long id);
}
