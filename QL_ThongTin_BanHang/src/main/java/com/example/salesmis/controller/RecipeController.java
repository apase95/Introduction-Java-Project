package com.example.salesmis.controller;

import com.example.salesmis.model.entity.Ingredient;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.model.entity.Recipe;
import com.example.salesmis.service.LookupService;
import com.example.salesmis.service.RecipeService;

import java.math.BigDecimal;
import java.util.List;

public class RecipeController {
    private final RecipeService recipeService;
    private final LookupService lookupService;

    public RecipeController(RecipeService recipeService, LookupService lookupService) {
        this.recipeService = recipeService;
        this.lookupService = lookupService;
    }

    public List<Product> getAllProducts() {
        return lookupService.getAllProducts();
    }

    public List<Recipe> getRecipesByProduct(Long productId) {
        return recipeService.getRecipesByProduct(productId);
    }

    public Recipe saveRecipe(Long productId, Long recipeId, String variationName, boolean active) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.setVariationName(variationName);
        recipe.setActive(active);
        return recipeService.saveRecipe(productId, recipe);
    }

    public void deleteRecipe(Long recipeId) {
        recipeService.deleteRecipe(recipeId);
    }

    public List<Ingredient> getAllIngredients() {
        return recipeService.getAllIngredients();
    }

    public Recipe addIngredientToRecipe(Long recipeId, Long ingredientId, BigDecimal quantity) {
        return recipeService.addIngredientToRecipe(recipeId, ingredientId, quantity);
    }

    public void removeIngredientFromRecipe(Long recipeId, Long ingredientId) {
        recipeService.removeIngredientFromRecipe(recipeId, ingredientId);
    }

    public Ingredient saveIngredient(Long id, String code, String name, String unit, BigDecimal stockQty, boolean active) {
        Ingredient i = new Ingredient();
        i.setId(id);
        i.setIngredientCode(code);
        i.setIngredientName(name);
        i.setUnit(unit);
        i.setStockQty(stockQty);
        i.setActive(active);
        return recipeService.saveIngredient(i);
    }

    public void deleteIngredient(Long id) {
        recipeService.deleteIngredient(id);
    }
}
