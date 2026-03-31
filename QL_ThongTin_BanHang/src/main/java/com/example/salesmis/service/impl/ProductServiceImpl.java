package com.example.salesmis.service.impl;

import com.example.salesmis.dao.CategoryDAO;
import com.example.salesmis.dao.ProductDAO;
import com.example.salesmis.model.entity.Category;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.service.ProductService;
import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    public ProductServiceImpl(ProductDAO productDAO, CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
    }

    @Override public List<Product> getAllProducts() { return productDAO.findAll(); }
    @Override public Product getProductById(Long id) { return productDAO.findById(id).orElse(null); }

    /** Tạo mới sản phẩm và gắn danh mục nếu được chứ định. */
    @Override
    public Product createProduct(String sku, String name, Long categoryId, BigDecimal price, int stock, boolean active) {
        Category category = null;
        if (categoryId != null) {
            category = categoryDAO.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục"));
        }
        Product p = new Product();
        p.setSku(sku);
        p.setProductName(name);
        p.setCategory(category);
        p.setUnitPrice(price);
        p.setStockQty(stock);
        p.setActive(active);
        return productDAO.save(p);
    }

    /** Cập nhật thông tin sản phẩm; ném ngoại lệ nếu không tìm thấy. */
    @Override
    public Product updateProduct(Long id, String sku, String name, Long categoryId, BigDecimal price, int stock, boolean active) {
        Product p = productDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm"));
        Category category = null;
        if (categoryId != null) {
            category = categoryDAO.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục"));
        }
        p.setSku(sku);
        p.setProductName(name);
        p.setCategory(category);
        p.setUnitPrice(price);
        p.setStockQty(stock);
        p.setActive(active);
        return productDAO.save(p);
    }

    @Override public void deleteProduct(Long id) { productDAO.delete(id); }
    @Override public List<Product> searchProducts(String keyword) { return productDAO.searchByKeyword(keyword); }
}
