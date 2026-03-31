package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Recipe;
import java.util.List;
import java.util.Optional;

public interface RecipeDAO {
    /** Lấy danh sách công thức theo ID sản phẩm. */
    List<Recipe> findByProductId(Long productId);
    /** Tìm công thức theo ID. */
    Optional<Recipe> findById(Long id);
    /** Lưu hoặc cập nhật công thức. */
    Recipe save(Recipe recipe);
    /** Xóa công thức theo ID. */
    void delete(Long id);
}
