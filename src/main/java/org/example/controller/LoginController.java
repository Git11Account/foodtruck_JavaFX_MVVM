package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.model.User;
import org.example.model.UserSession;
import org.example.utils.SceneManager;
import org.example.viewmodel.LoginViewModel;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button goRegisterButton;

    private final LoginViewModel viewModel = new LoginViewModel();

    @FXML
    private void initialize() {

        // Bind
        viewModel.usernameProperty().bind(usernameField.textProperty());
        viewModel.passwordProperty().bind(passwordField.textProperty());

        loginButton.setOnAction(e -> {

            System.out.println("*************************************************************************************");

            // *** NOUVEAU LOGIN CORRECT ***
            User user = viewModel.loginUser();

            if (user == null) {
                showAlert("Nom d'utilisateur ou mot de passe incorrect.");
                return;
            }

            UserSession.startSession(user.getId(), user.getUsername(), user.getRole());

            switch (user.getRole()) {
                case "ADMIN" -> SceneManager.switchTo("/org/example/view/AdminDashboard.fxml");
                case "OWNER" -> {
                    SceneManager.switchTo("/org/example/view/OwnerDashboard.fxml");
                    org.example.controller.OwnerDashboardController ctrl = SceneManager.getController();
                    if (ctrl != null) ctrl.setOwnerId(UserSession.getUserId());
                }
                default -> SceneManager.switchTo("/org/example/view/MapView.fxml");
            }

        });

        goRegisterButton.setOnAction(e ->
                SceneManager.switchTo("/org/example/view/RegisterView.fxml")
        );
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
