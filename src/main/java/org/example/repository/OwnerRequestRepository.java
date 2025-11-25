package org.example.repository;

import org.example.model.DatabaseManager;
import org.example.model.OwnerRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerRequestRepository {

    public static void createRequest(int userId, String nom, String description) {

        String sql = "INSERT INTO owner_requests (user_id, nom, description, status) VALUES (?, ?, ?, 'pending')";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, nom);
            stmt.setString(3, description);
            stmt.setString(4, "pending");

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<OwnerRequest> getAllRequests() {
        List<OwnerRequest> list = new ArrayList<>();

        String sql = "SELECT * FROM owner_requests WHERE status='pending'";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
