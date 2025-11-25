package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.repository.OwnerRequestDAO;
import org.example.utils.SceneManager;
import java.sql.*;

import org.example.model.DatabaseManager;

public class AdminDashboardController {

    @FXML private ListView<String> demandesList;
    @FXML private Label labelNom;
    @FXML private Label labelOwner;
    @FXML private TextArea labelDescription;

    @FXML private Label labelLatitude;
    @FXML private Label labelLongitude;

    @FXML private Button approveButton;
    @FXML private Button refuseButton;

    private int selectedRequestId = -1;

    private final OwnerRequestDAO repo = new OwnerRequestDAO();

    @FXML
    private void initialize() {
        loadPendingRequests();

        demandesList.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> {
            if (val != null) showDetails(val);
        });

        approveButton.setOnAction(e -> {
            if (selectedRequestId != -1) {
                repo.approve(selectedRequestId);
                loadPendingRequests();
            }
        });

        refuseButton.setOnAction(e -> {
            if (selectedRequestId != -1) {
                repo.refuse(selectedRequestId);
                loadPendingRequests();
            }
        });
    }

    private void loadPendingRequests() {
        demandesList.getItems().clear();

        try (Connection conn = DatabaseManager.getConnection();
             ResultSet rs = repo.getPendingRequests(conn)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String owner = rs.getString("username");

                demandesList.getItems().add(id + " - " + nom + " (" + owner + ")");
            }

        } catch (Exception e) {
            System.out.println("Erreur loadPendingRequests : " + e.getMessage());
        }
    }

    private void showDetails(String selected) {
        try {
            String[] parts = selected.split(" - ");
            selectedRequestId = Integer.parseInt(parts[0]);

            String sql = """
                SELECT r.nom, r.description, r.latitude, r.longitude, u.username
                FROM owner_requests r
                JOIN users u ON r.user_id = u.id
                WHERE r.id = ?
            """;

            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, selectedRequestId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                labelNom.setText(rs.getString("nom"));
                labelOwner.setText(rs.getString("username"));
                labelDescription.setText(rs.getString("description"));

                labelLatitude.setText(String.valueOf(rs.getDouble("latitude")));
                labelLongitude.setText(String.valueOf(rs.getDouble("longitude")));
            }

        } catch (Exception e) {
            System.out.println("Erreur showDetails : " + e.getMessage());
        }
    }
}
