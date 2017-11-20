package Presentation;


import Acq.IHighScore;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class HelpAlertBox {

    public static void display(String header, String textfilename) {
        String text = "";
        Stage window = new Stage();
        window.setTitle(header);
        window.setMinWidth(400);
        window.setMaxWidth(600);
        Scanner input = null;
        File file = new File(textfilename);
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HelpAlertBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (input.hasNext()) {
            text += input.nextLine() + System.lineSeparator() ;
        }
        Label label = new Label();
        label.setText(text);
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
    public static void display(String header, List<IHighScore> highScore ) {
        String text = "";
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
