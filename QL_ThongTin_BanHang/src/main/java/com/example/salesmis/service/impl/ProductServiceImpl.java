package com.example.salesmis.service.impl;

import com.example.salesmis.dao.ProductDAO;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.service.ProductService;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override public List<Product> getAllProducts() { return productDAO.findAll(); }
    @Override public Product getProductById(Long id) { return productDAO.findById(id).orElse(null); }
    @Override public Product saveProduct(Product product) { return productDAO.save(product); }
    @Override public void deleteProduct(Long id) { productDAO.delete(id); }
    @Override public List<Product> searchProducts(String keyword) { return productDAO.searchByKeyword(keyword); }
}
