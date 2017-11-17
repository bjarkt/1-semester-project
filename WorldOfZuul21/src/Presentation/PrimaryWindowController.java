package Presentation;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.*;


public class PrimaryWindowController {

    @FXML private GridPane minimapGrid;
    @FXML private Button northButton;
    @FXML private Button westButton;
    @FXML private Button eastButton;
    @FXML private Button southButton;
    @FXML private ImageView groundImageView;
    @FXML private TextArea textArea;
    @FXML private ListView inventoryListView;
    @FXML private ListView lootListView;


    private HashMap<Point2D, Pane> paneMap;
    private HashMap<Point2D, Image> boardBackgroundMap;
    //private Player player;
    //private Enemy enemy;

    final private boolean DRAW_MINIMAP_IMAGES = false;


    public PrimaryWindowController() {
        this.paneMap = new HashMap<>();
        this.boardBackgroundMap = new HashMap<>();
    }

    public void initialize() {
        initMinimapGrid();
        initGroundImageView();
        initButtons();
        initLists();

    }

    public void initLists() {
        /*ObservableList<Item> inventoryObservList = inventoryListView.getItems();
        inventoryObservList.add(new Item("Key"));
        inventoryObservList.add(new Item("Diamond"));

        ObservableList<Item> lootObservList = lootListView.getItems();
        lootObservList.add(new Item("Painting"));*/
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
        //player = new Player(maxCol, maxRow);
        //enemy = new Enemy(maxCol, maxRow);
        List<Integer> stupidList = new ArrayList<>();
        Collections.addAll(stupidList, 15, 16, 17, 18, 19, 10, 11, 12, 13, 14, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4);

        int i = 0;
        for (int r = 0; r < maxRow; r++) {
            for (int c = 0; c < maxCol; c++, i++) {
                Pane p = new Pane();
                String s = stupidList.get(i)+"";
                if (s.length() == 1) s = "0"+stupidList.get(i);
                Text text = new Text(4, 20, s);
                int rowIndex = (maxRow - r) - 1;

                p.setOnMouseClicked(this::handleMinimapPaneClick);
                p.getChildren().add(text);
                minimapGrid.add(p, c, (maxRow - rowIndex) - 1);
                paneMap.put(new Point2D(c, rowIndex), p);
            }
        }


        for (Map.Entry<Point2D, Pane> entry : paneMap.entrySet()) {
            String s;
            if (entry.getKey().getX() == 4) {
                if (entry.getKey().getY() == 0) {
                    s = "Pictures/bottomRight.png";
                    boardBackgroundMap.put(entry.getKey(), new Image(getClass().getResourceAsStream(s)));
                    setBackgroundImageForNode(paneMap.get(entry.getKey()), s);
                } else if (entry.getKey().getY() == 1 || entry.getKey().getY() == 2) {
                    s = "Pictures/middleLeft.png";
                    boardBackgroundMap.put(entry.getKey(), new Image(getClass().getResourceAsStream(s)));
                    setBackgroundImageForNode(paneMap.get(entry.getKey()), s);
                }else if (entry.getKey().getY() == 3) {
                    s = "Pictures/topRight.png";
                    boardBackgroundMap.put(entry.getKey(), new Image(getClass().getResourceAsStream(s)));
                    setBackgroundImageForNode(paneMap.get(entry.getKey()), s);
                }
            }
            else if (entry.getKey().getX() == 3 || entry.getKey().getX() == 2 || entry.getKey().getX() == 1) {
                if (entry.getKey().getY() == 0) {
                    s = "Pictures/bottomMiddle.png";
                    boardBackgroundMap.put(entry.getKey(), new Image(getClass().getResourceAsStream(s)));
                    setBackgroundImageForNode(paneMap.get(entry.getKey()), s);
                } else if (entry.getKey().getY() == 1 || entry.getKey().getY() == 2) {
                    s = "Pictures/middle.jpg";
                    boardBackgroundMap.put(entry.getKey(), new Image(getClass().getResourceAsStream(s)));
                    setBackgroundImageForNode(paneMap.get(entry.getKey()), s);
                } else if (entry.getKey().getY() == 3) {
                    s = "Pictures/topMiddle.png";
                    boardBackgroundMap.put(entry.getKey(), new Image(getClass().getResourceAsStream(s)));
                    setBackgroundImageForNode(paneMap.get(entry.getKey()), s);
                }
            }
            if (entry.getKey().getX() == 0) {
                if (entry.getKey().getY() == 0) {
                    s = "Pictures/bottomLeft.png";
                    boardBackgroundMap.put(entry.getKey(), new Image(getClass().getResourceAsStream(s)));
                    setBackgroundImageForNode(paneMap.get(entry.getKey()), s);
                } else if (entry.getKey().getY() == 1 || entry.getKey().getY() == 2) {
                    s = "Pictures/middleRight.png";
                    boardBackgroundMap.put(entry.getKey(), new Image(getClass().getResourceAsStream(s)));
                    setBackgroundImageForNode(paneMap.get(entry.getKey()), s);
                }else if (entry.getKey().getY() == 3) {
                    s = "Pictures/topLeft.png";
                    boardBackgroundMap.put(entry.getKey(), new Image(getClass().getResourceAsStream(s)));
                    setBackgroundImageForNode(paneMap.get(entry.getKey()), s);
                }
            }
        }

        update();
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
                if (children.get(i) instanceof Rectangle) {
                    children.remove(children.get(i));
                }
            }
        }

        /*groundImageView.setImage(boardBackgroundMap.get(player.getPos()));

        enemy.randomWalk();
        paneMap.get(player.getPos()).getChildren().add(player.getMapDisplay());
        paneMap.get(enemy.getPos()).getChildren().add(enemy.getMapDisplay());*/
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
        println("Hey hello, Mastermind Daniel here.");
        println("Please do some stuff.");
    }

    public void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) {
            case W: case UP: goNorth(); break;
            case S: case DOWN: goSouth(); break;
            case D: case RIGHT: goEast(); break;
            case A: case LEFT: goWest(); break;
        }
    }

    public void handleMenuItemSaveAction(ActionEvent e) {
        ButtonType buttonType = createAlert(Alert.AlertType.CONFIRMATION,"Save and exit?", "","Are you sure you want to save the game and exit?");
        if (buttonType == ButtonType.OK) {
            System.out.println("saved the game");
            Platform.exit();
        }
    }

    public void handleMenuItemCloseAction(ActionEvent e) {
        ButtonType buttonType = createAlert(Alert.AlertType.CONFIRMATION,"Exit the game?", "","Are you sure you want to exit the game without\nsaving?");
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (header.length() > 0) alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(null);
    }

    private void goNorth() {
        //player.goNorth();
        update();
    }
    private void goSouth() {
        //player.goSouth();
        update();
    }
    private void goEast() {
        //player.goEast();
        update();
    }
    private void goWest() {
        //player.goWest();
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
        textArea.appendText(text+"\n");
    }
    private void clear() {
        textArea.clear();
    }
}
