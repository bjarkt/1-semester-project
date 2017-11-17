package Presentation;

import Acq.IBusiness;
import Acq.IUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class StartWindowController implements IUI {
    @FXML private Button loadButton;
    @FXML private Button newGameButton;
    @FXML private ImageView imageView;

    private IBusiness business;

    @FXML
    private void handleLoadButtonAction(ActionEvent e) {
        System.out.println("handle load stuff");
        changeScene();
    }

    @FXML
    private void handleNewGameButtonAction(ActionEvent e) {
        changeScene();
    }

    private void changeScene() {
        Scene scene = loadButton.getScene();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/primaryWindow.fxml"));
            Parent root = loader.load();
            IUI controller = (IUI)loader.getController();
            controller.injectBusiness(this.business);

            scene.setRoot(root);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        Pane parentOfImageView = (Pane) imageView.getParent();
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(parentOfImageView.widthProperty());
        imageView.fitHeightProperty().bind(parentOfImageView.heightProperty());
    }


    @Override
    public void injectBusiness(IBusiness business) {
        this.business = business;
    }
}
