package org.example.repository;

import org.example.model.DatabaseManager;
import com.sothawo.mapjfx.Coordinate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository {

    public record FoodtruckRequest(
            int id,
            String ownerName,
            String nom,
            String description,
            Coordinate coord
    ) {}

    public List<FoodtruckRequest> getPendingRequests() {
        List<FoodtruckRequest> list = new ArrayList<>();

        String sql = """
            SELECT f.id, u.username, f.nom, f.description, f.latitude, f.longitude
            FROM foodtrucks f
            JOIN users u ON f.owner_id = u.id
            WHERE f.status = 'PENDING'
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new FoodtruckRequest(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        new Coordinate(
                                rs.getDouble("latitude"),
                                rs.getDouble("longitude")
                        )
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void approve(int id) {
        String sql = "UPDATE foodtrucks SET status = 'APPROVED' WHERE id = ?";
        updateStatus(id, sql);
    }

    public void refuse(int id) {
        String sql = "UPDATE foodtrucks SET status = 'REFUSED' WHERE id = ?";
        updateStatus(id, sql);
    }

    private void updateStatus(int id, String sql) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
