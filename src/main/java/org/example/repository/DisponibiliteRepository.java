package org.example.repository;

import org.example.model.DatabaseManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.sothawo.mapjfx.Coordinate;

public class DisponibiliteRepository {

    public static class Resultat {
        public List<String> noms = new ArrayList<>();
        public List<Coordinate> coords = new ArrayList<>();
    }

    public Resultat getDisponibilitesComplet(LocalDate date) {
        Resultat r = new Resultat();

        // Sécurité : éviter le crash
        if (date == null) {
            System.out.println("⚠ getDisponibilitesComplet() appelé avec une date NULL — aucune disponibilité chargée.");
            return r;
        }

        String sql = """
            SELECT f.nom, p.latitude, p.longitude
            FROM Disponibilite d
            JOIN FoodTruck f ON f.id = d.foodtruck_id
            JOIN Positions p ON p.id = d.position_id
            WHERE DATE(d.date_debut) = ?
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                r.noms.add(rs.getString("nom"));
                r.coords.add(new Coordinate(rs.getDouble("latitude"), rs.getDouble("longitude")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }
}
