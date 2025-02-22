package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.App;
import com.trinhdin.rpg.model.GameData;
import com.trinhdin.rpg.model.GameEntity.Character.Character;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Interactable;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.view.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * GameScreen class main controller of the game
 * It handles the game loop and create view of the game
 */
@Slf4j
public class GameScreen {
    private Stage stage;
    private Canvas canvas;
    BorderPane root = new BorderPane();
    private final Pane mainPane = new Pane();
    private Scene scene;
    SidePane sidePane;
    private final GameLog gameLog = new GameLog();
    private final int WIN_WIDTH = 850;
    private final int WIN_HEIGHT = 600;
    private final int CANVAS_WIDTH = 600;
    private final int CANVAS_HEIGHT = 400;
    @Getter
    private final Map map = new Map();
    private Hero hero;
    @Getter
    private boolean isInventoryOpen = false;
    private boolean nearMonster = false;
    @Getter
    private boolean questViewOpen = false;
    private Combat combat = null;
    private AnimationTimer animationTimer;

    public GameScreen(Stage stage) {
        this.stage = stage;
    }

    private void initGame(boolean newGame, int level) {
        try {
            map.loadMap(level, newGame);
        } catch (IOException e) {
            log.error("Error loading map: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            log.error("Invalid Level: " + level);
        }
    }

    /**
     * Load saved game from file
     *
     * @param fileName name of the file to load
     * @throws IOException if error occurs while loading file
     */
    public void loadGameFromFile(String fileName) throws IOException {
        GameSaveLoad gameSaveLoad = new GameSaveLoad();
        gameSaveLoad.loadGame(fileName, map);
        initGame(false, map.getLevel());
        hero = map.getHero();
        start();
    }

    /**
     * Load new game from configuration file
     *
     * @param heroName name of the hero
     */
    public void loadNewGame(String heroName) {
        log.info("Loading new game...");
        try {
            map.configureHero(heroName, CANVAS_WIDTH, CANVAS_HEIGHT);
            initGame(true, 0);
            hero = map.getHero();
            start();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }

    private void start() {
        log.info("Starting game...");

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setId("canvas");

        sidePane = new SidePane(WIN_WIDTH - CANVAS_WIDTH);
        sidePane.displayHeroStat(hero);
        sidePane.displayAbility(hero.getAbilities());

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.setDividerPositions(0.7);
        splitPane.getItems().addAll(mainPane, gameLog);

        mainPane.getChildren().addAll(canvas);
        mainPane.setId("mainPane");

        scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/fxml/gameStyle.css").toExternalForm());
        root.setCenter(splitPane);
        root.setRight(sidePane);
        // set key handler
        scene.setOnKeyPressed(new MainKeyHandler(this));
        animationTimer = new AnimationTimer() {
            long lastFrameTime = 0;
            final int FPS = 60;
            final double timePerFrame = 1000_000_000.0 / FPS;
            GraphicsContext gc = canvas.getGraphicsContext2D();

            @Override
            public void handle(long now) {
                if (now - lastFrameTime >= timePerFrame) {
                    //clear canvas
                    gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
                    onUpdate();
                    // update last frame time
                    lastFrameTime = now;
                }
            }
        };
        // start game loop
        animationTimer.start();
        stage.setScene(scene);
        stage.show();
    }

    private void onUpdate() {
        // render map
        renderMap();
        // render enemies, items, etc.
        renderEntities();
        // render hero
        drawHero();
        // check if hero is in combat
        combatController();
    }

    private void combatController() {
        // in Combat
        if (combat != null) {
            // check if combat is ended
            if (combat.isEnded()) {
                if (hero.isDead()) {
                    // hero is dead
                    Platform.runLater(() -> {
                        if (showAlert("Game Over", "You lost! Play Again"))
                            returnToMenu();
                        else
                            exit();
                    });
                } else if (map.getMonsters().isEmpty()) {
                    // all monsters have been killed -> hero won
                    Platform.runLater(() -> {
                        if (showAlert("Game Over", "You won! Play Again?"))
                            returnToMenu();
                        else
                            exit();
                    });
                }
                combat = null;
            }
        } else {
            // not in combat
            Monster monster = map.isMonsterNearby();
            if (monster != null) {
                if (!nearMonster) {
                    sidePane.displayMonsterStat(monster);
                    gameLog.displayLogMsg("Monster nearby: " + monster.getName());
                    nearMonster = true;
                }
            } else {
                nearMonster = false;
            }
        }
    }

    private void drawEntity(Entity entity) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double entityX = entity.getPos().getX() * Entity.getWidth();
        double entityY = entity.getPos().getY() * Entity.getHeight();
        double screenTileX = entityX - hero.getPos().getX() + hero.getScreenPos().getX();
        double screenTileY = entityY - hero.getPos().getY() + hero.getScreenPos().getY();
        // draw only entities that are visible on screen
        if (screenTileX + Entity.getWidth() >= 0 &&
                screenTileX - Entity.getWidth() <= CANVAS_WIDTH &&
                screenTileY + Entity.getHeight() >= 0 &&
                screenTileY - Entity.getHeight() <= CANVAS_HEIGHT) {
            double spriteSize = entity.getImage().getHeight();
            gc.drawImage(entity.getImage(), 0, 0, spriteSize, spriteSize, screenTileX, screenTileY, Entity.getWidth(), Entity.getHeight());
            gc.strokeRect(screenTileX, screenTileY, Entity.getWidth(), Entity.getHeight());
        }
    }

    private void drawHero() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double spriteSize = hero.getImage().getHeight();
        gc.drawImage(hero.getImage(), 0, 0, spriteSize, spriteSize, hero.getScreenPos().getX(), hero.getScreenPos().getY(), Entity.getWidth(), Entity.getHeight());
        gc.setStroke(Color.RED);
        gc.strokeRect(hero.getScreenPos().getX(), hero.getScreenPos().getY(), Entity.getWidth(), Entity.getHeight());
        gc.setStroke(Color.GREEN);
        gc.strokeRect(hero.getScreenPos().getX() + Character.getOffset(), hero.getScreenPos().getY() + Character.getOffset(), Entity.getWidth() - 2 * Character.getOffset(), Entity.getHeight() - 2 * Character.getOffset());
    }

