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

    /** Lấy toàn bộ danh sách nguyên liệu từ CSDL. */
    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.findAll();
    }

    /** Tìm nguyên liệu theo ID, trả null nếu không tìm thấy. */
    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientDAO.findById(id).orElse(null);
    }

    /** Tạo mới nguyên liệu sau khi kiểm tra dữ liệu đầu vào. */
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

    /** Cập nhật thông tin nguyên liệu theo ID sau khi kiểm tra hợp lệ. */
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

    /** Xóa nguyên liệu theo ID. */
    @Override
    public void deleteIngredient(Long id) {
        ingredientDAO.delete(id);
    }

    /** Tìm kiếm nguyên liệu theo từ khóa trong tên hoặc mã (không phân biệt hoa thường). */
    @Override
    public List<Ingredient> searchIngredients(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllIngredients();
        String kw = keyword.toLowerCase().trim();
        return getAllIngredients().stream()
                .filter(i -> i.getIngredientName().toLowerCase().contains(kw) || i.getIngredientCode().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }
}
