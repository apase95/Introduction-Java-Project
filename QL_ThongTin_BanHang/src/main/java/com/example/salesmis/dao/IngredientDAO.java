package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Ingredient;
import java.util.List;
import java.util.Optional;

public interface IngredientDAO {
    /** Lấy danh sách tất cả nguyên liệu. */
    List<Ingredient> findAll();
    /** Tìm nguyên liệu theo ID. */
    Optional<Ingredient> findById(Long id);
    /** Lưu hoặc cập nhật nguyên liệu. */
    Ingredient save(Ingredient ingredient);
    /** Xóa nguyên liệu theo ID. */
    void delete(Long id);
}
