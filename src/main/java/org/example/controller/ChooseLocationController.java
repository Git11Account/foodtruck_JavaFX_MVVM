package org.example.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.example.utils.SceneManager;

public class ChooseLocationController {

    @FXML
    private WebView mapWebView;

    @FXML
    private void initialize() {

        WebEngine engine = mapWebView.getEngine();
        engine.load(getClass().getResource("/map/choose_location.html").toExternalForm());

        // Attendre que le HTML soit complètement chargé
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {

                System.out.println("Carte chargée, injection de javaApp...");

                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("javaApp", this);

                System.out.println("javaApp est maintenant accessible depuis JavaScript.");
            }
        });
    }

    // Appelée depuis JavaScript : javaApp.setLocation(lat, lon)
    public void setLocation(double lat, double lon) {
        System.out.println("Coordonnées sélectionnées : " + lat + " / " + lon);

        RequestOwnerController.setSelectedCoords(lat, lon);

        // Retourner automatiquement au formulaire
        SceneManager.switchTo("/org/example/view/RequestOwnerView.fxml");
    }
}
