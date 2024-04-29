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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;

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
        Point2D pos = new Point2D(Entity.getWidth(), Entity.getHeight());
//        Point2D pos = new Point2D(1, 1);

        Stat stat = new Stat(10, 10, 10, 10, 10, 10, 10);
        hero = new Hero(pos, "Knight", prefixImgPath + "Knight/knight_run_anim_f0.png", 2, stat);
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
        gc.drawImage(tile.getImage(), tile.getPos().getX() * Entity.getWidth(), tile.getPos().getY() * Entity.getHeight() , Entity.getWidth(), Entity.getHeight());
    }
    public void drawCharacter(Entity character) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(character.getImage(), character.getPos().getX(), character.getPos().getY(), Entity.getWidth(), Entity.getHeight());
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
                if (keyEvent.getCode() == KeyCode.RIGHT && !isCharacterCollide(MoveDirection.RIGHT)) {
                    hero.moveRight();
                } else if (keyEvent.getCode() == KeyCode.LEFT && !isCharacterCollide(MoveDirection.LEFT)) {
                    hero.moveLeft();
                } else if (keyEvent.getCode() == KeyCode.UP && !isCharacterCollide(MoveDirection.UP)) {
                    hero.moveUp();
                } else if (keyEvent.getCode() == KeyCode.DOWN && !isCharacterCollide(MoveDirection.DOWN)) {
                    hero.moveDown();
                }
            }
        });
    }

    public boolean isCharacterCollide(MoveDirection direction) throws IllegalStateException {
        Point2D newPos;
        switch (direction) {
            case UP:
                newPos = hero.getPos().add(0, -hero.getSpeed());
                break;
            case DOWN:
                newPos = hero.getPos().add(0, hero.getSpeed());
                break;
            case LEFT:
                newPos = hero.getPos().add(-hero.getSpeed(), 0);
                break;
            case RIGHT:
                newPos = hero.getPos().add(hero.getSpeed(), 0);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        return calculateCollision(newPos, direction);
    }

    public boolean calculateCollision(Point2D newPos, MoveDirection direction) {
        int leftX = (int) ((newPos.getX() + Character.getBoundOffset()) / Entity.getWidth());
        int topY = (int) ((newPos.getY() + Character.getBoundOffset()) / Entity.getHeight());
        int rightX = (int) ((newPos.getX() + Entity.getWidth() - Character.getBoundOffset()) / Entity.getWidth());
        int bottomY = (int) ((newPos.getY() + Entity.getHeight() - Character.getBoundOffset()) / Entity.getHeight());
        Tile tile1 = null, tile2 = null;
        System.out.println(hero.getPos());
        if(direction == MoveDirection.UP){
            tile1 = map.getTileMap().get(new Point2D(leftX, topY));
            tile2 = map.getTileMap().get(new Point2D(rightX, topY));
        }else if(direction == MoveDirection.DOWN){
            tile1 = map.getTileMap().get(new Point2D(leftX, bottomY));
            tile2 = map.getTileMap().get(new Point2D(rightX, bottomY));
        }else if(direction == MoveDirection.LEFT){
            tile1 = map.getTileMap().get(new Point2D(leftX, topY));
            tile2 = map.getTileMap().get(new Point2D(leftX, bottomY));
        }else if(direction == MoveDirection.RIGHT){
            tile1 = map.getTileMap().get(new Point2D(rightX, topY));
            tile2 = map.getTileMap().get(new Point2D(rightX, bottomY));
        }
        return (tile1 != null && tile1.isCollision()) || (tile2 != null && tile2.isCollision());
    }
}
