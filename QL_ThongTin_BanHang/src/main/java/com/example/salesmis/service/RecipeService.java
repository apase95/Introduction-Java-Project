package com.example.salesmis.service;

import com.example.salesmis.model.entity.Ingredient;
import com.example.salesmis.model.entity.Recipe;
import com.example.salesmis.model.entity.Product;
import java.util.List;

public interface RecipeService {
    /** Lấy danh sách công thức của một sản phẩm. */
    List<Recipe> getRecipesByProduct(Long productId);
    /** Lưu hoặc cập nhật công thức. */
    Recipe saveRecipe(Long productId, Recipe recipe);
    /** Xóa công thức theo ID. */
    void deleteRecipe(Long recipeId);
    
    /** Thêm nguyên liệu vào công thức với số lượng. */
    Recipe addIngredientToRecipe(Long recipeId, Long ingredientId, java.math.BigDecimal quantity);
    /** Xóa nguyên liệu khỏi công thức. */
    void removeIngredientFromRecipe(Long recipeId, Long ingredientId);
    
    /** Lấy toàn bộ danh sách nguyên liệu. */
    List<Ingredient> getAllIngredients();
    /** Lưu nguyên liệu sau khi kiểm tra hợp lệ. */
    Ingredient saveIngredient(Ingredient ingredient);
    /** Xóa nguyên liệu theo ID. */
    void deleteIngredient(Long ingredientId);

    /** Cập nhật đường dẫn hình ảnh của sản phẩm. */
    Product updateProductImagePath(Long productId, String imagePath);
}
