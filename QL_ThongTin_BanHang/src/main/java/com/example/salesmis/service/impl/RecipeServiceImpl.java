package com.example.salesmis.service.impl;

import com.example.salesmis.dao.IngredientDAO;
import com.example.salesmis.dao.ProductDAO;
import com.example.salesmis.dao.RecipeDAO;
import com.example.salesmis.model.entity.Ingredient;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.model.entity.Recipe;
import com.example.salesmis.model.entity.RecipeIngredient;
import com.example.salesmis.service.RecipeService;

import java.math.BigDecimal;
import java.util.List;

public class RecipeServiceImpl implements RecipeService {
    private final RecipeDAO recipeDAO;
    private final IngredientDAO ingredientDAO;
    private final ProductDAO productDAO;

    public RecipeServiceImpl(RecipeDAO recipeDAO, IngredientDAO ingredientDAO, ProductDAO productDAO) {
        this.recipeDAO = recipeDAO;
        this.ingredientDAO = ingredientDAO;
        this.productDAO = productDAO;
    }

    @Override
    public List<Recipe> getRecipesByProduct(Long productId) {
        return recipeDAO.findByProductId(productId);
    }

    @Override
    public Recipe saveRecipe(Long productId, Recipe recipe) {
        if (recipe.getVariationName() == null || recipe.getVariationName().isBlank()) {
            throw new IllegalArgumentException("Tên công thức/size không được để trống.");
        }
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại."));
        recipe.setProduct(product);
        return recipeDAO.save(recipe);
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeDAO.delete(recipeId);
    }

    @Override
    public Recipe addIngredientToRecipe(Long recipeId, Long ingredientId, BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
        }
        Recipe recipe = recipeDAO.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Công thức không tồn tại."));
        Ingredient ingredient = ingredientDAO.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("Nguyên liệu không tồn tại."));

        // Check if ingredient already exists in recipe
        for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
            if (ri.getIngredient().getId().equals(ingredient.getId())) {
                ri.setQuantity(quantity);
                return recipeDAO.save(recipe);
            }
        }

        RecipeIngredient ri = new RecipeIngredient();
        ri.setIngredient(ingredient);
        ri.setQuantity(quantity);
        recipe.addIngredient(ri);

        return recipeDAO.save(recipe);
    }

    @Override
    public void removeIngredientFromRecipe(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeDAO.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Công thức không tồn tại."));
        
        RecipeIngredient target = null;
        for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
            if (ri.getIngredient().getId().equals(ingredientId)) {
                target = ri;
                break;
            }
        }
        
        if (target != null) {
            recipe.removeIngredient(target);
            recipeDAO.save(recipe);
        }
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.findAll();
    }

    @Override
    public Ingredient saveIngredient(Ingredient ingredient) {
        if (ingredient.getIngredientCode() == null || ingredient.getIngredientCode().isBlank()) {
            throw new IllegalArgumentException("Mã nguyên liệu không được để trống.");
        }
        if (ingredient.getIngredientName() == null || ingredient.getIngredientName().isBlank()) {
            throw new IllegalArgumentException("Tên nguyên liệu không được để trống.");
        }
        return ingredientDAO.save(ingredient);
    }

    @Override
    public void deleteIngredient(Long ingredientId) {
        ingredientDAO.delete(ingredientId);
    }
}
