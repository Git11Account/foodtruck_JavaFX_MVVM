package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.model.Foodtruck;
import org.example.model.UserSession;
import org.example.repository.FoodtruckRepository;
import org.example.utils.SceneManager;
import org.example.utils.TempHolder;

public class OwnerFoodtruckFormController {

    @FXML private TextField txtNom;
    @FXML private TextField txtType;
    @FXML private TextField txtPhone;
    @FXML private TextArea txtDescription;
    @FXML private TextField txtLat;
    @FXML private TextField txtLon;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final FoodtruckRepository repo = new FoodtruckRepository();
    private Foodtruck current;

    @FXML
    private void initialize() {
        current = TempHolder.getSelectedFoodtruck();

        if (current != null) {
            txtNom.setText(current.getNom());
            txtType.setText(current.getType());
            txtPhone.setText(current.getTelephone());
            txtDescription.setText(current.getDescription());
            txtLat.setText(String.valueOf(current.getLatitude()));
            txtLon.setText(String.valueOf(current.getLongitude()));
        }

        btnSave.setOnAction(e -> save());
        btnCancel.setOnAction(e -> SceneManager.switchTo("/org/example/view/OwnerDashboard.fxml"));
    }

    private void save() {
        String nom = txtNom.getText().trim();
        String type = txtType.getText().trim();
        String phone = txtPhone.getText().trim();
        String desc = txtDescription.getText().trim();
        double lat = 0, lon = 0;

        try {
            if (!txtLat.getText().isEmpty()) lat = Double.parseDouble(txtLat.getText());
            if (!txtLon.getText().isEmpty()) lon = Double.parseDouble(txtLon.getText());
        } catch (NumberFormatException ex) {
            alert("Latitude/Longitude invalides");
            return;
        }

        if (nom.isEmpty()) { alert("Nom requis"); return; }

        boolean ok;
        if (current == null) {
            int ownerId = UserSession.getUserId();
            ok = repo.createFoodtruck(ownerId, nom, type, phone, desc, lat, lon);
        } else {
            ok = repo.updateFoodtruck(current.getId(), nom, type, phone, desc, lat, lon);
        }

        if (!ok) { alert("Erreur en base"); return; }

        SceneManager.switchTo("/org/example/view/OwnerDashboard.fxml");
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