    private void renderMap() {
        map.getTiles().values().forEach(this::drawEntity);
    }

    private void renderEntities() {
        map.getEntities().values().forEach(this::drawEntity);
        map.getMonsters().values().forEach(this::drawEntity);
    }

    /**
     * Open inventory view and equipment view to display all items from hero
     */
    public void openInventory() {
        gameLog.displayLogMsg("Open inventory");
        log.info("Open inventory");
        isInventoryOpen = true;
        HBox splitPane = new HBox();
        // remove canvas from mainPane
        mainPane.getChildren().remove(canvas);
        // create inventory view and equipment view
        InventoryView inventoryView = new InventoryView(hero, gameLog, sidePane);
        EquipmentView equipmentView = new EquipmentView(hero, gameLog, sidePane);
        // add reference equipment view for inventory view
        inventoryView.setEquipmentView(equipmentView);
        // add reference inventory view for equipment view
        equipmentView.setInventoryView(inventoryView);
        // get inventory and equipment pane
        VBox equipmentPane = equipmentView.getEquipmentPane();
        VBox inventoryPane = inventoryView.getInventoryPane();
        // add inventory and equipment pane to splitPane
        splitPane.getChildren().addAll(inventoryPane, equipmentPane);
        mainPane.getChildren().add(splitPane);
        // set position for splitPane
        splitPane.setLayoutX(10);
        splitPane.setLayoutY(10);
        splitPane.setSpacing(10);
    }

    /**
     * Close inventory view
     */
    public void closeInventory() {
        gameLog.displayLogMsg("Close inventory");
        log.info("Close inventory");
        isInventoryOpen = false;
        mainPane.getChildren().remove(0);
        mainPane.getChildren().add(canvas);
    }

