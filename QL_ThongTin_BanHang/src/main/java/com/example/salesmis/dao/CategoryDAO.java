package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
    List<Category> findAll();
    Optional<Category> findById(Long id);
}
