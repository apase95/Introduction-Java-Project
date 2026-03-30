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

    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientService.getIngredientById(id);
    }

    public Ingredient createIngredient(String code, String name, String unit, BigDecimal stock, boolean active) {
        return ingredientService.createIngredient(code, name, unit, stock, active);
    }

    public Ingredient updateIngredient(Long id, String code, String name, String unit, BigDecimal stock, boolean active) {
        return ingredientService.updateIngredient(id, code, name, unit, stock, active);
    }

    public void deleteIngredient(Long id) {
        ingredientService.deleteIngredient(id);
    }

    public List<Ingredient> searchIngredients(String keyword) {
        return ingredientService.searchIngredients(keyword);
    }
}
