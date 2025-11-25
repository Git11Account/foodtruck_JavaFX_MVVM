package org.example.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.model.Foodtruck;
import org.example.model.UserSession;
import org.example.utils.SceneManager;
import org.example.utils.TempHolder;
import org.example.viewmodel.OwnerDashboardViewModel;

public class OwnerDashboardController {

    @FXML private ListView<Foodtruck> foodtruckList;
    @FXML private Button btnNew;
    @FXML private Button btnEdit;
    @FXML private Button btnDisponibilites;
    @FXML private Label lblStatus;
    @FXML private Button btnBecomeOwner;


    private final OwnerDashboardViewModel viewModel = new OwnerDashboardViewModel();
    private int currentOwnerId;


    @FXML
    private void debug() {
        System.out.println("FXML LOADED - Buttons:");
        System.out.println("btnNew = " + btnNew);
        System.out.println("btnEdit = " + btnEdit);
        System.out.println("btnDisponibilites = " + btnDisponibilites);
        System.out.println("btnBecomeOwner = " + btnBecomeOwner);
    }


    @FXML
    private void initialize() {

        // Empêcher un USER d'utiliser ce panneau
        if (!UserSession.isOwner()) {
            disableOwnerFeatures();
        }

        // Style des items
        foodtruckList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Foodtruck ft, boolean empty) {
                super.updateItem(ft, empty);

                if (empty || ft == null) {
                    setText(null);
                } else {
                    setText(ft.getNom() + "   [" + ft.getStatus() + "]");
                }
            }
        });

        // Ajouter un foodtruck
        btnNew.setOnAction(e -> {
            TempHolder.setSelectedFoodtruck(null);
            SceneManager.switchTo("/org/example/view/OwnerFoodtruckForm.fxml");
        });

        // Modifier foodtruck
        btnEdit.setOnAction(e -> {
            Foodtruck selected = foodtruckList.getSelectionModel().getSelectedItem();
            if (selected == null) {
                alert("Sélectionne un foodtruck !");
                return;
            }

            TempHolder.setSelectedFoodtruck(selected);
            SceneManager.switchTo("/org/example/view/OwnerFoodtruckForm.fxml");
        });

        // Disponibilités
        btnDisponibilites.setOnAction(e -> {
            Foodtruck selected = foodtruckList.getSelectionModel().getSelectedItem();
            if (selected == null) {
                alert("Sélectionne un foodtruck !");
                return;
            }

            TempHolder.setSelectedFoodtruck(selected);
            SceneManager.switchTo("/org/example/view/OwnerDisponibilites.fxml");
        });

        // Devenir propriétaire
        btnBecomeOwner.setOnAction(e -> {
            System.out.println("DEBUG: Clic sur Devenir propriétaire !");
            SceneManager.switchTo("/org/example/view/RequestOwnerView.fxml");
        });

    }

    /** Appelé après le login */
    public void setOwnerId(int ownerId) {
        System.out.println("➡ Chargement du propriétaire ID = " + ownerId);

        this.currentOwnerId = ownerId;
        viewModel.loadMyFoodtrucks(ownerId);

        foodtruckList.setItems(viewModel.getMyFoodtrucks());
    }

    /** Bloquer accès aux users non OWNER */
    private void disableOwnerFeatures() {
        btnNew.setDisable(true);
        btnEdit.setDisable(true);
        btnDisponibilites.setDisable(true);
        lblStatus.setText("Accès limité — Vous n'êtes pas propriétaire.");
    }

    private void alert(String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText(msg);
            a.show();
        });
    }
}
