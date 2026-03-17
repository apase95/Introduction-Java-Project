package org.quanly.dal;

import org.quanly.dto.ProductDTO;
import org.quanly.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDAL {
    public List<ProductDTO> getAll() {
        List<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT p.*, c.category_name FROM Products p JOIN Categories c ON p.category_id = c.category_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ProductDTO p = new ProductDTO();
                p.setId(rs.getInt("product_id"));
                p.setName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setCategoryName(rs.getString("category_name"));
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(ProductDTO p) {
        String sql = "INSERT INTO Products (product_name, price, quantity, category_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, p.getName());
            pst.setDouble(2, p.getPrice());
            pst.setInt(3, p.getQuantity());
            pst.setInt(4, p.getCategoryId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public boolean update(ProductDTO p) {
        String sql = "UPDATE Products SET product_name = ?, price = ?, quantity = ?, category_id = ? WHERE product_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, p.getName());
            pst.setDouble(2, p.getPrice());
            pst.setInt(3, p.getQuantity());
            pst.setInt(4, p.getCategoryId());
            pst.setInt(5, p.getId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Products WHERE product_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
