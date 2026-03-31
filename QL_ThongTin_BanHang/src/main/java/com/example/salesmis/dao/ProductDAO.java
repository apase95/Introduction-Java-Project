package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    /** Lấy danh sách tất cả sản phẩm. */
    List<Product> findAll();
    /** Tìm sản phẩm theo ID. */
    Optional<Product> findById(Long id);
    /** Lưu hoặc cập nhật sản phẩm. */
    Product save(Product product);
    /** Xóa sản phẩm theo ID. */
    void delete(Long id);
    /** Tìm kiếm sản phẩm theo từ khóa (SKU hoặc tên). */
    List<Product> searchByKeyword(String keyword);
}
