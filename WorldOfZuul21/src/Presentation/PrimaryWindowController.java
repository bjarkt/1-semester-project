package Presentation;

import Acq.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;


public class PrimaryWindowController implements IUI, Initializable {

    @FXML private GridPane minimapGrid;
    @FXML private Button northButton;
    @FXML private Button westButton;
    @FXML private Button eastButton;
    @FXML private Button southButton;
    @FXML private ImageView groundImageView;
    @FXML private TextArea textArea;
    @FXML private ListView<IItem> inventoryListView;
    @FXML private ListView<IItem> lootListView;

    private IBusiness business;
    private String playerName;


    private HashMap<Point2D, Pane> paneMap;
    private HashMap<Point2D, Image> boardBackgroundMap;
    //private Player player;
    //private Enemy enemy;

    final private boolean DRAW_MINIMAP_IMAGES = true;


    public PrimaryWindowController() {
        this.paneMap = new HashMap<>();
        this.boardBackgroundMap = new HashMap<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMinimapGrid();
        initGroundImageView();
        initButtons();
        update();

        TextInputDialog t = new TextInputDialog();
        t.setTitle("Enter name");
        t.setHeaderText("Enter your name");
        Optional<String> s = t.showAndWait();
        if (s.isPresent()) {
            playerName = s.get();
        }
    }

    private void initButtons() {
        HashMap<Button, Image> btnMap = new HashMap<>();
        btnMap.put(northButton, new Image(getClass().getResourceAsStream("Pictures/north2.png")));
        btnMap.put(southButton, new Image(getClass().getResourceAsStream("Pictures/south2.png")));
        btnMap.put(eastButton, new Image(getClass().getResourceAsStream("Pictures/east2.png")));
        btnMap.put(westButton, new Image(getClass().getResourceAsStream("Pictures/west2.png")));

        for (Map.Entry<Button, Image> entry : btnMap.entrySet()) {
            entry.getKey().setText("");
            entry.getKey().setGraphic(new ImageView(entry.getValue()));
        }
    }

    private void initGroundImageView() {
        Pane parentOfImageView = (Pane) groundImageView.getParent();
        groundImageView.setPreserveRatio(false);
        groundImageView.fitWidthProperty().bind(parentOfImageView.widthProperty());
        groundImageView.fitHeightProperty().bind(parentOfImageView.heightProperty());

    }

    private void initMinimapGrid() {
        int maxCol = getColCount(minimapGrid);
        int maxRow = getRowCount(minimapGrid);
        List<Integer> stupidList = new ArrayList<>();
        Collections.addAll(stupidList, 15, 16, 17, 18, 19, 10, 11, 12, 13, 14, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4);

        int i = 0;
        for (int r = 0; r < maxRow; r++) {
            for (int c = 0; c < maxCol; c++, i++) {
                Pane p = new Pane();
                String s = stupidList.get(i) + "";
                if (s.length() == 1) s = "0" + stupidList.get(i);
                Text text = new Text(4, 20, s);
                int rowIndex = (maxRow - r) - 1;

                p.setOnMouseClicked(this::handleMinimapPaneClick);
                p.getChildren().add(text);
                minimapGrid.add(p, c, (maxRow - rowIndex) - 1);
                paneMap.put(new Point2D(c, rowIndex), p);
            }
        }

        for (Map.Entry<Integer, IRoom> entry : business.getRooms().entrySet()) {
            String filename = "Pictures/" + entry.getValue().getVisualDescription() + ".png";
            boardBackgroundMap.put(locationToPoint(entry.getValue().getLocation()), new Image(getClass().getResourceAsStream(filename)));
        }
    }

    private void setBackgroundImageForNode(Node node, String s) {
        if (DRAW_MINIMAP_IMAGES) {
            node.setStyle("-fx-background-image: url('/sample/" + s + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;" +
                    "-fx-background-position: center center;");
        }
    }

    private void update() {
        for (Pane pane : paneMap.values()) {
            ObservableList<Node> children = pane.getChildren();
            for (int i = 0; i < children.size(); i++) {
                // Bug i JDK, kan ikke clear alle shapes pålideligt
                // https://bugs.openjdk.java.net/browse/JDK-8087752

                // Vi ved at texten er på position 0, så hent teksten, før der bliver clearet.
                Text text = (Text) children.get(0);
                if (children.get(i) instanceof Rectangle) {
                    children.clear();
                    children.add(text);
                }
            }
        }


        // TODO
        // flyt Rectangle r ... kode over i en anden klasse, f.x. guard og player
        Rectangle r = new Rectangle(0, 25, 10, 10);
        r.setFill(Color.GREEN);
        paneMap.get(locationToPoint(business.getCurrentLocation())).getChildren().add(r);

        // TODO
        // gør det samme hers
        Rectangle r1 = new Rectangle(15, 25, 10, 10);
        r1.setFill(Color.RED);
        paneMap.get(locationToPoint(business.getGuardLocations()[0])).getChildren().add(r1);
        Rectangle r2 = new Rectangle(30, 25, 10, 10);
        r2.setFill(Color.RED);
        paneMap.get(locationToPoint(business.getGuardLocations()[1])).getChildren().add(r2);

        groundImageView.setImage(boardBackgroundMap.get(locationToPoint(business.getCurrentLocation())));

        //paneMap.get(player.getPos()).getChildren().add(player.getMapDisplay());
        //paneMap.get(enemy.getPos()).getChildren().add(enemy.getMapDisplay());*/
    }

