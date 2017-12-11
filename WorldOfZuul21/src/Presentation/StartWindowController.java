package Presentation;

import Acq.IBusiness;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Optional;

public class StartWindowController {
    @FXML private Button loadButton;
    @FXML private Button newGameButton;
    @FXML private ImageView imageView;

    private IBusiness business;
    private boolean nameLoaded;

    /**
     * Handle action on the load button
     * Attempt to load the game. If no save game file is found, start a new game
     *
     * @param e Action event
     */
    @FXML
    private void handleLoadButtonAction(ActionEvent e) {
        if (business.doesGameSaveFileExist()) {
            nameLoaded = true;
            business.load();
            changeScene();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Game save file not found. Start new game?");
            alert.setHeaderText("");
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

    /**
     * Handle action on the new game button
     *
     * @param e Action event
     */
    @FXML
    private void handleNewGameButtonAction(ActionEvent e) {
        nameLoaded = false;
        business.deleteSaveGameFile();
        changeScene();
    }

    /**
     * Changes the scene to the one defined in primaryWindow.fxml
     */
    private void changeScene() {
        // get the scene of this window
        Scene scene = loadButton.getScene();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/primaryWindow.fxml"));

            // create new controller
            PrimaryWindowController controller = new PrimaryWindowController(nameLoaded);
            controller.injectBusiness(this.business);

            // set the controller for the loader
            loader.setController(controller);
            Parent root = loader.load();

            scene.setRoot(root);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Make the imageview resizable.
     */
    @FXML
    public void initialize() {
        Pane parentOfImageView = (Pane) imageView.getParent();
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(parentOfImageView.widthProperty());
        imageView.fitHeightProperty().bind(parentOfImageView.heightProperty());
    }

    /**
     * Injects the business facade
     *
     * @param business business facade
     */
    void injectBusiness(IBusiness business) {
        this.business = business;
    }

}
