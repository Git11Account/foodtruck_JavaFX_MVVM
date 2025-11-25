package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.example.model.DatabaseManager;
import org.example.utils.SceneManager;
import org.example.model.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MapController {

    @FXML private WebView mapWebView;

    @FXML private javafx.scene.control.Button requestButton;
    @FXML private javafx.scene.control.Button ownerDashboardButton;

    private WebEngine engine;

    @FXML
    private void initialize() {
        System.out.println("MapController initialisé");

        engine = mapWebView.getEngine();
        engine.setJavaScriptEnabled(true);

        // Charger la carte locale
        engine.load(getClass().getResource("/map/map.html").toExternalForm());

        // Charger les foodtrucks APPROVED après chargement de la page
        engine.documentProperty().addListener((obs, oldDoc, newDoc) -> {
            if (newDoc != null) {
                loadApprovedFoodtrucks();
            }
        });

        setupButtons();
    }

    private void setupButtons() {
        if (UserSession.isAdmin()) {
            requestButton.setVisible(false);
            ownerDashboardButton.setVisible(false);
        }
        else if (UserSession.isOwner()) {
            requestButton.setVisible(false);
            ownerDashboardButton.setVisible(true);
            ownerDashboardButton.setOnAction(e ->
                    SceneManager.switchTo("/org/example/view/OwnerDashboard.fxml")
            );
        }
        else {
            // USER
            requestButton.setVisible(true);
            ownerDashboardButton.setVisible(false);
            requestButton.setOnAction(e ->
                    SceneManager.switchTo("/org/example/view/RequestOwnerView.fxml")
            );
        }
    }

    private void loadApprovedFoodtrucks() {
        System.out.println("Chargement des foodtrucks…");

        String sql = "SELECT nom, latitude, longitude FROM owner_requests WHERE status = 'approved'";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("nom");
                double lat = rs.getDouble("latitude");
                double lon = rs.getDouble("longitude");

                engine.executeScript(
                        "window.addFoodtruckMarker(" +
                                "'" + name.replace("'", "\\'") + "', " +
                                lat + ", " + lon +
                                ");"
                );
            }

        } catch (Exception e) {
            System.out.println("Erreur loadApprovedFoodtrucks : " + e.getMessage());
        }
    }
}
