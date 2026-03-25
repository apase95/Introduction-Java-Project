package com.example.salesmis.controller;

import com.example.salesmis.model.entity.Customer;
import com.example.salesmis.service.CustomerService;
import java.util.List;

public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<Customer> getAllCustomers() { return customerService.getAllCustomers(); }
    
    public Customer getCustomerById(Long id) { return customerService.getCustomerById(id); }
    
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
    
    public void deleteCustomer(Long id) { customerService.deleteCustomer(id); }
    
    public List<Customer> searchCustomers(String keyword) { return customerService.searchCustomers(keyword); }
}
