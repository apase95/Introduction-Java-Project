package com.example.salesmis.service.impl;

import com.example.salesmis.dao.IngredientDAO;
import com.example.salesmis.model.entity.Ingredient;
import com.example.salesmis.service.IngredientService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientServiceImpl implements IngredientService {
    private final IngredientDAO ingredientDAO;

    public IngredientServiceImpl(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.findAll();
    }

    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientDAO.findById(id).orElse(null);
    }

    @Override
    public Ingredient createIngredient(String code, String name, String unit, BigDecimal stock, boolean active) {
        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("Mã nguyên liệu không được để trống");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Tên nguyên liệu không được để trống");
        if (stock == null || stock.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Tồn kho phải >= 0");

        Ingredient i = new Ingredient();
        i.setIngredientCode(code);
        i.setIngredientName(name);
        i.setUnit(unit);
        i.setStockQty(stock);
        i.setActive(active);
        return ingredientDAO.save(i);
    }

    @Override
    public Ingredient updateIngredient(Long id, String code, String name, String unit, BigDecimal stock, boolean active) {
        if (id == null) throw new IllegalArgumentException("ID không hợp lệ");
        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("Mã nguyên liệu không được để trống");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Tên nguyên liệu không được để trống");
        if (stock == null || stock.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Tồn kho phải >= 0");

        Ingredient i = getIngredientById(id);
        if (i == null) throw new IllegalArgumentException("Nguyên liệu không tồn tại");

        i.setIngredientCode(code);
        i.setIngredientName(name);
        i.setUnit(unit);
        i.setStockQty(stock);
        i.setActive(active);
        return ingredientDAO.save(i);
    }

    @Override
    public void deleteIngredient(Long id) {
        ingredientDAO.delete(id);
    }

    @Override
    public List<Ingredient> searchIngredients(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllIngredients();
        String kw = keyword.toLowerCase().trim();
        return getAllIngredients().stream()
                .filter(i -> i.getIngredientName().toLowerCase().contains(kw) || i.getIngredientCode().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }
}
