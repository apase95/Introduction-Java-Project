package com.example.salesmis.service;

import com.example.salesmis.model.entity.Category;
import com.example.salesmis.model.entity.Customer;
import com.example.salesmis.model.entity.DiningTable;
import com.example.salesmis.model.entity.Ingredient;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.model.entity.Recipe;
import java.util.List;

public interface LookupService {
    /** Lấy danh sách khách hàng cho ComboBox. */
    List<Customer> getAllCustomers();
    /** Lấy danh sách sản phẩm cho ComboBox. */
    List<Product> getAllProducts();
    /** Lấy danh sách danh mục cho ComboBox. */
    List<Category> getAllCategories();
    /** Lấy danh sách bàn ăn cho ComboBox. */
    List<DiningTable> getAllDiningTables();
    /** Lấy danh sách nguyên liệu cho ComboBox. */
    List<Ingredient> getAllIngredients();
    /** Lấy danh sách công thức theo ID sản phẩm. */
    List<Recipe> getRecipesByProductId(Long productId);
}
