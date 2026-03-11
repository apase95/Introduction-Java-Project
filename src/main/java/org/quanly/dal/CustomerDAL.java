package org.quanly.dal;

import org.quanly.dto.CustomerDTO;
import org.quanly.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAL {
    public List<CustomerDTO> getAll() {

        List<CustomerDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM Customers";

        try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CustomerDTO c = new CustomerDTO();
                c.setId(rs.getInt("customer_id"));
                c.setName(rs.getString("customer_name"));
                c.setAddress(rs.getString("address"));
                c.setPhone(rs.getString("phone"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insert(CustomerDTO c) {
        String sql = "INSERT INTO Customers (customer_name, address, phone) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, c.getName());
            pst.setString(2, c.getAddress());
            pst.setString(3, c.getPhone());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(CustomerDTO c) {
        String sql = "UPDATE Customers SET customer_name = ?, address = ?, phone = ? WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, c.getName());
            pst.setString(2, c.getAddress());
            pst.setString(3, c.getPhone());
            pst.setInt(4, c.getId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Customers WHERE customer_id = ?";
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
