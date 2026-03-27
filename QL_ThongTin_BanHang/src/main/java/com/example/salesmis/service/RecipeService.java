package com.example.salesmis.service;

import com.example.salesmis.model.entity.Ingredient;
import com.example.salesmis.model.entity.Recipe;
import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipesByProduct(Long productId);
    Recipe saveRecipe(Long productId, Recipe recipe);
    void deleteRecipe(Long recipeId);
    
    Recipe addIngredientToRecipe(Long recipeId, Long ingredientId, java.math.BigDecimal quantity);
    void removeIngredientFromRecipe(Long recipeId, Long ingredientId);
    
    List<Ingredient> getAllIngredients();
    Ingredient saveIngredient(Ingredient ingredient);
    void deleteIngredient(Long ingredientId);
}
