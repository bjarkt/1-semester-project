package Presentation;

import Acq.*;
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
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;

import java.net.URL;
import java.util.*;

public class PrimaryWindowController implements Initializable {

    // JavaFX GUI elements
    @FXML private GridPane minimapGrid;
    @FXML private Button northButton;
    @FXML private Button westButton;
    @FXML private Button eastButton;
    @FXML private Button southButton;
    @FXML private Button escapeButton;
    @FXML private ImageView groundImageView;
    @FXML private TextArea textArea;
    @FXML private ListView<IItem> inventoryListView;
    @FXML private ListView<IItem> lootListView;
    @FXML private StackPane stackPane;
    @FXML private VBox rootVBox;
    @FXML private Label timeLeftLabel;
    @FXML private Label friendlyNpcLabel;

    // player and its position
    private Sprite sPlayer;
    private Point2DProperty playerStartPos;

    // the doors and their positions
    private List<Sprite> doors;
    private Sprite northDoor;
    private Point2DProperty northDoorPos;
    private Sprite southDoor;
    private Point2DProperty southDoorPos;
    private Sprite eastDoor;
    private Point2DProperty eastDoorPos;
    private Sprite westDoor;
    private Point2DProperty westDoorPos;

    /**
     * Canvas that the sprites are drawn on
     */
    private ResizableCanvas canvas;

    /**
     * List that contains keyboard inputs
     */
    private List<String> inputs;

    private IBusiness business;
    private String playerName;
    private boolean nameLoaded;

    private boolean forcedToQuit;

    private HashMap<ILocation, Pane> paneMap;
    private HashMap<ILocation, Image> boardBackgroundMap;

    // the drawables
    private Player player;
    private Guard[] guards;
    private PowerSwitch powerSwitch;
    private PowerRelay[] powerRelays;
    private Item item;
    private List<Drawable> drawables;

    PrimaryWindowController(boolean nameLoaded) {
        this.paneMap = new HashMap<>();
        this.boardBackgroundMap = new HashMap<>();
        forcedToQuit = false;
        this.nameLoaded = nameLoaded;
    }

    /**
     * Initialize various parts of the controller
     *
     * @param location url location
     * @param resources ResourceBoundle resource
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMinimapGrid();
        initImages();
        initGroundImageView();
        initButtons();
        initDrawables();
        initSprites();
        initCanvas();

        inputs = new ArrayList<>();

        friendlyNpcLabel.setTextAlignment(TextAlignment.JUSTIFY);

        animateSprite();

        // get the name of the player
        if (!nameLoaded) {
            TextInputDialog t = new TextInputDialog("Jeg er for doven til at skrive et rigtigt navn");
            t.setTitle("Enter name");
            t.setHeaderText("Enter your name");
            Optional<String> s = t.showAndWait();
            if (s.isPresent()) {
                if (s.get().contains(" ")) {
                    // spaces are not allowed in the xml, so replace them with '-'.
                    playerName = s.get().replace(" ", "-");
                } else {
                    playerName = s.get();
                }
            } else {
                // if the cancel button was pressed, exit the game
                Platform.exit();
            }
        } else {
            // if the game is loaded, load the name
            this.loadVisibleDrawablesSeenStatus();
            playerName = business.getLoadedPlayerName();
        }
        update();
    }

    /**
     * Create the sprites and set their images
     */
    private void initSprites() {
        sPlayer = new Sprite();
        sPlayer.setImage("/Presentation/Pictures/Sprites/playerSpriteSheet.png", 16, 4, 4, 48, 71, 3, 0, 2, 1);

        doors = new ArrayList<>();
        northDoor = new Sprite();
        northDoor.setImage("/Presentation/Pictures/Sprites/door_horizontal.png");

        southDoor = new Sprite();
        southDoor.setImage("/Presentation/Pictures/Sprites/door_horizontal.png");

        eastDoor = new Sprite();
        eastDoor.setImage("/Presentation/Pictures/Sprites/door_vertical.png");

        westDoor = new Sprite();
        westDoor.setImage("/Presentation/Pictures/Sprites/door_vertical.png");

        updateSpritePosition();
        Collections.addAll(doors, northDoor, southDoor, eastDoor, westDoor);
    }

