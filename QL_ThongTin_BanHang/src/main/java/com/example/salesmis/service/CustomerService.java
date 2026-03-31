package com.example.salesmis.service;

import com.example.salesmis.model.entity.Customer;
import java.util.List;

public interface CustomerService {
    /** Lấy danh sách tất cả khách hàng. */
    List<Customer> getAllCustomers();
    /** Tìm khách hàng theo ID. */
    Customer getCustomerById(Long id);
    /** Lưu hoặc cập nhật khách hàng. */
    Customer saveCustomer(Customer customer);
    /** Xóa khách hàng theo ID. */
    void deleteCustomer(Long id);
    /** Tìm kiếm khách hàng theo từ khóa. */
    List<Customer> searchCustomers(String keyword);
    /** Tạo khách vãng lai mặc định nếu chưa tồn tại. */
    Customer ensureDefaultCustomer();
}
