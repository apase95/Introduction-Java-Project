package com.example.salesmis;

import com.example.salesmis.controller.CustomerController;
import com.example.salesmis.controller.OrderController;
import com.example.salesmis.controller.ProductController;
import com.example.salesmis.controller.ReportController;
import com.example.salesmis.dao.CustomerDAO;
import com.example.salesmis.dao.ProductDAO;
import com.example.salesmis.dao.SalesOrderDAO;
import com.example.salesmis.dao.impl.CustomerDAOImpl;
import com.example.salesmis.dao.impl.ProductDAOImpl;
import com.example.salesmis.dao.impl.SalesOrderDAOImpl;
import com.example.salesmis.service.CustomerService;
import com.example.salesmis.service.LookupService;
import com.example.salesmis.service.OrderService;
import com.example.salesmis.service.ProductService;
import com.example.salesmis.service.impl.CustomerServiceImpl;
import com.example.salesmis.service.impl.LookupServiceImpl;
import com.example.salesmis.service.impl.OrderServiceImpl;
import com.example.salesmis.service.impl.ProductServiceImpl;
import com.example.salesmis.view.CustomerManagementPanel;
import com.example.salesmis.view.MainFrame;
import com.example.salesmis.view.OrderManagementPanel;
import com.example.salesmis.view.ProductManagementPanel;
import com.example.salesmis.view.ReportManagementPanel;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        // DAOs
        CustomerDAO customerDAO = new CustomerDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();
        SalesOrderDAO salesOrderDAO = new SalesOrderDAOImpl();

        // Services
        LookupService lookupService = new LookupServiceImpl(customerDAO, productDAO);
        OrderService orderService = new OrderServiceImpl(salesOrderDAO, customerDAO, productDAO);
        CustomerService customerService = new CustomerServiceImpl(customerDAO);
        ProductService productService = new ProductServiceImpl(productDAO);

        // Controllers
        OrderController orderController = new OrderController(orderService, lookupService);
        ReportController reportController = new ReportController();
        CustomerController customerController = new CustomerController(customerService);
        ProductController productController = new ProductController(productService);

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(
                new OrderManagementPanel(orderController), 
                new ReportManagementPanel(reportController),
                new CustomerManagementPanel(customerController),
                new ProductManagementPanel(productController)
            );
            frame.setVisible(true);
        });
    }
}