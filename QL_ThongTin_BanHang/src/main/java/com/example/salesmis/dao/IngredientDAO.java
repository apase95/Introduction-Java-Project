package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Ingredient;
import java.util.List;
import java.util.Optional;

public interface IngredientDAO {
    List<Ingredient> findAll();
    Optional<Ingredient> findById(Long id);
    Ingredient save(Ingredient ingredient);
    void delete(Long id);
}
