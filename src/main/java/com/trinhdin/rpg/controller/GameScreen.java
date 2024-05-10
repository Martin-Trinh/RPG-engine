package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.GameEntity.Character.Character;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.view.EquipmentView;
import com.trinhdin.rpg.view.InventoryView;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class GameScreen {
    private Scene scene;
    private Canvas canvas;
    private Pane mainPane = new Pane();
    private BorderPane root = new BorderPane();
    SplitPane splitPane = new SplitPane();
    private final int WIN_WIDTH = 800;
    private final int WIN_HEIGHT = 600;
    private final int CANVAS_WIDTH = 600;
    private final int CANVAS_HEIGHT = 400;
    private Map map;
    private final Hero hero;
    boolean isInventoryOpen = false;
    public GameScreen(Stage stage) {
        configureMap();
        hero = map.getHero();
        createHero();
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        GameLog gameLog = new GameLog();
        SidePane sidePane = new SidePane(WIN_WIDTH - CANVAS_WIDTH);
        sidePane.displayHeroStat(hero);

        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.setDividerPositions(0.7);
        splitPane.getItems().addAll(mainPane,gameLog);

        mainPane.getChildren().addAll(canvas);
        mainPane.setStyle("-fx-border-color: black");


        // Sample log items
        gameLog.addLogItem("Log Item 1");
        gameLog.addLogItem("Log Item 2");
        gameLog.addLogItem("Log Item 3");
        gameLog.addLogItem("Log Item 4");
        gameLog.addLogItem("Log Item 5");

        gameLog.displayLog();

        scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/fxml/gameStyle.css").toExternalForm());
        canvas.setId("canvas");
        root.setCenter(splitPane);
        root.setRight(sidePane);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        addKeyListener();
        AnimationTimer animationTimer = new AnimationTimer() {
            long lastFrameTime = 0;
            final int FPS = 60;
            final double timePerFrame = 1000_000_000.0 / FPS;
            GraphicsContext gc = canvas.getGraphicsContext2D();
            @Override
            public void handle(long now) {
                if(now - lastFrameTime >= timePerFrame){
                    //clear canvas
                    gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

                    onUpdate();
                    // update last frame time
                    lastFrameTime = now;
                }
            }
        };
        animationTimer.start();
        stage.setScene(scene);
        stage.show();
    }
    public void configureMap() {
        map = new Map();
        try {
            String mapFileName = "src/main/resources/configMap/map1.map";
            map.loadTileMap(mapFileName);
        } catch (IOException e) {
            System.out.println("Error loading map");
        }
    }
    public void onUpdate() {
        // render map
        renderMap();
        // render enemies, items, etc.
        renderEntities();
        // render hero
        drawHero();
    }
    public Map getMap() {
        return map;
    }
    public void createHero(){
        double screenX = CANVAS_WIDTH/2.0 - Entity.getWidth()/2.0;
        double screenY = CANVAS_HEIGHT/2.0 - Entity.getHeight()/2.0;
        Point2D screenPos = new Point2D(screenX, screenY);
        hero.setScreenPos(screenPos);
    }
    public void drawEntity(Entity entity) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double entityX = entity.getPos().getX()* Entity.getWidth();
        double entityY = entity.getPos().getY()* Entity.getHeight();
        double screenTileX = entityX - hero.getPos().getX() + hero.getScreenPos().getX();
        double screenTileY = entityY - hero.getPos().getY() + hero.getScreenPos().getY();
        if( screenTileX + Entity.getWidth() >= 0 &&
            screenTileX - Entity.getWidth() <= CANVAS_WIDTH &&
            screenTileY + Entity.getHeight() >= 0 &&
            screenTileY - Entity.getHeight() <= CANVAS_HEIGHT)
        {
            double spriteSize = entity.getImage().getHeight();
            gc.drawImage(entity.getImage(), 0, 0, spriteSize, spriteSize, screenTileX, screenTileY, Entity.getWidth(), Entity.getHeight());
            gc.strokeRect(  screenTileX, screenTileY, Entity.getWidth(), Entity.getHeight());
        }
    }
    public void drawHero() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double spriteSize = hero.getImage().getHeight();
        gc.drawImage(hero.getImage(),0,0, spriteSize, spriteSize, hero.getScreenPos().getX(), hero.getScreenPos().getY(), Entity.getWidth(), Entity.getHeight());
        gc.setStroke(Color.RED);
        gc.strokeRect(hero.getScreenPos().getX(), hero.getScreenPos().getY(), Entity.getWidth(), Entity.getHeight());
        gc.setStroke(Color.GREEN);
        gc.strokeRect(hero.getScreenPos().getX() + Character.getOffset(), hero.getScreenPos().getY() + Character.getOffset(), Entity.getWidth() - 2*Character.getOffset(), Entity.getHeight() - 2*Character.getOffset());

    }
    public void renderMap() {
        map.getTileMap().values().forEach(this::drawEntity);
    }
    public void renderEntities() {
        map.getEntities().values().forEach(this::drawEntity);
    }
    public void addKeyListener() {
        scene.setOnKeyPressed(new KeyHandler(this));
    }
    public void openInventory(Inventory inventory){
        isInventoryOpen = true;
        HBox splitPane = new HBox();
        mainPane.getChildren().remove(canvas);
        InventoryView inventoryView = new InventoryView(inventory);
        EquipmentView equipmentView = new EquipmentView();
        VBox equipmentPane = equipmentView.getEquipmentPane();
        VBox inventoryPane = inventoryView.getInventoryPane();
        splitPane.getChildren().addAll(inventoryPane, equipmentPane);
        mainPane.getChildren().add(splitPane);
        splitPane.setLayoutX(10);
        splitPane.setLayoutY(10);
        splitPane.setSpacing(10);
    }
    public void closeInventory(){
        isInventoryOpen = false;
        System.out.println("Close inventory");
        mainPane.getChildren().remove(0);
        mainPane.getChildren().add(canvas);
    }
    public boolean isInventoryOpen() {
        return isInventoryOpen;
    }
}
