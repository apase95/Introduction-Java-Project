package com.example.salesmis.service;

import com.example.salesmis.model.entity.Ingredient;
import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {
    /** Lấy danh sách tất cả nguyên liệu. */
    List<Ingredient> getAllIngredients();
    /** Tìm nguyên liệu theo ID. */
    Ingredient getIngredientById(Long id);
    /** Tạo mới nguyên liệu với đầy đủ thông tin. */
    Ingredient createIngredient(String code, String name, String unit, BigDecimal stock, boolean active);
    /** Cập nhật thông tin nguyên liệu theo ID. */
    Ingredient updateIngredient(Long id, String code, String name, String unit, BigDecimal stock, boolean active);
    /** Xóa nguyên liệu theo ID. */
    void deleteIngredient(Long id);
    /** Tìm kiếm nguyên liệu theo từ khóa. */
    List<Ingredient> searchIngredients(String keyword);
}
