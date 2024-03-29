package com.stocha.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Model extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("IHMStocha.fxml"));
        primaryStage.setTitle("Stocha Application");
        primaryStage.setScene(new Scene(root, 1366, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
