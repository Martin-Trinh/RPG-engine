package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.GameEntity.Character.Character;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Tile;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.model.MoveDirection;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class GameScreen {
    private Scene scene;
    private Canvas canvas;
    private Group root;
    private final int WIN_HEIGHT = 600;
    private final int WIN_WIDTH = 900;
    private Map map;
    Hero hero;
    private final String prefixImgPath = "file:src/main/resources/img/";
    public void createHero(){
        Point2D pos = new Point2D(1, 1);
        Stat stat = new Stat(10, 10, 10, 10, 10, 10, 10);
        hero = new Hero(pos, "Knight", prefixImgPath + "Knight/knight_run_anim_f0.png", 1, stat);
        map.addMapEntity(pos, hero);
    }
    public GameScreen(Stage stage) {
        configureMap();
        createHero();
        canvas = new Canvas(map.getWidth(), map.getHeight() );
        root = new Group();
        scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        addKeyListener();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                //clear canvas
                gc.clearRect(0, 0, map.getWidth(), map.getHeight());
                onUpdate();
            }
        }.start();
        stage.setScene(scene);
        stage.show();
    }
    public void configureMap() {
        map = new Map();
        try {
            map.loadTileMap("src/main/resources/configMap/map1.map");
        } catch (IOException e) {
            e.getMessage();
        }
    }
    public void onUpdate() {
        // render map
        renderMap();
        // render hero
        drawCharacter(hero);
    }

    public void renderEntity(Entity entity) {
        Image img = new Image(entity.getFileName(), Entity.getWidth(), Entity.getHeight(), true, true);
        ImageView imageView = new ImageView(img);
        imageView.setX(entity.getPos().getX() * Entity.getWidth());
        imageView.setY(entity.getPos().getY() * Entity.getHeight());
        root.getChildren().add(imageView);
    }

    public void drawTile(Tile tile) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image img = new Image(tile.getFileName());
        gc.drawImage(img, tile.getPos().getX() * Entity.getWidth(), tile.getPos().getY() * Entity.getHeight(), Entity.getWidth(), Entity.getHeight());
    }
    public void drawCharacter(Entity character) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image img = new Image(character.getFileName());
        gc.drawImage(img, character.getPos().getX() * Entity.getWidth(), character.getPos().getY() * Entity.getHeight(), Entity.getWidth(), Entity.getHeight());
    }

    public void renderMap() {
        map.getTileMap().values().forEach(this::drawTile);
    }

    public void renderEntities() {
        map.getEntities().values().forEach(this::drawCharacter);
    }


    public void addKeyListener() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.RIGHT && !isHeroCollide(MoveDirection.RIGHT)) {
                    hero.moveRight();
                } else if (keyEvent.getCode() == KeyCode.LEFT && !isHeroCollide(MoveDirection.LEFT)) {
                    hero.moveLeft();
                } else if (keyEvent.getCode() == KeyCode.UP && !isHeroCollide(MoveDirection.UP)) {
                    hero.moveUp();
                } else if (keyEvent.getCode() == KeyCode.DOWN && !isHeroCollide(MoveDirection.DOWN)) {
                    hero.moveDown();
                }
            }
        });
    }

    public boolean isHeroCollide(MoveDirection direction) {
        Tile tile;
        switch (direction) {
            case UP:
                tile = map.getTileMap().get(hero.getPos().add(0, -1));
                return isCollideWithTile(tile);
            case DOWN:
                tile = map.getTileMap().get(hero.getPos().add(0, 1));
                return isCollideWithTile(tile);
            case LEFT:
                tile = map.getTileMap().get(hero.getPos().add(-1, 0));
                return isCollideWithTile(tile);
            case RIGHT:
                tile = map.getTileMap().get(hero.getPos().add(1, 0));
                return isCollideWithTile(tile);
            default:
                return true;
        }
    }
    public Rectangle getEntityBound(Entity entity) {
        return new Rectangle(entity.getPos().getX(), entity.getPos().getY(), Entity.getWidth(), Entity.getHeight());
    }
    public boolean isCollideWithTile(Tile tile) {
        if (tile.isCollision()) {
            Rectangle heroBound = getEntityBound(hero);
            Rectangle tileBound = getEntityBound(tile);
            return heroBound.intersects(tileBound.getBoundsInLocal());
        }
        return false;
    }
}
