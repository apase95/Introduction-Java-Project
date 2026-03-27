package com.example.salesmis.service.impl;

import com.example.salesmis.dao.CategoryDAO;
import com.example.salesmis.dao.CustomerDAO;
import com.example.salesmis.dao.DiningTableDAO;
import com.example.salesmis.dao.IngredientDAO;
import com.example.salesmis.dao.ProductDAO;
import com.example.salesmis.dao.RecipeDAO;
import com.example.salesmis.model.entity.Category;
import com.example.salesmis.model.entity.Customer;
import com.example.salesmis.model.entity.DiningTable;
import com.example.salesmis.model.entity.Ingredient;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.model.entity.Recipe;
import com.example.salesmis.service.LookupService;
import java.util.List;

public class LookupServiceImpl implements LookupService {
    private final CustomerDAO customerDAO;
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final DiningTableDAO diningTableDAO;
    private final IngredientDAO ingredientDAO;
    private final RecipeDAO recipeDAO;

    public LookupServiceImpl(CustomerDAO customerDAO, ProductDAO productDAO, CategoryDAO categoryDAO, DiningTableDAO diningTableDAO, IngredientDAO ingredientDAO, RecipeDAO recipeDAO) {
        this.customerDAO = customerDAO;
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.diningTableDAO = diningTableDAO;
        this.ingredientDAO = ingredientDAO;
        this.recipeDAO = recipeDAO;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    @Override
    public List<DiningTable> getAllDiningTables() {
        return diningTableDAO.findAll();
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.findAll();
    }

    @Override
    public List<Recipe> getRecipesByProductId(Long productId) {
        return recipeDAO.findByProductId(productId);
    }
}
