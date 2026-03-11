package org.quanly.dal;

import org.quanly.dto.CategoryDTO;
import org.quanly.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAL {
    public List<CategoryDTO> getAll() {
        List<CategoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Categories";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new CategoryDTO(rs.getInt("category_id"), rs.getString("category_name")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
