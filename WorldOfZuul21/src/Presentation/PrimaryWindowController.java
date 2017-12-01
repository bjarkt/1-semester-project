package Presentation;

import Acq.*;
import Business.BusinessFacade;
import Presentation.Animation.LongValue;
import Presentation.Animation.Point2DProperty;
import Presentation.Animation.ResizableCanvas;
import Presentation.Animation.Sprite;
import Presentation.Drawables.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import java.net.URL;
import java.util.*;

public class PrimaryWindowController implements Initializable {

    @FXML
    private GridPane minimapGrid;
    @FXML
    private Button northButton;
    @FXML
    private Button westButton;
    @FXML
    private Button eastButton;
    @FXML
    private Button southButton;
    @FXML
    private ImageView groundImageView;
    @FXML
    private TextArea textArea;
    @FXML
    private ListView<IItem> inventoryListView;
    @FXML
    private ListView<IItem> lootListView;
    @FXML
    private StackPane stackPane;
    @FXML
    private VBox rootVBox;

    private Sprite sPlayer;
    private Point2DProperty playerStartPos;

    private List<Sprite> doors;
    private Sprite northDoor;
    private Point2DProperty northDoorPos;
    private Sprite southDoor;
    private Point2DProperty southDoorPos;
    private Sprite eastDoor;
    private Point2DProperty eastDoorPos;
    private Sprite westDoor;
    private Point2DProperty westDoorPos;

    private ResizableCanvas canvas;
    private List<String> inputs;

    private IBusiness business;
    private String playerName;
    private boolean nameLoaded;

    private boolean forcedToQuit;

    private HashMap<Point2D, Pane> paneMap;
    private HashMap<Point2D, Image> boardBackgroundMap;

    private Player player;
    private Guard[] guards;
    private PowerSwitch powerSwitch;
    private PowerRelay[] powerRelays;
    private Item item;
    private List<VisibleDrawable> visibleDrawables;

    public PrimaryWindowController(boolean nameLoaded) {
        this.paneMap = new HashMap<>();
        this.boardBackgroundMap = new HashMap<>();
        forcedToQuit = false;
        this.nameLoaded = nameLoaded;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMinimapGrid();
        initGroundImageView();
        initButtons();
        initDrawables();
        initSprites();
        initCanvas();

        inputs = new ArrayList<>();

        animateSprite();

        update();

        if (!nameLoaded) {
            TextInputDialog t = new TextInputDialog("Jeg er for doven til at skrive et rigtigt navn");
            t.setTitle("Enter name");
            t.setHeaderText("Enter your name");
            Optional<String> s = t.showAndWait();
            if (s.isPresent()) {
                if (s.get().contains(" ")) {
                    playerName = s.get().replace(" ", "-");
                } else {
                    playerName = s.get();
                }
            } else {
                Platform.exit();
            }
        } else {
            playerName = business.getLoadedPlayerName();
        }
    }

    private void initSprites() {
        sPlayer = new Sprite();
        //sPlayer.setImage("/Presentation/Pictures/spriteSheetTest3.png", 16, 4, 4, 64, 64, 1, 0, 2, 3);
        sPlayer.setImage("/Presentation/Pictures/spriteSheetTest4.png", 16, 4, 4, 48, 71, 3, 0, 2, 1);

        doors = new ArrayList<>();
        northDoor = new Sprite();
        northDoor.setImage("/Presentation/Pictures/door2.png");

        southDoor = new Sprite();
        southDoor.setImage("/Presentation/Pictures/door2.png");

        eastDoor = new Sprite();
        eastDoor.setImage("/Presentation/Pictures/door.png");

        westDoor = new Sprite();
        westDoor.setImage("/Presentation/Pictures/door.png");

        updateSpritePosition();
        Collections.addAll(doors, northDoor, southDoor, eastDoor, westDoor);
    }

