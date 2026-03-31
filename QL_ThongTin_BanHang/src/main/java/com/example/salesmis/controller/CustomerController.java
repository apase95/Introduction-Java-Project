package com.example.salesmis.controller;

import com.example.salesmis.model.entity.Customer;
import com.example.salesmis.service.CustomerService;
import java.util.List;

public class CustomerController {
    private final CustomerService customerService;

    /** Constructor nhận CustomerService (dependency injection). */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /** Lấy danh sách tất cả khách hàng. */
    public List<Customer> getAllCustomers() { return customerService.getAllCustomers(); }
    
    /** Tìm khách hàng theo ID. */
    public Customer getCustomerById(Long id) { return customerService.getCustomerById(id); }
    
    /** Tạo mới khách hàng và lưu vào CSDL. */
    public Customer createCustomer(String code, String name, String phone, String email, String address, boolean active) {
        Customer c = new Customer();
        c.setCustomerCode(code);
        c.setFullName(name);
        c.setPhone(phone);
        c.setEmail(email);
        c.setAddress(address);
        c.setActive(active);
        return customerService.saveCustomer(c);
    }
    
    /** Cập nhật thông tin khách hàng theo ID. */
    public Customer updateCustomer(Long id, String code, String name, String phone, String email, String address, boolean active) {
        Customer c = customerService.getCustomerById(id);
        if (c == null) throw new IllegalArgumentException("Không tìm thấy khách hàng");
        c.setCustomerCode(code);
        c.setFullName(name);
        c.setPhone(phone);
        c.setEmail(email);
        c.setAddress(address);
        c.setActive(active);
        return customerService.saveCustomer(c);
    }
    
    /** Xóa khách hàng theo ID. */
    public void deleteCustomer(Long id) { customerService.deleteCustomer(id); }
    
    /** Tìm kiếm khách hàng theo từ khóa. */
    public List<Customer> searchCustomers(String keyword) { return customerService.searchCustomers(keyword); }

    /** Đảm bảo khách vãng lai mặc định tồn tại trong hệ thống. */
    public Customer ensureDefaultCustomer() {
        return customerService.ensureDefaultCustomer();
    }
}
