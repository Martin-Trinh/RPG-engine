package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.Combat;
import com.trinhdin.rpg.model.GameEntity.*;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.model.MoveDirection;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent>{
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
//                System.out.println("Move right");
            hero.moveRight();
        } else if (keyEvent.getCode() == KeyCode.LEFT && !map.isCharacterCollide(MoveDirection.LEFT)) {
            hero.moveLeft();
        } else if (keyEvent.getCode() == KeyCode.UP && !map.isCharacterCollide(MoveDirection.UP)) {
            hero.moveUp();
        } else if (keyEvent.getCode() == KeyCode.DOWN && !map.isCharacterCollide(MoveDirection.DOWN)) {
            hero.moveDown();
        } else if (keyEvent.getCode() == KeyCode.F) {
            gameScreen.interactionWithFKey();
        } else if (keyEvent.getCode() == KeyCode.I) {
            if (gameScreen.isInventoryOpen()) {
                gameScreen.closeInventory();
            } else {
                gameScreen.openInventory();
            }
        } else if (keyEvent.getCode() == KeyCode.E) {
            gameScreen.checkMonsterForCombat();
        }

    }
}


