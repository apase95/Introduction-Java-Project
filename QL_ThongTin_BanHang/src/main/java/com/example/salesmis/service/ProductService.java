package com.example.salesmis.service;

import com.example.salesmis.model.entity.Product;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    /** Lấy danh sách tất cả sản phẩm. */
    List<Product> getAllProducts();
    /** Tìm sản phẩm theo ID. */
    Product getProductById(Long id);
    /** Tạo mới sản phẩm với đầy đủ thông tin. */
    Product createProduct(String sku, String name, Long categoryId, BigDecimal price, int stock, boolean active);
    /** Cập nhật thông tin sản phẩm theo ID. */
    Product updateProduct(Long id, String sku, String name, Long categoryId, BigDecimal price, int stock, boolean active);
    /** Xóa sản phẩm theo ID. */
    void deleteProduct(Long id);
    /** Tìm kiếm sản phẩm theo từ khóa (SKU hoặc tên). */
    List<Product> searchProducts(String keyword);
}
