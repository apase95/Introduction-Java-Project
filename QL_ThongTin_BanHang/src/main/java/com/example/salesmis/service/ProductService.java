package com.example.salesmis.service;

import com.example.salesmis.model.entity.Product;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product createProduct(String sku, String name, Long categoryId, BigDecimal price, int stock, boolean active);
    Product updateProduct(Long id, String sku, String name, Long categoryId, BigDecimal price, int stock, boolean active);
    void deleteProduct(Long id);
    List<Product> searchProducts(String keyword);
}
