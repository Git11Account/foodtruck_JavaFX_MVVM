package org.example.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage stage;
    private static FXMLLoader lastLoader;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void switchTo(String fxmlPath) {
        try {
            lastLoader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = lastLoader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("➡ Chargement FXML : " + fxmlPath);
            lastLoader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = lastLoader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getController() {
        if (lastLoader == null) return null;
        return lastLoader.getController();
    }
}
