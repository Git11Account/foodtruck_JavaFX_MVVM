package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.model.UserSession;
import org.example.utils.SceneManager;

public class TopMenuController {

    @FXML
    private Button btnLogout;

    @FXML
    private void initialize() {
        btnLogout.setOnAction(e -> {
            UserSession.logout();
            SceneManager.switchTo("/org/example/view/LoginView.fxml");
        });
    }
}
