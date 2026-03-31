package com.example.salesmis.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String sku;

    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Column(name = "stock_qty", nullable = false)
    private Integer stockQty = 0;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "image_path")
    private String imagePath = "default.png";

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Recipe> recipes = new ArrayList<>();

    /** Constructor mặc định không tham số, JPA yêu cầu. */
    public Product() {}

    /** Constructor tạo sản phẩm với mã SKU, tên và giá bán. */
    public Product(String sku, String productName, BigDecimal unitPrice) {
        this.sku = sku;
        this.productName = productName;
        this.unitPrice = unitPrice;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getProductName() { return productName; }
    public Category getCategory() { return category; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public Integer getStockQty() { return stockQty; }
    public Boolean getActive() { return active; }
    public String getImagePath() { return imagePath; }
    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public List<Recipe> getRecipes() { return recipes; }

    public void setId(Long id) { this.id = id; }
    public void setSku(String sku) { this.sku = sku; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setCategory(Category category) { this.category = category; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public void setStockQty(Integer stockQty) { this.stockQty = stockQty; }
    public void setActive(Boolean active) { this.active = active; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }
    public void setRecipes(List<Recipe> recipes) { this.recipes = recipes; }

    /** Trả về mã SKU và tên sản phẩm để hiển thị trong ComboBox. */
    @Override
    public String toString() {
        return sku + " - " + productName;
    }

    /** So sánh hai sản phẩm dựa trên ID. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    /** Trả về hash code đồng nhất với equals. */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
