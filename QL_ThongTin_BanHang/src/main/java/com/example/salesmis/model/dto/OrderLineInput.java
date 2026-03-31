package com.example.salesmis.model.dto;

import java.math.BigDecimal;

public class OrderLineInput {
    private Long productId;
    private Long recipeId;
    private int quantity;
    private BigDecimal unitPrice;

    /** Constructor mặc định không tham số. */
    public OrderLineInput() {}

    /** Constructor tạo một dòng đơn hàng với đầy đủ thông tin. */
    public OrderLineInput(Long productId, Long recipeId, int quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getProductId() { return productId; }
    public Long getRecipeId() { return recipeId; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }

    public void setProductId(Long productId) { this.productId = productId; }
    public void setRecipeId(Long recipeId) { this.recipeId = recipeId; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
