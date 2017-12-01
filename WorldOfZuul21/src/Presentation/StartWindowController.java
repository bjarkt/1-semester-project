package Presentation;

import Acq.IBusiness;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class StartWindowController implements Initializable {
    @FXML private Button loadButton;
    @FXML private Button newGameButton;
    @FXML private ImageView imageView;

    private IBusiness business;
    private boolean nameLoaded;

    @FXML
    private void handleLoadButtonAction(ActionEvent e) {
        if (business.doesGameSaveFileExist()) {
            nameLoaded = true;
            business.load();
            changeScene();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Game save file not found. Start new game?");
            alert.setTitle("File not found");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    nameLoaded = false;
                    changeScene();
                }
            }
        }
    }

    @FXML
    private void handleNewGameButtonAction(ActionEvent e) {
        nameLoaded = false;
        changeScene();
    }

    private void changeScene() {
        Scene scene = loadButton.getScene();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/primaryWindow.fxml"));
            //IUI controller = (IUI)loader.getController();
            PrimaryWindowController controller = new PrimaryWindowController(nameLoaded);
            controller.injectBusiness(this.business);
            loader.setController(controller);
            Parent root = loader.load();

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

    void injectBusiness(IBusiness business) {
        this.business = business;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