    private void updateInventoryList() {
        ObservableList<IItem> inventoryObservList = inventoryListView.getItems();
        inventoryObservList.clear();
        inventoryObservList.addAll(business.getInventoryList());
    }

    public void handleNorthButton(ActionEvent e) {
        goNorth();
    }

    public void handleSouthButton(ActionEvent e) {
        goSouth();
    }

    public void handleEastButton(ActionEvent e) {
        goEast();
    }

    public void handleWestButton(ActionEvent e) {
        goWest();
    }

    public void handleCallButtonAction(ActionEvent e) {
        println(business.callFriendlyNpc());
    }

    public void handleStealButtonAction(ActionEvent e) {
        business.steal();
        updateInventoryList();
    }

    public void handleInteractButtonAction(ActionEvent e) {
        business.interact();
    }

    public void handleHideButtonAction(ActionEvent e) {
        business.hide();
        update();
    }

    public void handleEscapeButtonAction(ActionEvent e) {
        if (business.isAtEntrance()) {
            ButtonType choice = createAlert(Alert.AlertType.CONFIRMATION, "Escape", "", "Do you want to go back inside?");
            if (choice == ButtonType.OK) {
                business.escape(true);
            } else {
                business.escape(false);
                business.updateHighScore(playerName);
                // TODO
                // vis highscores på en bedre måde?
                println("-- HIGHSCORES --");
                for (IHighScore iHighScore : business.getHighScores()) {
                    println(iHighScore.getName() + " - " + iHighScore.getScore());
                }
                ButtonType exitGame = createAlert(Alert.AlertType.WARNING, "Quit the game now", "", "The game will now exit!");
                if (exitGame == ButtonType.OK) {
                    Platform.exit();
                }
            }
        }
    }

    public void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) {
            case W:
            case UP:
                goNorth();
                break;
            case S:
            case DOWN:
                goSouth();
                break;
            case D:
            case RIGHT:
                goEast();
                break;
            case A:
            case LEFT:
                goWest();
                break;
        }
    }

    public void handleMenuItemSaveAction(ActionEvent e) {
        ButtonType buttonType = createAlert(Alert.AlertType.CONFIRMATION, "Save and exit?", "", "Are you sure you want to save the game and exit?");
        if (buttonType == ButtonType.OK) {
            System.out.println("saved the game");
            Platform.exit();
        }
    }

    public void handleMenuItemCloseAction(ActionEvent e) {
        ButtonType buttonType = createAlert(Alert.AlertType.CONFIRMATION, "Exit the game?", "", "Are you sure you want to exit the game without\nsaving?");
        if (buttonType == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void handleMenuItemHelpAction(ActionEvent e) {
        createAlert(Alert.AlertType.INFORMATION, "Information about Night at the Museum",
                "This is some help text.",
                "Her kunne man f.eks. skrive hvad spillet går ud på,\nda kommandoerne allerede står på knapperne.");
    }

    private ButtonType createAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        if (header.length() > 0) alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(null);
    }

    private void goNorth() {
        business.goDirection(Direction.NORTH);
        update();
    }

    private void goSouth() {
        business.goDirection(Direction.SOUTH);
        update();
    }

    private void goEast() {
        business.goDirection(Direction.EAST);
        update();
    }

    private void goWest() {
        business.goDirection(Direction.WEST);
        update();
    }

    public void handleMinimapPaneClick(MouseEvent e) {
        Pane clicked = ((Pane) e.getSource());
        for (Map.Entry<Point2D, Pane> entry : paneMap.entrySet()) {
            if (entry.getValue() == clicked) {
                System.out.println(entry.getKey());
            }
        }
    }

    private int getColCount(GridPane pane) {
        int numCols = pane.getColumnConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer colIndex = GridPane.getColumnIndex(child);
                if (colIndex != null) {
                    numCols = Math.max(numCols, colIndex + 1);
                }
            }
        }
        return numCols;
    }

    private int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if (rowIndex != null) {
                    numRows = Math.max(numRows, rowIndex + 1);
                }
            }
        }
        return numRows;
    }

    private void println(String text) {
        textArea.appendText(text + "\n");
    }

    private void clear() {
        textArea.clear();
    }

    @Override
    public void injectBusiness(IBusiness business) {
        this.business = business;
    }

    private Point2D locationToPoint(ILocation loc) {
        return new Point2D(loc.getX(), loc.getY());
    }


}
