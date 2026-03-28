package com.example.salesmis.service;

import com.example.salesmis.model.entity.Customer;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer saveCustomer(Customer customer);
    void deleteCustomer(Long id);
    List<Customer> searchCustomers(String keyword);
    Customer ensureDefaultCustomer();
}
