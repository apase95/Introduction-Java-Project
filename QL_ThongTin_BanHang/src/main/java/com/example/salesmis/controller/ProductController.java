package com.example.salesmis.controller;

import com.example.salesmis.model.entity.Product;
import com.example.salesmis.service.ProductService;
import java.math.BigDecimal;
import java.util.List;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getAllProducts() { return productService.getAllProducts(); }
    
    public Product getProductById(Long id) { return productService.getProductById(id); }
    
    public Product createProduct(String sku, String name, String category, BigDecimal price, int stock, boolean active) {
        Product p = new Product();
        p.setSku(sku);
        p.setProductName(name);
        p.setCategory(category);
        p.setUnitPrice(price);
        p.setStockQty(stock);
        p.setActive(active);
        return productService.saveProduct(p);
    }
    
    public Product updateProduct(Long id, String sku, String name, String category, BigDecimal price, int stock, boolean active) {
        Product p = productService.getProductById(id);
        if (p == null) throw new IllegalArgumentException("Không tìm thấy sản phẩm");
        p.setSku(sku);
        p.setProductName(name);
        p.setCategory(category);
        p.setUnitPrice(price);
        p.setStockQty(stock);
        p.setActive(active);
        return productService.saveProduct(p);
    }
    
    public void deleteProduct(Long id) { productService.deleteProduct(id); }
    
    public List<Product> searchProducts(String keyword) { return productService.searchProducts(keyword); }
}
