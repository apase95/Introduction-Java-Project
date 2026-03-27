package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Recipe;
import java.util.List;
import java.util.Optional;

public interface RecipeDAO {
    List<Recipe> findByProductId(Long productId);
    Optional<Recipe> findById(Long id);
    Recipe save(Recipe recipe);
    void delete(Long id);
}