    public void updateSpritePosition() {
        playerStartPos = new Point2DProperty();
        if (stackPane.getWidth() == 0) {
            // hvis stackpane ikke har fået en bredde, skal spilleren placeres på (200, 200)
            // ellers bliver han placeret oven på døren, og går i gennem den, og ender i fx. rum 15, eller bliver busted
            playerStartPos = new Point2DProperty(200, 200);
        } else {
            playerStartPos.propertyXProperty().bind(stackPane.widthProperty().divide(2).subtract(sPlayer.getWidth() / 2));
            playerStartPos.propertyYProperty().bind(stackPane.heightProperty().divide(2).subtract(sPlayer.getHeight() / 2));
        }
        setPlayerStartPos();

        northDoorPos = new Point2DProperty();
        northDoorPos.propertyXProperty().bind(stackPane.widthProperty().divide(2).subtract(northDoor.getWidth() / 2));
        northDoorPos.setPropertyY(0);
        northDoor.setPosition(northDoorPos.getPropertyX(), northDoorPos.getPropertyY());

        southDoorPos = new Point2DProperty();
        southDoorPos.propertyXProperty().bind(stackPane.widthProperty().divide(2).subtract(southDoor.getWidth() / 2));
        southDoorPos.propertyYProperty().bind(stackPane.heightProperty().subtract(20));
        southDoor.setPosition(southDoorPos.getPropertyX(), southDoorPos.getPropertyY());

        eastDoorPos = new Point2DProperty();
        eastDoorPos.propertyXProperty().bind(stackPane.widthProperty().subtract(20));
        eastDoorPos.propertyYProperty().bind(stackPane.heightProperty().divide(2).subtract(eastDoor.getHeight() / 2));
        eastDoor.setPosition(eastDoorPos.getPropertyX(), eastDoorPos.getPropertyY());

        westDoorPos = new Point2DProperty();
        westDoorPos.setPropertyX(0);
        westDoorPos.propertyYProperty().bind(stackPane.heightProperty().divide(2).subtract(westDoor.getHeight() / 2));
        westDoor.setPosition(westDoorPos.getPropertyX(), westDoorPos.getPropertyY());
    }

    private void setPlayerStartPos() {
        sPlayer.setPosition(playerStartPos.getPropertyX(), playerStartPos.getPropertyY());
    }

    private void animateSprite() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        LongValue lastNanoTime = new LongValue(System.nanoTime());
        // taget fra https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development/blob/master/Example5.java
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                // logic
                int speed = 150;
                sPlayer.setVelocity(0, 0);
                if (inputs.contains("A")) {
                    sPlayer.addVelocity(-speed, 0);
                    sPlayer.getWestTimeline().play();
                }
                if (inputs.contains("D")) {
                    sPlayer.addVelocity(speed, 0);
                    sPlayer.getEastTimeline().play();
                }
                if (inputs.contains("W")) {
                    sPlayer.addVelocity(0, -speed);
                    sPlayer.getNorthTimeline().play();
                }
                if (inputs.contains("S")) {
                    sPlayer.addVelocity(0, speed);
                    sPlayer.getSouthTimeline().play();
                }
                if (!isSpriteOutsideRectangle(sPlayer, 0, 0, stackPane.getWidth(), stackPane.getHeight())) {
                    inputs.clear();
                    sPlayer.setVelocity(0, 0);
                    if (sPlayer.getPositionY() < stackPane.getHeight() / 2) {
                        sPlayer.setPosition(sPlayer.getPositionX(), sPlayer.getPositionY() + 1);
                    }
                    if (sPlayer.getPositionY() > stackPane.getHeight() / 2) {
                        sPlayer.setPosition(sPlayer.getPositionX(), sPlayer.getPositionY() - 1);
                    }
                    if (sPlayer.getPositionX() < stackPane.getWidth() / 2) {
                        sPlayer.setPosition(sPlayer.getPositionX() + 1, sPlayer.getPositionY());
                    }
                    if (sPlayer.getPositionX() > stackPane.getWidth() / 2) {
                        sPlayer.setPosition(sPlayer.getPositionX() - 1, sPlayer.getPositionY());
                    }
                }
                sPlayer.update(elapsedTime);

                // collision
                for (Sprite door : doors) {
                    if (sPlayer.intersects(door)) {
                        if (door == northDoor) {
                            if (inputs.contains("W")) {
                                goNorth();
                                setPlayerStartPos();
                            }
                        } else if (door == southDoor) {
                            goSouth();
                            setPlayerStartPos();
                        } else if (door == eastDoor) {
                            goEast();
                            setPlayerStartPos();
                        } else if (door == westDoor) {
                            goWest();
                            setPlayerStartPos();
                        }
                    }
                }

                // render
                gc.clearRect(0, 0, stackPane.getWidth(), stackPane.getHeight());

