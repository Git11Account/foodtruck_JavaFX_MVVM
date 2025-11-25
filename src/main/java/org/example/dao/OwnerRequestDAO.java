package org.example.dao;

import org.example.model.OwnerRequest;
import org.example.model.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerRequestDAO {

    public void createRequest(int userId, String nom, String description, double latitude, double longitude) {
        String sql = "INSERT INTO owner_requests (user_id, nom, description, latitude, longitude, status) " +
                "VALUES (?, ?, ?, ?, ?, 'pending')";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, nom);
            ps.setString(3, description);
            ps.setDouble(4, latitude);
            ps.setDouble(5, longitude);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur createRequest : " + e.getMessage());
        }
    }



    public static List<OwnerRequest> getAllRequests() {
        List<OwnerRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM owner_requests ORDER BY created_at DESC";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new OwnerRequest(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
