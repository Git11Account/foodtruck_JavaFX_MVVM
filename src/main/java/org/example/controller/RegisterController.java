package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.utils.SceneManager;
import org.example.viewmodel.RegisterViewModel;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button registerButton;
    @FXML private Button backToLoginButton;

    private final RegisterViewModel viewModel = new RegisterViewModel();

    @FXML
    private void initialize() {

        viewModel.usernameProperty().bind(usernameField.textProperty());
        viewModel.passwordProperty().bind(passwordField.textProperty());

        registerButton.setOnAction(e -> {
            boolean ok = viewModel.register();

            if (ok) {
                showAlert("Compte créé !");
                SceneManager.switchTo("/org/example/view/LoginView.fxml");
            } else {
                showAlert("Erreur : nom déjà utilisé");
            }
        });

        backToLoginButton.setOnAction(e ->
                SceneManager.switchTo("/org/example/view/LoginView.fxml")
        );
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
