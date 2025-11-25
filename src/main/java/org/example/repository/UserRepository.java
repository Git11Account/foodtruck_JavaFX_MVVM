package org.example.repository;

import org.example.model.DatabaseManager;
import org.example.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserRepository {

    /** Inscription simple (utilisateur standard) */
    public boolean register(String username, String password) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'USER')";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, hashed);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur création user : " + e.getMessage());
            return false;
        }
    }

    /** Vérifie login et renvoie uniquement le rôle (ancienne version) */
    public String login(String username, String password) {
        String sql = "SELECT password, role FROM users WHERE username = ?";

        System.out.println(sql);

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashed = rs.getString("password");
                String role   = rs.getString("role");

                System.out.println(hashed + " / " + password);

                if (BCrypt.checkpw(password, hashed)) {
                    return role;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur login : " + e.getMessage());
        }

        return null;
    }

    /** NOUVELLE VERSION — Retourne l'utilisateur complet */
    public User loginUserObject(String username, String password) {

        String sql = "SELECT id, username, password, role FROM users WHERE username = ?";

        System.out.println(sql);

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String hashed = rs.getString("password");

                // Debug propre
                System.out.println("Comparaison : ");
                System.out.println("Mot de passe saisi = " + password);
                System.out.println("Hash en base       = " + hashed);

                if (!BCrypt.checkpw(password, hashed)) {
                    System.out.println("Mot de passe incorrect");
                    return null;
                }

                System.out.println("✔ Mot de passe OK !");

                int id = rs.getInt("id");
                String role = rs.getString("role");

                // ⬅⬅⬅ ICI tu dois renvoyer un User COMPLET
                return new User(id, username, hashed, role);
            }

        } catch (SQLException e) {
            System.out.println("Erreur login user : " + e.getMessage());
        }

        return null;
    }




    /** 🔥 AUTO-CRÉATION D'UN COMPTE ADMIN SI ABSENT */
    public void createAdminIfNotExists() {
        String check = "SELECT id FROM users WHERE role = 'ADMIN' LIMIT 1";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(check)) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Admin déjà présent.");
                return;
            }

            System.out.println("Aucun admin trouvé → création de l'admin...");

            String hashed = BCrypt.hashpw("admin123", BCrypt.gensalt());

            String insert = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

            PreparedStatement insertPs = conn.prepareStatement(insert);
            insertPs.setString(1, "admin");
            insertPs.setString(2, hashed);
            insertPs.setString(3, "ADMIN");
            insertPs.executeUpdate();

            System.out.println("Admin créé : username = admin | password = admin123");

        } catch (SQLException e) {
            System.out.println("Erreur création admin : " + e.getMessage());
        }
    }
}
