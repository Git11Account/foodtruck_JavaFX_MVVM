package org.example.repository;

import org.example.model.DatabaseManager;
import org.example.model.OwnerRequest;
import java.sql.*;

public class RequestRepository {

    // =============== INSERT =================
    public void insert(OwnerRequest req) {
        String sql = "INSERT INTO owner_requests (user_id, nom, description, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, req.getUserId());
            ps.setString(2, req.getNom());
            ps.setString(3, req.getDescription());
            ps.setString(4, req.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur insert() : " + e.getMessage());
        }
    }

    // =============== SELECT pending requests =================
    public ResultSet getPendingRequests(Connection conn) {
        try {
            String sql = "SELECT r.id, r.nom, u.username " +
                    "FROM owner_requests r " +
                    "JOIN users u ON r.user_id = u.id " +
                    "WHERE r.status = 'pending'";

            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Erreur getPendingRequests() : " + e.getMessage());
            return null;
        }
    }

    // =============== APPROVE =================
    public void approve(int id) {
        updateStatus(id, "approved");
    }

    // =============== REFUSE ==================
    public void refuse(int id) {
        updateStatus(id, "refused");
    }

    private void updateStatus(int id, String status) {
        String sql = "UPDATE owner_requests SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur updateStatus() : " + e.getMessage());
        }
    }
}
