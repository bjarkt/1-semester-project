package Presentation;

import Acq.IBusiness;
import Acq.IUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI extends Application implements IUI {

    private static IBusiness business;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/startWindow.fxml"));
        VBox root = loader.load();
        Scene startWindowScene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

        StartWindowController startWindowController = loader.getController();
        startWindowController.injectBusiness(business);

        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(450);
        primaryStage.setTitle("Night at the Museum");
        primaryStage.setScene(startWindowScene);
        primaryStage.show();
    }

    @Override
    public void injectBusiness(IBusiness businessFacade) {
        business = businessFacade;
    }

    @Override
    public void startApplication(String[] args) {
        launch(args);
    }


}
