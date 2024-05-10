package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.GameEntity.*;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.model.MoveDirection;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {
    private GameScreen gameScreen;
    private Map map;
    private Hero hero;

    public KeyHandler(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.map = gameScreen.getMap();
        this.hero = map.getHero();
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.RIGHT && !map.isCharacterCollide(MoveDirection.RIGHT)) {
            hero.moveRight();
        } else if (keyEvent.getCode() == KeyCode.LEFT && !map.isCharacterCollide(MoveDirection.LEFT)) {
            hero.moveLeft();
        } else if (keyEvent.getCode() == KeyCode.UP && !map.isCharacterCollide(MoveDirection.UP)) {
            hero.moveUp();
        } else if (keyEvent.getCode() == KeyCode.DOWN && !map.isCharacterCollide(MoveDirection.DOWN)) {
            hero.moveDown();
        } else if (keyEvent.getCode() == KeyCode.F) {
            interactionWithFKey();
        } else if (keyEvent.getCode() == KeyCode.I) {
            if(gameScreen.isInventoryOpen()){
                gameScreen.closeInventory();
            }else{
                gameScreen.openInventory(hero.getInventory());
                System.out.println("Open inventory");
            }
        }

    }
    public void interactionWithFKey(){
        Entity entity = map.isCollideWithEntity();
        if (entity != null) {
            System.out.println("Interacting with entity");
            System.out.println(hero.getBounds().getBoundsInParent());
            System.out.println(entity.getName() + " " + entity.getBounds().getBoundsInParent());
            if(((Interactable)entity).interact(hero)){
                map.removeEntity(entity.getPos());
            }
        } else {
            System.out.println("No entity to interact with!");
        }
    }
}


