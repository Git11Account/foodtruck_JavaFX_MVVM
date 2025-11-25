package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.utils.SceneManager;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.setStage(stage);
        SceneManager.switchTo("/org/example/view/LoginView.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}
