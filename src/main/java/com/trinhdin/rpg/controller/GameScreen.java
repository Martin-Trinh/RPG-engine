package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.Combat;
import com.trinhdin.rpg.model.GameEntity.Character.Character;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Interactable;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.view.*;
import javafx.animation.AnimationTimer;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class GameScreen {
    private final Canvas canvas;
    private final Pane mainPane = new Pane();
    private BorderPane root = new BorderPane();
    SplitPane splitPane = new SplitPane();
    SidePane sidePane;
    private GameLog gameLog = new GameLog();
    private final int WIN_WIDTH = 800;
    private final int WIN_HEIGHT = 600;
    private final int CANVAS_WIDTH = 600;
    private final int CANVAS_HEIGHT = 400;
    private Map map;
    private final Hero hero;
    private boolean isInventoryOpen = false;
    private boolean nearMonster = false;
    private boolean questViewOpen = false;

    private Combat combat = null;

    private final AnimationTimer animationTimer;
    public GameScreen(Stage stage) {
        configureMap();
        hero = map.getHero();
        createHero();
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setId("canvas");

        sidePane = new SidePane(WIN_WIDTH - CANVAS_WIDTH);
        sidePane.displayHeroStat(hero);

        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.setDividerPositions(0.7);
        splitPane.getItems().addAll(mainPane,gameLog);

        mainPane.getChildren().addAll(canvas);
        mainPane.setId("mainPane");

        sidePane.displayAbility(hero.getAbilities());

        Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/fxml/gameStyle.css").toExternalForm());
        root.setCenter(splitPane);
        root.setRight(sidePane);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(new MainKeyHandler(this));
         animationTimer = new AnimationTimer() {
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
         // start game loop
        animationTimer.start();
        stage.setScene(scene);
        stage.show();
    }
    private void configureMap() {
        map = new Map();
        try {
            String mapFileName = "src/main/resources/configMap/map1.map";
            map.loadTileMap(mapFileName);
        } catch (IOException e) {
            System.out.println("Error loading map");
        }
    }
    private void onUpdate() {
        // render map
        renderMap();
        // render enemies, items, etc.
        renderEntities();
        // render hero
        drawHero();
        combatController();
    }
    private void combatController(){
        if(combat != null){
            // check if combat is ended
            if(combat.ended()){
                Monster monster = combat.getKilledMonster();
                if(monster != null){
                    map.removeMonster(monster);
                    gameLog.displayLogMsg("Hero won");
                    System.out.println("Hero won");
                }else{
                    gameLog.displayLogMsg("Hero lost");
                    // exit out of the game
                    System.out.println("Hero lost");
                }
                combat = null;
            }
        }else{
            // not in combat
            Monster monster = map.isMonsterNearby();
            if(monster != null){
                if(!nearMonster) {
                    sidePane.displayMonsterStat(monster);
                    gameLog.displayLogMsg("Monster nearby: " + monster.getName());
                    nearMonster = true;
                }
            }else{
                nearMonster = false;
            }
        }
    }
    public Map getMap() {
        return map;
    }
    public void createHero(){
        // set screen position for hero to middle of canvas
        double screenX = CANVAS_WIDTH/2.0 - Entity.getWidth()/2.0;
        double screenY = CANVAS_HEIGHT/2.0 - Entity.getHeight()/2.0;
        Point2D screenPos = new Point2D(screenX, screenY);
        hero.setScreenPos(screenPos);
    }
    private void drawEntity(Entity entity) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double entityX = entity.getPos().getX()* Entity.getWidth();
        double entityY = entity.getPos().getY()* Entity.getHeight();
        double screenTileX = entityX - hero.getPos().getX() + hero.getScreenPos().getX();
        double screenTileY = entityY - hero.getPos().getY() + hero.getScreenPos().getY();
        // draw only entities that are visible on screen
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

    private void drawHero() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double spriteSize = hero.getImage().getHeight();
        gc.drawImage(hero.getImage(),0,0, spriteSize, spriteSize, hero.getScreenPos().getX(), hero.getScreenPos().getY(), Entity.getWidth(), Entity.getHeight());
        gc.setStroke(Color.RED);
        gc.strokeRect(hero.getScreenPos().getX(), hero.getScreenPos().getY(), Entity.getWidth(), Entity.getHeight());
        gc.setStroke(Color.GREEN);
        gc.strokeRect(hero.getScreenPos().getX() + Character.getOffset(), hero.getScreenPos().getY() + Character.getOffset(), Entity.getWidth() - 2*Character.getOffset(), Entity.getHeight() - 2*Character.getOffset());
    }
    private void renderMap() {
        map.getTileMap().values().forEach(this::drawEntity);
    }
    private void renderEntities() {
        map.getEntities().values().forEach(this::drawEntity);
        map.getMonsters().values().forEach(this::drawEntity);
    }
    public void openInventory(){
        gameLog.displayLogMsg("Open inventory");
        System.out.println("Open inventory");
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
    public void closeInventory(){
        gameLog.displayLogMsg("Close inventory");
        System.out.println("Close inventory");
        isInventoryOpen = false;
        mainPane.getChildren().remove(0);
        mainPane.getChildren().add(canvas);
    }
    public void openQuestView(){
        gameLog.displayLogMsg("Open quest view");
        System.out.println("Open quest view");
        mainPane.getChildren().remove(canvas);
        QuestView questView = new QuestView(hero.getQuests());
        mainPane.getChildren().add(questView.createQuestPane());
        questViewOpen = true;
    }
    public void closeQuestView(){
        gameLog.displayLogMsg("Close quest view");
        System.out.println("Close quest view");
        mainPane.getChildren().remove(mainPane.lookup("#questPane"));
        mainPane.getChildren().add(canvas);
        questViewOpen = false;
    }
    public boolean isInventoryOpen() {
        return isInventoryOpen;
    }
    public boolean isQuestViewOpen() {return questViewOpen;}
    public void interactionWithFKey(){
        Entity entity = map.isCollideWithEntity();
        if (entity != null) {
            if(((Interactable)entity).interact(hero)){
                map.removeEntity(entity);
            }
            gameLog.displayLogMsg(entity.getGameMsg());
        } else {
            gameLog.displayLogMsg("No entity to interact with!");
            System.out.println("No entity to interact with!");
        }
    }
    public void checkMonsterForCombat(){
        Monster monster = map.isMonsterNearby();
        if(monster != null){
            gameLog.displayLogMsg("Entering combat with " + monster.getName());
            System.out.println("Entering combat with " + monster.getName());

            combat = new Combat(hero, mainPane);
            combat.start(monster);
            sidePane.displayMonsterStat(monster);
        }
    }
}
