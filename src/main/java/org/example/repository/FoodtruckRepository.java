package org.example.repository;

import org.example.model.DatabaseManager;
import org.example.model.Foodtruck;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodtruckRepository {

    public List<Foodtruck> getFoodtrucksByOwner(int ownerId) {
        List<Foodtruck> list = new ArrayList<>();

        String sql = "SELECT id, owner_id, nom, type_cuisine, telephone, description, latitude, longitude, status " +
                "FROM foodtruck WHERE owner_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Foodtruck(
                        rs.getInt("id"),
                        rs.getInt("owner_id"),
                        rs.getString("nom"),
                        rs.getString("type_cuisine"),
                        rs.getString("telephone"),
                        rs.getString("description"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean createFoodtruck(int ownerId, String nom, String typeCuisine, String telephone,
                                   String description, double lat, double lon) {

        String sql = "INSERT INTO foodtruck (owner_id, nom, type_cuisine, telephone, is_validated, description, latitude, longitude, status) " +
                "VALUES (?, ?, ?, ?, 1, ?, ?, ?, 'APPROVED')";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, ownerId);
            ps.setString(2, nom);
            ps.setString(3, typeCuisine);
            ps.setString(4, telephone);
            ps.setString(5, description);
            ps.setDouble(6, lat);
            ps.setDouble(7, lon);

            int affected = ps.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFoodtruck(int id, String nom, String typeCuisine, String telephone,
                                   String description, double lat, double lon) {

        String sql = "UPDATE foodtruck SET nom=?, type_cuisine=?, telephone=?, description=?, latitude=?, longitude=? WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nom);
            ps.setString(2, typeCuisine);
            ps.setString(3, telephone);
            ps.setString(4, description);
            ps.setDouble(5, lat);
            ps.setDouble(6, lon);
            ps.setInt(7, id);

            int affected = ps.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
