package com.example.salesmis.service;

import com.example.salesmis.model.entity.Ingredient;
import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {
    List<Ingredient> getAllIngredients();
    Ingredient getIngredientById(Long id);
    Ingredient createIngredient(String code, String name, String unit, BigDecimal stock, boolean active);
    Ingredient updateIngredient(Long id, String code, String name, String unit, BigDecimal stock, boolean active);
    void deleteIngredient(Long id);
    List<Ingredient> searchIngredients(String keyword);
}
