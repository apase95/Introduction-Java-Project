package com.example.salesmis.service.impl;

import com.example.salesmis.dao.CustomerDAO;
import com.example.salesmis.model.entity.Customer;
import com.example.salesmis.service.CustomerService;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override public List<Customer> getAllCustomers() { return customerDAO.findAll(); }
    @Override public Customer getCustomerById(Long id) { return customerDAO.findById(id).orElse(null); }
    @Override public Customer saveCustomer(Customer customer) { return customerDAO.save(customer); }
    @Override public void deleteCustomer(Long id) { customerDAO.delete(id); }
    @Override public List<Customer> searchCustomers(String keyword) { return customerDAO.searchByKeyword(keyword); }

    @Override
    public Customer ensureDefaultCustomer() {
        return customerDAO.findAll().stream()
                .filter(c -> "KVL01".equals(c.getCustomerCode()))
                .findFirst()
                .orElseGet(() -> {
                    Customer defaultCustomer = new Customer();
                    defaultCustomer.setCustomerCode("KVL01");
                    defaultCustomer.setFullName("Khách vãng lai");
                    defaultCustomer.setPhone("");
                    defaultCustomer.setEmail("");
                    defaultCustomer.setAddress("");
                    defaultCustomer.setActive(true);
                    return customerDAO.save(defaultCustomer);
                });
    }
}