    /**
     * Create the position of the sprites, and their initial position.
     */
    public void updateSpritePosition() {
        playerStartPos = new Point2DProperty();
        if (stackPane.getWidth() == 0) {
            // if the stackpane hasn't gotten a width, place the player at (200, 200)
            // otherwise he will be placed ontop of doors, and walk through them
            playerStartPos = new Point2DProperty(200, 200);
        } else {
            // place the player in the middle of the stackpane
            playerStartPos.propertyXProperty().bind(stackPane.widthProperty().divide(2).subtract(sPlayer.getWidth() / 2));
            playerStartPos.propertyYProperty().bind(stackPane.heightProperty().divide(2).subtract(sPlayer.getHeight() / 2));
        }
        setPlayerStartPos();

        // place north door at top middle
        northDoorPos = new Point2DProperty();
        northDoorPos.propertyXProperty().bind(stackPane.widthProperty().divide(2).subtract(northDoor.getWidth() / 2));
        northDoorPos.setPropertyY(0);
        northDoor.setPosition(northDoorPos.getPropertyX(), northDoorPos.getPropertyY());

        // place south door at bottom middle
        southDoorPos = new Point2DProperty();
        southDoorPos.propertyXProperty().bind(stackPane.widthProperty().divide(2).subtract(southDoor.getWidth() / 2));
        southDoorPos.propertyYProperty().bind(stackPane.heightProperty().subtract(20));
        southDoor.setPosition(southDoorPos.getPropertyX(), southDoorPos.getPropertyY());

        // place east door at right middle
        eastDoorPos = new Point2DProperty();
        eastDoorPos.propertyXProperty().bind(stackPane.widthProperty().subtract(20));
        eastDoorPos.propertyYProperty().bind(stackPane.heightProperty().divide(2).subtract(eastDoor.getHeight() / 2));
        eastDoor.setPosition(eastDoorPos.getPropertyX(), eastDoorPos.getPropertyY());

        // place west door at left middle
        westDoorPos = new Point2DProperty();
        westDoorPos.setPropertyX(0);
        westDoorPos.propertyYProperty().bind(stackPane.heightProperty().divide(2).subtract(westDoor.getHeight() / 2));
        westDoor.setPosition(westDoorPos.getPropertyX(), westDoorPos.getPropertyY());
    }

    /**
     * Set the player's position at the middle of the room
     */
    private void setPlayerStartPos() {
        sPlayer.setPosition(playerStartPos.getPropertyX(), playerStartPos.getPropertyY());
    }

    /**
     * Animate and move the sprite.
     */
    private void animateSprite() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        LongValue lastNanoTime = new LongValue(System.nanoTime());
        // taget fra https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development/blob/master/Example5.java
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                // handle inputs
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

                // if the player is outside the allowed area, stop him, and move him back a bit
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
                            /* bug fix: if the following line is not done, the player will begin to move at the screen
                             *  where the user enters their name
                             */
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

