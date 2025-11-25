package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.model.UserSession;
import org.example.model.OwnerRequest;
import org.example.repository.OwnerRequestDAO;
import org.example.utils.SceneManager;

public class RequestOwnerController {

    @FXML private TextField nomField;
    @FXML private TextArea descField;
    @FXML private TextField latField;
    @FXML private TextField lonField;
    @FXML private Button chooseLocationButton;
    @FXML private Button sendButton;

    private static double selectedLat = 0;
    private static double selectedLon = 0;

    public static void setSelectedCoords(double lat, double lon) {
        selectedLat = lat;
        selectedLon = lon;
    }

    @FXML
    private void initialize() {

        // Réinjecter les coordonnées si on revient de la carte
        if (selectedLat != 0 && selectedLon != 0) {
            latField.setText(String.valueOf(selectedLat));
            lonField.setText(String.valueOf(selectedLon));
        }

        chooseLocationButton.setOnAction(e ->
                SceneManager.switchTo("/org/example/view/ChooseLocationView.fxml")
        );

        sendButton.setOnAction(e -> sendRequest());
    }

    private void sendRequest() {

        // Injecter les coordonnées dans les champs cachés
        latField.setText(String.valueOf(selectedLat));
        lonField.setText(String.valueOf(selectedLon));

        String nom = nomField.getText();
        String desc = descField.getText();

        if (nom.isEmpty() || desc.isEmpty()) {
            alert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        if (selectedLat == 0 || selectedLon == 0) {
            alert("Erreur", "Vous devez choisir un emplacement sur la carte.");
            return;
        }

        OwnerRequestDAO dao = new OwnerRequestDAO();
        dao.createRequest(UserSession.getUserId(), nom, desc, selectedLat, selectedLon);

        alert("Succès", "Votre demande a été envoyée !");
        SceneManager.switchTo("/org/example/view/MapView.fxml");
    }


    private void alert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
