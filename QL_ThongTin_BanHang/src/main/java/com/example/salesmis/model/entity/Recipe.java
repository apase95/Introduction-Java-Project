package com.example.salesmis.model.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "variation_name", nullable = false, length = 100)
    private String variationName;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    /** Constructor mặc định không tham số, JPA yêu cầu. */
    public Recipe() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getVariationName() { return variationName; }
    public void setVariationName(String variationName) { this.variationName = variationName; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public List<RecipeIngredient> getRecipeIngredients() { return recipeIngredients; }
    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) { this.recipeIngredients = recipeIngredients; }

    /** Thêm một nguyên liệu vào công thức và thiết lập liên kết ngược. */
    public void addIngredient(RecipeIngredient ri) {
        recipeIngredients.add(ri);
        ri.setRecipe(this);
    }

    /** Xóa một nguyên liệu khỏi công thức và hủy liên kết ngược. */
    public void removeIngredient(RecipeIngredient ri) {
        recipeIngredients.remove(ri);
        ri.setRecipe(null);
    }

    /** Trả về tên biến thể để hiển thị trong ComboBox. */
    @Override
    public String toString() { return variationName; }

    /** So sánh hai công thức dựa trên ID. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe that)) return false;
        return id != null && Objects.equals(id, that.id);
    }
    /** Trả về hash code đồng nhất với equals. */
    @Override
    public int hashCode() { return getClass().hashCode(); }
}