                for (Sprite door : doors) {
                    door.render(gc);
                }
                sPlayer.render(gc);
            }
        }.start();
    }

    private boolean isSpriteOutsideRectangle(Sprite sprite, double x1, double y1, double x2, double y2) {
        return sprite.getPositionX() > x1 && sprite.getPositionX() + sprite.getWidth() < x2
                && sprite.getPositionY() > y1 && sprite.getPositionY() + sprite.getHeight() < y2;
    }

    private void initCanvas() {
        canvas = new ResizableCanvas(this);
        canvas.widthProperty().bind(((Pane) groundImageView.getParent()).widthProperty());
        canvas.heightProperty().bind(((Pane) groundImageView.getParent()).heightProperty());
        canvas.prefWidth(((Pane) groundImageView.getParent()).getPrefWidth());
        canvas.prefHeight(((Pane) groundImageView.getParent()).getPrefHeight());

        stackPane.getChildren().add(canvas);
    }

    private void initDrawables() {
        visibleDrawables = new ArrayList<>();
        this.player = new Player();
        this.guards = new Guard[]{new Guard(Color.RED), new Guard(Color.RED)};
        this.powerSwitch = new PowerSwitch();

        ILocation[] powerRelayLocations = business.getPowerRelayLocations();
        this.powerRelays = new PowerRelay[powerRelayLocations.length];
        for (int i = 0; i < powerRelays.length; i++) {
            this.powerRelays[i] = new PowerRelay(locationToPoint(powerRelayLocations[i]));
        }
        this.item = new Item();

        visibleDrawables.add(powerSwitch);
        visibleDrawables.add(item);
        visibleDrawables.addAll(Arrays.asList(powerRelays));
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
                if (s.length() == 1) {
                    s = "0" + stupidList.get(i);
                }
                Text text = new Text(4, 20, s);
                int rowIndex = (maxRow - r) - 1;

                p.setOnMouseClicked(this::handleMinimapPaneClick);
                p.getChildren().add(text);
                minimapGrid.add(p, c, (maxRow - rowIndex) - 1);
                paneMap.put(new Point2D(c, rowIndex), p);
            }
        }

        initImages();
    }

    private void initImages() {
        for (IRoom room : business.getRooms()) {
            String filename = "Pictures/" + room.getVisualDescription() + ".png";
            boardBackgroundMap.put(locationToPoint(room.getLocation()), new Image(getClass().getResourceAsStream(filename)));
        }
    }

    public void positionExitsAndDisableButtons() {
        Set<Direction> exits = business.getExitsForCurrentRoom();
        Set<Button> buttons = new HashSet<>();
        Collections.addAll(buttons, northButton, southButton, eastButton, westButton);

        for (Sprite door : doors) {
            door.setPosition(-1000, -1000);
        }

        for (Button button : buttons) {
            button.setDisable(true);
        }

        if (exits.contains(Direction.NORTH)) {
            northDoor.setPosition(northDoorPos.getPropertyX(), northDoorPos.getPropertyY());
            northButton.setDisable(false);
        }
        if (exits.contains(Direction.SOUTH)) {
            southDoor.setPosition(southDoorPos.getPropertyX(), southDoorPos.getPropertyY());
            southButton.setDisable(false);
        }
        if (exits.contains(Direction.EAST)) {
            eastDoor.setPosition(eastDoorPos.getPropertyX(), eastDoorPos.getPropertyY());
            eastButton.setDisable(false);
        }
        if (exits.contains(Direction.WEST)) {
            westDoor.setPosition(westDoorPos.getPropertyX(), westDoorPos.getPropertyY());
            westButton.setDisable(false);
        }

    }

    private void update() {
        if (business.currentRoomContainsItem()) {
            item.setSeen(true);
        } else if (business.currentRoomContainsPowerRelay()) {
            for (PowerRelay relay : powerRelays) {
                if (relay.getLocation().equals(locationToPoint(business.getCurrentLocation()))) {
                    relay.setSeen(true);
                }
            }
        } else if (business.currentRoomContainsPowerSwitch()) {
            powerSwitch.setSeen(true);
        }

        positionExitsAndDisableButtons();

        drawMinimap();
        groundImageView.setImage(boardBackgroundMap.get(locationToPoint(business.getCurrentLocation())));
        //println(business.callFriendlyNPC());

        if (!business.getPowerStatus()) {
            println("Time before power turns back on: " + business.getTimeBeforePowerTurnsBackOn());
        }

        checkForBusted();

    }

    private void checkForBusted() {
        if ((forcedToQuit && !business.getCheatMode()) || (business.getPolicedArrived() && !business.getCheatMode()) || (business.isGotBusted() && !business.getCheatMode())) {
            inputs.clear();
            business.updateHighScore(playerName);
            ButtonType close = new ButtonType("", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert quitPopup = new Alert(Alert.AlertType.INFORMATION, "You got " + business.getCurrentHighScore() + " points.", close);
            quitPopup.getDialogPane().lookupButton(close).setVisible(false);

            BooleanValue newGameClicked = new BooleanValue(false);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));

            ImageView customImage = new ImageView(new Image(getClass().getResourceAsStream("Pictures/BUSTED.png"), 500, 400, false, true));

            ButtonBar buttonBar = new ButtonBar();

            Button newgameBtn = new Button("New Game");
            newgameBtn.setOnAction(actionEvent -> {
                newGameClicked.value = true;
                business.restartGame();
                forcedToQuit = false;
                initDrawables();
                initImages();
                update();
                quitPopup.close();
                textArea.clear();
                updateInventoryList();
                updateLootList();
                
            });
            buttonBar.setButtonData(newgameBtn, ButtonBar.ButtonData.OTHER);

            Button highscoreBtn = new Button("Show Highscore");
            highscoreBtn.setOnAction(actionEvent -> {
                AlertBox.display("Highscore", business.getHighScores());
                quitPopup.close();
            });
            ButtonBar.setButtonData(highscoreBtn, ButtonBar.ButtonData.OK_DONE);

            Button closeBtn = new Button("Close");
            closeBtn.setOnAction(actionEvent -> quitPopup.close());
            ButtonBar.setButtonData(closeBtn, ButtonBar.ButtonData.CANCEL_CLOSE);
            buttonBar.getButtons().addAll(highscoreBtn, closeBtn, newgameBtn);

            grid.add(customImage, 0, 0);
            grid.add(buttonBar, 0, 1);
            quitPopup.getDialogPane().setExpandableContent(grid);
            quitPopup.getDialogPane().setExpanded(true);

            quitPopup.setTitle("You got busted!");
            quitPopup.setHeaderText("You got busted!");
            quitPopup.initModality(Modality.WINDOW_MODAL);
            Platform.runLater(quitPopup::showAndWait);

            quitPopup.setOnCloseRequest(dialogEvent -> {
                if (newGameClicked.value == false) {
                    Platform.exit();
                }
            });
        }
    }

    private void drawMinimap() {
        for (Pane pane : paneMap.values()) {
            ObservableList<Node> children = pane.getChildren();
            for (int i = children.size() - 1; i > 0; i--) {
                if (children.get(i) instanceof Rectangle) {
                    children.remove(children.get(i));
                }
            }
        }

        player.draw(paneMap.get(locationToPoint(business.getCurrentLocation())));
        powerSwitch.draw(paneMap.get(locationToPoint(business.getPowerSwitchLocation())));

        if (business.getItemLocation() != null) {
            item.draw(paneMap.get(locationToPoint(business.getItemLocation())));
        }

        for (int i = 0; i < powerRelays.length; i++) {
            powerRelays[i].draw(paneMap.get(locationToPoint(business.getPowerRelayLocations()[i])));
        }
        for (int i = 0; i < guards.length; i++) {
            guards[i].draw(paneMap.get(locationToPoint(business.getGuardLocations()[i])));
        }
    }

    private void updateInventoryList() {
        ObservableList<IItem> inventoryObservList = inventoryListView.getItems();
        inventoryObservList.clear();
        inventoryObservList.addAll(business.getInventoryList());
    }

    private void updateLootList() {
        ObservableList<IItem> lootObservList = lootListView.getItems();
        lootObservList.clear();
        lootObservList.addAll(business.getLootList());
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
        println(business.callMasterMindDaniel());
        println("TOGGLED CHEAT MODE - FJERN DETTE I RIGTIG VERSION");
        business.toggleCheatMode();
    }

    public void handleStealButtonAction(ActionEvent e) {
        if (business.getItemForCurrentRoom() != null && !business.getItemForCurrentRoom().getName().equalsIgnoreCase("Key")) {
            item.setSeen(false);
        }
        forcedToQuit = business.steal();
        updateInventoryList();
        initImages();
        update();
    }

    public void handleInteractButtonAction(ActionEvent e) {
        IBooleanMessage message = business.interact();
        println(message.getMessage());
        forcedToQuit = message.getABoolean();

        update();
    }

    public void handleHideButtonAction(ActionEvent e) {
        forcedToQuit = business.hide();
        update();
    }

    public void handleEscapeButtonAction(ActionEvent e) {
        if (business.isAtEntrance()) {
            ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noBtn  = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType choice = createAlert(Alert.AlertType.CONFIRMATION, "Escape", "", "Do you want to go back inside?", yesBtn, noBtn);
            if (choice == yesBtn) {
                business.escape(true);
                updateLootList();
                business.getInventoryList().clear();
                updateInventoryList();
                initImages();
                setAllDrawablesSeen(false);
                update();
            } else {
                business.escape(false);
                business.updateHighScore(playerName);

                AlertBox.display("Highscore", business.getHighScores());
                Platform.exit();
            }
        }
    }

    public void handleHighScoreButtonAction(ActionEvent e) {
        AlertBox.display("Highscore", business.getHighScores());

    }

    public void handleKeyPress(KeyEvent e) {
        String code = e.getCode().toString();
        if (code.equals("UP") || code.equals("DOWN") || code.equals("RIGHT") || code.equals("LEFT")) {
            e.consume();
        }
        if (!inputs.contains(code) && !forcedToQuit && isSpriteOutsideRectangle(sPlayer, 0, 0, stackPane.getWidth(), stackPane.getHeight())) {
            inputs.add(code);
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        String code = e.getCode().toString();
        inputs.remove(code);
    }

    public void handleMenuItemSaveAction(ActionEvent e) {
        ButtonType buttonType = createAlert(Alert.AlertType.CONFIRMATION, "Save and exit?", "", "Are you sure you want to save the game and exit?");
        if (buttonType == ButtonType.OK) {
            System.out.println("saved the game");
            business.save(playerName);
            Platform.exit();
        }
    }

    public void handleMenuItemCloseAction(ActionEvent e) {
        ButtonType buttonType = createAlert(Alert.AlertType.CONFIRMATION, "Exit the game?", "", "Are you sure you want to exit the game without\nsaving?");
        if (buttonType == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void handleMenuItemHistoryAction(ActionEvent e) {
        AlertBox.display("Historie", "History.txt");
    }

    public void handleMenuItemHelpAction(ActionEvent e) {
        AlertBox.display("Help", "HelpFile.txt");
    }

    private ButtonType createAlert(Alert.AlertType type, String title, String header, String content, ButtonType... buttons) {
        Alert alert = new Alert(type, content, buttons);
        if (header.length() > 0) {
            alert.setHeaderText(header);
        }
        alert.setTitle(title);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(null);
    }

    private void goNorth() {
        if (!forcedToQuit) {
            IBooleanMessage message = business.goDirection(Direction.NORTH);
            forcedToQuit = message.getABoolean() && !business.getCheatMode();
            println(message.getMessage());
            update();
        }
    }

    private void goSouth() {
        if (!forcedToQuit) {
            IBooleanMessage message = business.goDirection(Direction.SOUTH);
            forcedToQuit = message.getABoolean() && !business.getCheatMode();
            println(message.getMessage());
            update();
        }
    }

    private void goEast() {
        if (!forcedToQuit) {
            IBooleanMessage message = business.goDirection(Direction.EAST);
            forcedToQuit = message.getABoolean() && !business.getCheatMode();
            println(message.getMessage());
            update();
        }
    }

    private void goWest() {
        if (!forcedToQuit) {
            IBooleanMessage message = business.goDirection(Direction.WEST);
            forcedToQuit = message.getABoolean() && !business.getCheatMode();
            println(message.getMessage());
            update();
        }
    }

    private void setAllDrawablesSeen(boolean seen) {
        for (VisibleDrawable visibleDrawable : visibleDrawables) {
            visibleDrawable.setSeen(seen);
        }
    }

    private void handleMinimapPaneClick(MouseEvent e) {
        Pane clicked = ((Pane) e.getSource());
        for (Map.Entry<Point2D, Pane> entry : paneMap.entrySet()) {
            if (entry.getValue() == clicked) {
                System.out.println(entry.getKey());
                for (IRoom room : business.getRooms()) {
                    if (room.getLocation().getX() == entry.getKey().getX() && room.getLocation().getY() == entry.getKey().getY()) {
                        System.out.println(room.getVisualDescription());
                    }
                }
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
        if (text.length() != 0) {
            textArea.appendText(text + "\n");
        }
    }

    private void clear() {
        textArea.clear();
    }

    public void injectBusiness(IBusiness business) {
        this.business = business;
    }

    private Point2D locationToPoint(ILocation loc) {
        return new Point2D(loc.getX(), loc.getY());
    }

}
