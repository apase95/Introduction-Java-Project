package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
    /** Lấy danh sách tất cả danh mục sản phẩm. */
    List<Category> findAll();
    /** Tìm danh mục theo ID. */
    Optional<Category> findById(Long id);
}
