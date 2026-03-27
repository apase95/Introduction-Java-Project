package com.example.salesmis.controller;

import com.example.salesmis.model.entity.Category;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.service.LookupService;
import com.example.salesmis.service.ProductService;
import java.math.BigDecimal;
import java.util.List;

public class ProductController {
    private final ProductService productService;
    private final LookupService lookupService;

    public ProductController(ProductService productService, LookupService lookupService) {
        this.productService = productService;
        this.lookupService = lookupService;
    }

    public List<Category> getAllCategories() {
        return lookupService.getAllCategories();
    }

    public List<Product> getAllProducts() { return productService.getAllProducts(); }
    
    public Product getProductById(Long id) { return productService.getProductById(id); }
    
    public Product createProduct(String sku, String name, Long categoryId, BigDecimal price, int stock, boolean active) {
        return productService.createProduct(sku, name, categoryId, price, stock, active);
    }
    
    public Product updateProduct(Long id, String sku, String name, Long categoryId, BigDecimal price, int stock, boolean active) {
        return productService.updateProduct(id, sku, name, categoryId, price, stock, active);
    }
    
    public void deleteProduct(Long id) { productService.deleteProduct(id); }
    
    public List<Product> searchProducts(String keyword) { return productService.searchProducts(keyword); }
}
