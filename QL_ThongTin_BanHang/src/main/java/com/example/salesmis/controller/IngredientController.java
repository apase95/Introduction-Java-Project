package com.example.salesmis.controller;

import com.example.salesmis.model.entity.Ingredient;
import com.example.salesmis.service.IngredientService;

import java.math.BigDecimal;
import java.util.List;

public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /** Lấy danh sách tất cả nguyên liệu. */
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    /** Tìm nguyên liệu theo ID. */
    public Ingredient getIngredientById(Long id) {
        return ingredientService.getIngredientById(id);
    }

    /** Tạo mới nguyên liệu. */
    public Ingredient createIngredient(String code, String name, String unit, BigDecimal stock, boolean active) {
        return ingredientService.createIngredient(code, name, unit, stock, active);
    }

    /** Cập nhật thông tin nguyên liệu. */
    public Ingredient updateIngredient(Long id, String code, String name, String unit, BigDecimal stock, boolean active) {
        return ingredientService.updateIngredient(id, code, name, unit, stock, active);
    }

    /** Xóa nguyên liệu theo ID. */
    public void deleteIngredient(Long id) {
        ingredientService.deleteIngredient(id);
    }

    /** Tìm kiếm nguyên liệu theo từ khóa. */
    public List<Ingredient> searchIngredients(String keyword) {
        return ingredientService.searchIngredients(keyword);
    }
}
