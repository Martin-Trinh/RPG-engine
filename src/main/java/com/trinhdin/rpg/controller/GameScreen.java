package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
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
import javafx.stage.Stage;

import java.io.*;

public class GameScreen {
    private Scene scene;
    private Canvas canvas;
    private Group root;
    private final int WIN_HEIGHT = 400;
    private final int WIN_WIDTH = 600;
    private Map map;
    private final Hero hero;
    private int animationTick = 0;
    private int animationIndex = 0;
    private int animationSpeed = 6;
    public GameScreen(Stage stage) {
        configureMap();
        hero  = map.getHero();
        createHero();
        canvas = new Canvas(map.getWidth(), map.getHeight() );
        root = new Group();
        scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        addKeyListener();
        AnimationTimer animationTimer = new AnimationTimer() {
            long lastFrameTime = 0;
            final int FPS = 60;
            final double timePerFrame = 1000_000_000.0 / FPS;
            @Override
            public void handle(long now) {
                if(now - lastFrameTime >= timePerFrame){
                    //clear canvas
                    gc.clearRect(0, 0, map.getWidth(), map.getHeight());

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
            map.loadTileMap("src/main/resources/configMap/map1.map");
        } catch (IOException e) {
            e.getMessage();
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

    public void renderEntity(Entity entity) {
        Image img = new Image(entity.getFileName(), Entity.getWidth(), Entity.getHeight(), true, true);
        ImageView imageView = new ImageView(img);
        imageView.setX(entity.getPos().getX() * Entity.getWidth());
        imageView.setY(entity.getPos().getY() * Entity.getHeight());
        root.getChildren().add(imageView);
    }
    public void createHero(){
        double screenX = WIN_WIDTH/2.0 - Entity.getWidth()/2.0;
        double screenY = WIN_HEIGHT/2.0 - Entity.getHeight()/2.0;
        Point2D screenPos = new Point2D(screenX, screenY);
        hero.setScreenPos(screenPos);
    }
    public void animate(){
        animationTick++;
        if(animationTick >= animationSpeed){
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= 4){
                animationIndex = 0;
            }
        }
    }
    public void drawTile(Entity entity) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double entityX = entity.getPos().getX()* Entity.getWidth();
        double entityY = entity.getPos().getY()* Entity.getWidth();
        double screenTileX = entityX - hero.getPos().getX() + hero.getScreenPos().getX();
        double screenTileY = entityY - hero.getPos().getY() + hero.getScreenPos().getY();
        if( screenTileX + Entity.getWidth() >= 0 &&
            screenTileX - Entity.getWidth() <= WIN_WIDTH &&
            screenTileY + Entity.getHeight() >= 0 &&
            screenTileY - Entity.getHeight() <= WIN_HEIGHT)
        {
            if(entity instanceof Monster)
                gc.drawImage(entity.getImage(), 0, 0, 16, 16, screenTileX, screenTileY, Entity.getWidth(), Entity.getHeight());
            else
                gc.drawImage(entity.getImage(), screenTileX, screenTileY , Entity.getWidth(), Entity.getHeight());

        }
    }
    public void drawHero() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(hero.getImage(),0,0, 16, 16, hero.getScreenPos().getX(), hero.getScreenPos().getY(), Entity.getWidth(), Entity.getHeight());
    }

    public void renderMap() {
        map.getTileMap().values().forEach(this::drawTile);
    }

    public void renderEntities() {
        map.getEntities().values().forEach(this::drawTile);
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
