package Presentation;


import Acq.IHighScore;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class AlertBox {

    static void display(String header, String textfilename) {
        StringBuilder text = new StringBuilder();
        Stage window = new Stage();
        window.setTitle(header);
        window.setMinWidth(400);
        window.setMaxWidth(600);
        Scanner input;

        File directory = new File("");
        String realFilePath;
        if (directory.getAbsolutePath().contains("WorldOfZuul21")) {
            realFilePath = directory.getAbsolutePath() + "/src/Presentation/Textfiles/" + textfilename;
        } else {
            realFilePath = directory.getAbsolutePath() + "/WorldOfZuul21/src/Presentation/Textfiles/" + textfilename;
        }
        File file = new File(realFilePath);

        try {
            input = new Scanner(file);
            while (input.hasNext()) {
                text.append(input.nextLine()).append(System.lineSeparator());
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error: File at " + realFilePath + " not found.");
        }
        Label label = new Label();
        label.setText(text.toString());
        label.setWrapText(true);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    static void display(String header, List<IHighScore> highScore ) {
        Stage window = new Stage();
        window.setTitle(header);
        window.setMinWidth(400);
        window.setMaxWidth(600);
        
        ListView<IHighScore> highscore = new ListView<>();
        ObservableList<IHighScore> highscorelist = highscore.getItems();        
        highscorelist.addAll(highScore);
       

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(highscore, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
