package org.example.repository;

import org.example.model.DatabaseManager;

import java.sql.*;

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
        }
    }

    // Récupérer toutes les demandes en attente
    public ResultSet getPendingRequests(Connection conn) throws SQLException {
        String sql = """
            SELECT r.id, r.nom, r.description, r.latitude, r.longitude, u.username
            FROM owner_requests r
            JOIN users u ON r.user_id = u.id
            WHERE r.status = 'pending'
        """;
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }

    public void approve(int requestId) {
        String sql = "UPDATE owner_requests SET status = 'approved' WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, requestId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refuse(int requestId) {
        String sql = "UPDATE owner_requests SET status = 'refused' WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, requestId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
