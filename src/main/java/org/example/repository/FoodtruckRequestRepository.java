package org.example.repository;

import org.example.model.DatabaseManager;

import java.sql.*;

public class FoodtruckRequestRepository {

    public boolean createRequest(int userId, String nom, String description) {

        String sql = """
            INSERT INTO requests (user_id, nom, description, status)
            VALUES (?, ?, ?, 'PENDING')
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, nom);
            ps.setString(3, description);
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Erreur création demande : " + e.getMessage());
            return false;
        }
    }

    public ResultSet getPendingRequests(Connection conn) throws SQLException {
        String sql = "SELECT r.id, r.nom, r.description, u.username FROM requests r JOIN users u ON r.user_id = u.id WHERE status = 'PENDING'";
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }

    public boolean approveRequest(int requestId) {
        String sql = "UPDATE requests SET status = 'APPROVED' WHERE id = ?";
        return update(sql, requestId);
    }

    public boolean refuseRequest(int requestId) {
        String sql = "UPDATE requests SET status = 'REFUSED' WHERE id = ?";
        return update(sql, requestId);
    }

    private boolean update(String sql, int id) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur update demande : " + e.getMessage());
            return false;
        }
    }
}
