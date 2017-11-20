package Presentation;

import Acq.IBusiness;
import Acq.IData;
import Acq.IUI;
import Business.BusinessFacade;
import Data.DataFacade;
import Data.XMLUtilities;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/startWindow.fxml"));
        VBox root = loader.load();
        Scene startWindowScene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

        IData data = new DataFacade();

        IBusiness businessFacade = new BusinessFacade();
        businessFacade.injectData(data);

        IUI controller = (IUI)loader.getController();
        controller.injectBusiness(businessFacade);

        primaryStage.setTitle("Night at the Museum - GUI test");
        primaryStage.setScene(startWindowScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}