package Presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox root = FXMLLoader.load(getClass().getResource("FXML/startWindow.fxml"));
        Scene startWindowScene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

        primaryStage.setTitle("Night at the Museum - GUI test");
        primaryStage.setScene(startWindowScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}