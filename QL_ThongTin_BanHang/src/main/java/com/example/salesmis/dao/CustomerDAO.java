package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    /** Lấy danh sách tất cả khách hàng. */
    List<Customer> findAll();
    /** Tìm khách hàng theo ID. */
    Optional<Customer> findById(Long id);
    /** Lưu hoặc cập nhật khách hàng. */
    Customer save(Customer customer);
    /** Xóa khách hàng theo ID. */
    void delete(Long id);
    /** Tìm kiếm khách hàng theo từ khóa (mã hoặc tên). */
    List<Customer> searchByKeyword(String keyword);
}