    /**
     * Open quest view to display all quests from hero
     */
    public void openQuestView() {
        gameLog.displayLogMsg("Open quest view");
        log.info("Open quest view");
        mainPane.getChildren().remove(canvas);
        QuestView questView = new QuestView(hero.getQuests());
        mainPane.getChildren().add(questView.createQuestPane());
        questViewOpen = true;
    }

    /**
     * Close quest view
     */
    public void closeQuestView() {
        gameLog.displayLogMsg("Close quest view");
        log.info("Close quest view");
        mainPane.getChildren().remove(mainPane.lookup("#questPane"));
        mainPane.getChildren().add(canvas);
        questViewOpen = false;
    }

    /**
     * Trigger interaction with entity nearby
     */
    public void interactionWithFKey() {
        Entity entity = map.isEntityNearby();
        if (entity != null) {
            if (((Interactable) entity).interact(hero)) {
                map.removeEntity(entity);
            }
            gameLog.displayLogMsg(entity.getGameMsg());
        } else {
            gameLog.displayLogMsg("No entity to interact with!");
            log.info("No entity to interact with!");
        }
    }

    /**
     * Enter combat if monster nearby
     */
    public void enterCombatWithMonster() {
        Monster monster = map.isMonsterNearby();
        if (monster != null) {
            gameLog.displayLogMsg("Entering combat with " + monster.getName());
            log.info("Entering combat with " + monster.getName());

            combat = new Combat(map, mainPane, sidePane, gameLog);
            combat.start(monster);
            sidePane.displayMonsterStat(monster);
        }
    }

    /**
     * Exit application
     */
    public void exit() {
        gameLog.displayLogMsg("Exit game");
        log.info("Exit game");
        stage.close();
    }

    /**
     * Show alert dialog with title and question to confirm
     *
     * @param title    title of the alert
     * @param question question to ask
     * @return true if user click OK, false if user click cancel
     */
    public boolean showAlert(String title, String question) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(question);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    /**
     * Return to main menu loaded from MainMenu.fxml
     */
    public void returnToMenu() {
        String path = "MainMenu.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + path));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            String css = App.class.getResource("/fxml/mainMenu.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch (NullPointerException e) {
            log.error("Error switching scene from file: " + path + " " + e.getMessage());
        } catch (IOException e) {
            log.error("Error loading scene from file: " + path + " " + e.getMessage());
        }
    }

    /**
     * Display saving dialog for user to enter file name
     */
    public void showSavingView() {
        String fxmlPath = "/fxml/savingView.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();
            TextField fileNameInput = (TextField) root.lookup("#fileNameInput");
            Button saveGameBtn = (Button) root.lookup("#saveGameBtn");
            Button returnToGameBtn = (Button) root.lookup("#returnToGameBtn");
            Text errorMsg = (Text) root.lookup("#errorMsg");
            saveGameBtn.setOnAction((event) -> this.saveGame(fileNameInput.getText(), errorMsg));
            returnToGameBtn.setOnAction(this::returnToGame);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            log.error("Error loading scene from file: " + fxmlPath + "\n" + e.getMessage());
        }
    }

    /**
     * Save game to file then return to main menu
     *
     * @param filename name of the file to save
     * @param errorMsg error message to display if error occurs
     */
    public void saveGame(String filename, Text errorMsg) {
        try {
            log.info("Saving game to: " + filename + ".json");
            GameSaveLoad gameSaveLoad = new GameSaveLoad();
            // convert attribute from map class to game data
            GameData gameData = new GameData(map);
            gameSaveLoad.saveGame(gameData, filename + ".json");
            log.info("Game saved to " + filename + ".json");
            // return to menu after saving
            returnToMenu();
        } catch (IOException e) {
            errorMsg.setText("Error saving game to: " + filename + ".json");
            log.error(e.getMessage());
        }
    }

    /**
     * Return to game screen
     *
     * @param event action event
     */
    public void returnToGame(ActionEvent event) {
        stage.setScene(scene);
        stage.show();
    }
}
