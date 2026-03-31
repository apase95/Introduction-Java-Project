package com.example.salesmis.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ingredient_code", nullable = false, unique = true, length = 30)
    private String ingredientCode;

    @Column(name = "ingredient_name", nullable = false, length = 150)
    private String ingredientName;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(name = "stock_qty", nullable = false, precision = 10, scale = 2)
    private BigDecimal stockQty = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    /** Constructor mặc định không tham số, JPA yêu cầu. */
    public Ingredient() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIngredientCode() { return ingredientCode; }
    public void setIngredientCode(String ingredientCode) { this.ingredientCode = ingredientCode; }

    public String getIngredientName() { return ingredientName; }
    public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public BigDecimal getStockQty() { return stockQty; }
    public void setStockQty(BigDecimal stockQty) { this.stockQty = stockQty; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public List<RecipeIngredient> getRecipeIngredients() { return recipeIngredients; }
    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) { this.recipeIngredients = recipeIngredients; }

    /** Trả về tên nguyên liệu để hiển thị trong ComboBox. */
    @Override
    public String toString() { return ingredientName; }

    /** So sánh hai nguyên liệu dựa trên ID. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient that)) return false;
        return id != null && Objects.equals(id, that.id);
    }
    /** Trả về hash code đồng nhất với equals. */
    @Override
    public int hashCode() { return getClass().hashCode(); }
}
