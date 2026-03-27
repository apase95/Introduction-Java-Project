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

    public void addIngredient(RecipeIngredient ri) {
        recipeIngredients.add(ri);
        ri.setRecipe(this);
    }

    public void removeIngredient(RecipeIngredient ri) {
        recipeIngredients.remove(ri);
        ri.setRecipe(null);
    }

    @Override
    public String toString() { return variationName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe that)) return false;
        return id != null && Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() { return getClass().hashCode(); }
}