                // Door sprites are not rendered, but still collidable
                //for (Sprite door : doors) { door.render(gc); }
                sPlayer.render(gc);
            }
        }.start();
    }

    /**
     * Check if a sprite is outside a rectangle, defined by two points (four x/y values).
     *
     * @param sprite The sprite to check
     * @param x1     x value of first point
     * @param y1     y value of first point
     * @param x2     x value of second point
     * @param y2     y value of second point
     * @return true if the sprite is outside the rectangle
     */
    private boolean isSpriteOutsideRectangle(Sprite sprite, double x1, double y1, double x2, double y2) {
        return sprite.getPositionX() > x1 && sprite.getPositionX() + sprite.getWidth() < x2
                && sprite.getPositionY() > y1 && sprite.getPositionY() + sprite.getHeight() < y2;
    }

    /**
     * Create the canvas, ,make it resizable and add it to the GUI
     */
    private void initCanvas() {
        canvas = new ResizableCanvas(this);
        canvas.widthProperty().bind(((Pane) groundImageView.getParent()).widthProperty());
        canvas.heightProperty().bind(((Pane) groundImageView.getParent()).heightProperty());
        canvas.prefWidth(((Pane) groundImageView.getParent()).getPrefWidth());
        canvas.prefHeight(((Pane) groundImageView.getParent()).getPrefHeight());

        stackPane.getChildren().add(canvas);
    }

    /**
     * Create the drawable objects, base their location off of the locations from the business layer
     */
    private void initDrawables() {
        drawables = new ArrayList<>();

        this.player = new Player(business.getCurrentLocation());
        this.guards = new Guard[]{new Guard(business.getGuardLocations()[0]), new Guard(business.getGuardLocations()[1])};
        this.powerSwitch = new PowerSwitch(business.getPowerSwitchLocation());

        ILocation[] powerRelayLocations = business.getPowerRelayLocations();
        this.powerRelays = new PowerRelay[powerRelayLocations.length];
        for (int i = 0; i < powerRelays.length; i++) {
            this.powerRelays[i] = new PowerRelay(powerRelayLocations[i], i);
        }
        this.item = new Item(business.getItemLocation());

        // player and guard are added first, so the drawables are drawn in the correct order.
        drawables.add(player);
        drawables.addAll(Arrays.asList(guards));

        drawables.addAll(Arrays.asList(powerRelays));
        Collections.addAll(drawables, powerSwitch, item);
    }

    /**
     * Put pictures and remove text from buttons
     */
    private void initButtons() {
        HashMap<Button, Image> btnMap = new HashMap<>();
        btnMap.put(northButton, new Image(getClass().getResourceAsStream("Pictures/north.png")));
        btnMap.put(southButton, new Image(getClass().getResourceAsStream("Pictures/south.png")));
        btnMap.put(eastButton, new Image(getClass().getResourceAsStream("Pictures/east.png")));
        btnMap.put(westButton, new Image(getClass().getResourceAsStream("Pictures/west.png")));

        for (Map.Entry<Button, Image> entry : btnMap.entrySet()) {
            entry.getKey().setText("");
            entry.getKey().setGraphic(new ImageView(entry.getValue()));
        }
    }

    /**
     * Make the backgroundimage view resizable
     */
    private void initGroundImageView() {
        Pane parentOfImageView = (Pane) groundImageView.getParent();
        groundImageView.setPreserveRatio(false);
        groundImageView.fitWidthProperty().bind(parentOfImageView.widthProperty());
        groundImageView.fitHeightProperty().bind(parentOfImageView.heightProperty());
    }

    /**
     * Add pane with number to each cell of the gridpane minimap.
     * Initialize hashmap paneMap with location and pane.
     */
    private void initMinimapGrid() {
        int maxCol = getColCount(minimapGrid);
        int maxRow = getRowCount(minimapGrid);

        /*
        This list is used to place the correct numbers on the tiles in the minimap
         */
        List<Integer> stupidList = new ArrayList<>();
        Collections.addAll(stupidList, 15, 16, 17, 18, 19, 10, 11, 12, 13, 14, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4);

        int i = 0;
        for (int r = 0; r < maxRow; r++) {
            for (int c = 0; c < maxCol; c++, i++) {
                Pane p = new Pane();
                // add the number, and append zero if needed
                String s = stupidList.get(i) + "";
                if (s.length() == 1) {
                    s = "0" + stupidList.get(i);
                }
                Text text = new Text(4, 20, s);
                int rowIndex = (maxRow - r) - 1;

                p.getChildren().add(text);
                minimapGrid.add(p, c, (maxRow - rowIndex) - 1);
                paneMap.put(business.newLocation(c, rowIndex), p);
            }
        }
    }

    /**
     * Add images to map according to location and Room visual description.
     */
    private void initImages() {
        for (IRoom room : business.getRooms()) {
            String filename = "Pictures/Rooms/" + room.getVisualDescription() + ".png";
            boardBackgroundMap.put(room.getLocation(), new Image(getClass().getResourceAsStream(filename)));
        }
    }

    /**
     * Position door sprites according to exits in the current room
     * This method is also called each time the canvas is resized.
     */
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

        if (business.isAtEntrance()) {
            escapeButton.setDisable(false);
        } else {
            escapeButton.setDisable(true);
        }

    }

    /**
     * Update various parts of the gui
     * Check for changes from the business layer
     */
    private void update() {
        updateInventoryList();
        updateLootList();

        updateDrawables();
        drawMinimap();
        groundImageView.setImage(boardBackgroundMap.get(business.getCurrentLocation()));

        positionExitsAndDisableButtons();
        friendlyNpcLabel.setText(business.callFriendlyNPC());

        if (!business.getPowerStatus()) {
            timeLeftLabel.setText("Time before power turns back on: " + business.getTimeBeforePowerTurnsBackOn());
        } else if (business.getPoliceAlerted() && business.getTimeBeforePoliceArrives() >= 0) {
            timeLeftLabel.setText("Time before the police arrives: " + business.getTimeBeforePoliceArrives());
        } else {
            timeLeftLabel.setText("");
        }
        println(business.getGlobalMessage());
        business.clearGlobalMessage();

        if (business.getPolicedArrived()) {
            println("The police arrived. You got busted. No points for you. Better luck next time");
        }
        checkForBusted();
    }

    /**
     * Check if any of the drawables can be seen, and update their seen status
     * Update the player and guards locations
     */
    private void updateDrawables() {
        if (business.currentRoomContainsItem()) {
            item.setSeen(true);
        } else if (business.currentRoomContainsPowerRelay()) {
            for (PowerRelay relay : powerRelays) {
                if (relay.getLocation().equals(business.getCurrentLocation())) {
                    relay.setSeen(true);
                }
            }
        } else if (business.currentRoomContainsPowerSwitch()) {
            powerSwitch.setSeen(true);
        }

        // update the location of the two things that can change location every time
        player.setLocation(business.getCurrentLocation());
        for (int i = 0; i < guards.length; i++) {
            guards[i].setLocation(business.getGuardLocations()[i]);
        }
    }

    /**
     * Popup window, if the player got busted
     */
    private void checkForBusted() {
        if (forcedToQuit || business.getPolicedArrived() || business.isGotBusted()) {
            // clear the inputs, so the player stops moving
            inputs.clear();
            // update the highscore
            business.updateHighScore(playerName);

            // Disable all gui elements, so they cannot be interacted with
            for (Node node : rootVBox.getChildren()) {
                node.setDisable(true);
            }

            ButtonType close = new ButtonType("", ButtonBar.ButtonData.CANCEL_CLOSE); // make the built in button invisible
            Alert quitPopup = new Alert(Alert.AlertType.INFORMATION, "You got " + business.getCurrentHighScore() + " points.", close);
            quitPopup.getDialogPane().lookupButton(close).setVisible(false); // make the built in button invisible

            // BooleanValue is needed, so the value can be changed in the OnAction event
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

                // Disable all gui elements, so the game cannot progess.
                for (Node node : rootVBox.getChildren()) {
                    node.setDisable(false);
                }
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
            ButtonBar.setButtonData(newgameBtn, ButtonBar.ButtonData.OTHER);

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

            /*
                quitPopup.showAndWait cannot be used, because an animation is most likely running,
                when this popup will happen
             */
            quitPopup.initModality(Modality.WINDOW_MODAL);
            Platform.runLater(quitPopup::showAndWait);

            quitPopup.setOnCloseRequest(dialogEvent -> {
                if (!newGameClicked.value) {
                    Platform.exit();
                }
            });
        }
    }

    /**
     * Draw drawables on the minimap
     */
    private void drawMinimap() {
        for (Pane pane : paneMap.values()) {
            ObservableList<Node> children = pane.getChildren();
            // Start from the last element, so it checks all elements, and does not skip any
            for (int i = children.size() - 1; i > 0; i--) {
                if (children.get(i) instanceof Rectangle) {
                    children.remove(children.get(i));
                }
            }
        }

        for (Drawable drawable : drawables) {
            if (drawable.getLocation() != null) {
                drawable.draw(paneMap.get(drawable.getLocation()));
            }
        }
    }

    /**
     * Updates the inventory list
     */
    private void updateInventoryList() {
        ObservableList<IItem> inventoryObservList = inventoryListView.getItems();
        inventoryObservList.clear();
        inventoryObservList.addAll(business.getInventoryList());
    }

    /**
     * Updates the loot list
     */
    private void updateLootList() {
        ObservableList<IItem> lootObservList = lootListView.getItems();
        lootObservList.clear();
        lootObservList.addAll(business.getLootList());
    }

    /**
     * Handle action on the north button
     *
     * @param e Action event
     */
    public void handleNorthButton(ActionEvent e) {
        goNorth();
    }

    /**
     * Handle action on the south button
     *
     * @param e Action event
     */
    public void handleSouthButton(ActionEvent e) {
        goSouth();
    }

    /**
     * Handle action on the east button
     *
     * @param e Action event
     */
    public void handleEastButton(ActionEvent e) {
        goEast();
    }

    /**
     * Handle action on the west button
     *
     * @param e Action event
     */
    public void handleWestButton(ActionEvent e) {
        goWest();
    }

    /**
     * Handle action on the call button
     * Write to the textarea, what master mind daniel wants to say
     *
     * @param e Action event
     */
    public void handleCallButtonAction(ActionEvent e) {
        println(business.callMasterMindDaniel());
    }

    /**
     * Handle action on the steal button
     * If possible, steal the item
     *
     * @param e Action event
     */
    public void handleStealButtonAction(ActionEvent e) {
        // Do not change the seen status of the drawable item, if the item stolen is a key
        if (business.getItemForCurrentRoom() != null && !business.getItemForCurrentRoom().getName().equalsIgnoreCase("Key")) {
            item.setSeen(false);
        }
        IBooleanMessage message = business.steal();
        forcedToQuit = message.getABoolean();
        println(message.getMessage());
        updateInventoryList();
        initImages();
        update();
    }

    /**
     * Handle action on the interact button
     * If possible, interact with interactables
     *
     * @param e Action event
     */
    public void handleInteractButtonAction(ActionEvent e) {
        IBooleanMessage message = business.interact();
        println(message.getMessage());
        forcedToQuit = message.getABoolean();

        update();
    }

    /**
     * Handle action on the hide button
     * Hide the player
     *
     * @param e Action event
     */
    public void handleHideButtonAction(ActionEvent e) {
        forcedToQuit = business.hide();
        println("You hide.");
        update();
    }

    /**
     * Handle action on the escape button
     * if possible, escape from the museum, and give the player choice of going back inside.
     *
     * @param e Action event
     */
    public void handleEscapeButtonAction(ActionEvent e) {
        if (business.isAtEntrance()) {
            ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType choice = createAlert(Alert.AlertType.CONFIRMATION, "Escape", "", "Do you want to go back inside?", yesBtn, noBtn);
            if (choice == yesBtn) {
                business.escape(true);
                initDrawables();
                updateLootList();
                business.getInventoryList().clear();
                updateInventoryList();
                initImages();
                setAllDrawablesSeen(false);
                textArea.clear();
                update();
            } else {
                business.escape(false);
                business.updateHighScore(playerName);

                AlertBox.display("Highscore", business.getHighScores());
                Platform.exit();
            }
        }
    }

    /**
     * Handle action on the highscore button
     * Display list of highscores
     *
     * @param e Action event
     */
    public void handleHighScoreButtonAction(ActionEvent e) {
        AlertBox.display("Highscore", business.getHighScores());

    }

    /**
     * Get input from the keyboard. Ignore arrow keys and consume their event
     *
     * @param e Key event
     */
    public void handleKeyPress(KeyEvent e) {
        String code = e.getCode().toString();
        /*
        Arrow keys also navigate the javafx gui elements, so if they are pressed, consume their event
        so the focus stays on the canvas
         */
        if (code.equals("UP") || code.equals("DOWN") || code.equals("RIGHT") || code.equals("LEFT")) {
            e.consume();
        }
        if (!inputs.contains(code) && !forcedToQuit && isSpriteOutsideRectangle(sPlayer, 0, 0, stackPane.getWidth(), stackPane.getHeight())) {
            inputs.add(code);
        }
    }

    /**
     * Handle input from keyborad
     * Remove the pressed key from the inputs list
     *
     * @param e key event
     */
    public void handleKeyReleased(KeyEvent e) {
        String code = e.getCode().toString();
        inputs.remove(code);
    }

    /**
     * Handle action on save button.
     * Saves the game and exits
     *
     * @param e action event
     */
    public void handleMenuItemSaveAction(ActionEvent e) {
        ButtonType buttonType = createAlert(Alert.AlertType.CONFIRMATION, "Save and exit?", "", "Are you sure you want to save the game and exit?");
        if (buttonType == ButtonType.OK) {
            business.save(playerName);
            this.saveVisibleDrawablesSeenStatus();
            Platform.exit();
        }
    }

    /**
     * Handle action on the close button
     * Closes the game
     *
     * @param e Action event
     */
    public void handleMenuItemCloseAction(ActionEvent e) {
        ButtonType buttonType = createAlert(Alert.AlertType.CONFIRMATION, "Exit the game?", "", "Are you sure you want to exit the game without\nsaving?");
        if (buttonType == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     * Handle action on the history button
     * Shows a window with history of the game
     *
     * @param e Action event
     */
    public void handleMenuItemHistoryAction(ActionEvent e) {
        AlertBox.display("Historie", "History.txt");
    }

    /**
     * Handle action on the help button
     * Shows a window with help text.
     *
     * @param e Action event
     */
    public void handleMenuItemHelpAction(ActionEvent e) {
        AlertBox.display("Help", "HelpFile.txt");
    }

    /**
     * Create an alert window, and return the button that was pressed.
     *
     * @param type    Type of alert window (which icon to display)
     * @param title   Title of alert (text in the top of the window)
     * @param header  Header text
     * @param content Content text
     * @param buttons Which buttons to display
     * @return the type of that was pressed, if any. If no button pressed, return null.
     */
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

    /**
     * Attempt to move the player north.
     */
    private void goNorth() {
        if (!forcedToQuit) {
            IBooleanMessage message = business.goDirection(Direction.NORTH);
            forcedToQuit = message.getABoolean();
            println(message.getMessage());
            update();
        }
    }

    /**
     * Attempt to move the player south.
     */
    private void goSouth() {
        if (!forcedToQuit) {
            IBooleanMessage message = business.goDirection(Direction.SOUTH);
            forcedToQuit = message.getABoolean();
            println(message.getMessage());
            update();
        }
    }

    /**
     * Attempt to move the player east.
     */
    private void goEast() {
        if (!forcedToQuit) {
            IBooleanMessage message = business.goDirection(Direction.EAST);
            forcedToQuit = message.getABoolean();
            println(message.getMessage());
            update();
        }
    }

    /**
     * Attempt to move the player west.
     */
    private void goWest() {
        if (!forcedToQuit) {
            IBooleanMessage message = business.goDirection(Direction.WEST);
            forcedToQuit = message.getABoolean();
            println(message.getMessage());
            update();
        }
    }

    /**
     * set the seen status of all visible drawables
     *
     * @param seen seen boolean value
     */
    private void setAllDrawablesSeen(boolean seen) {
        for (Drawable drawable : drawables) {
            if (drawable instanceof VisibleDrawable) {
                ((VisibleDrawable) drawable).setSeen(seen);
            }
        }
    }

    /**
     * get amount of columns in gridpane
     *
     * @param pane a {@link GridPane}
     * @return amount of columns in gridpane
     */
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

    /**
     * Found at https://stackoverflow.com/a/20766735
     * Get the amount of rows in a grid pane
     *
     * @param pane a {@link GridPane}
     * @return amount of rows in pane
     */
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

    /**
     * print text to textarea. Do not print if text is empty
     *
     * @param text some text
     */
    private void println(String text) {
        if (text.length() != 0) {
            textArea.appendText(text + "\n");
        }
    }

    /**
     * inject business facade into gui layer
     *
     * @param business the business facade
     */
    public void injectBusiness(IBusiness business) {
        this.business = business;
    }

    /**
     * Save the status of the visible drawables
     */
    private void saveVisibleDrawablesSeenStatus() {
        Map<String, String> mapToSave = new LinkedHashMap<>();
        mapToSave.put("itemStatus", String.valueOf(item.hasSeen()));
        mapToSave.put("powerSwitchStatus", String.valueOf(powerSwitch.hasSeen()));
        for (int i = 0; i < business.getPowerRelayLocations().length; i++) {
            mapToSave.put("powerRelayStatus_" + i, String.valueOf(powerRelays[i].hasSeen()));
        }
        business.saveSeenStatus(mapToSave);
    }

    /**
     * load the status of the visible drawables
     */
    private void loadVisibleDrawablesSeenStatus() {
        setAllDrawablesSeen(false);

        Map<String, String> loadedMap = business.loadSeenStatus();
        if (loadedMap != null) {
            item.setSeen(Boolean.valueOf(loadedMap.get("itemStatus")));
            powerSwitch.setSeen(Boolean.valueOf(loadedMap.get("powerSwitchStatus")));

            for (int i = 0; i < business.getPowerRelayLocations().length; i++) {
                for (PowerRelay powerRelay : powerRelays) {
                    if (business.getPowerRelays()[i].getID() == powerRelay.getID()) {
                        powerRelays[i].setSeen(Boolean.valueOf(loadedMap.get("powerRelayStatus_" + i)));
                        powerRelays[i].setLocation(business.getPowerRelayLocations()[i]);
                    }
                }
            }
        }
    }
}
