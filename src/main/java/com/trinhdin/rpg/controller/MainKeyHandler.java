package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.GameData;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.model.MoveDirection;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainKeyHandler implements EventHandler<KeyEvent>{
    private GameScreen gameScreen;
    private Map map;
    private Hero hero;

    public MainKeyHandler(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.map = gameScreen.getMap();
        this.hero = map.getHero();
    }
    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case RIGHT:
                if (!map.isCharacterCollide(MoveDirection.RIGHT)) {
                    hero.moveRight();
                }
                break;
            case LEFT:
                if (!map.isCharacterCollide(MoveDirection.LEFT)) {
                    hero.moveLeft();
                }
                break;
            case UP:
                if (!map.isCharacterCollide(MoveDirection.UP)) {
                    hero.moveUp();
                }
                break;
            case DOWN:
                if (!map.isCharacterCollide(MoveDirection.DOWN)) {
                    hero.moveDown();
                }
                break;
            case F:
                gameScreen.interactionWithFKey();
                break;
            case I:
                if (gameScreen.isInventoryOpen()) {
                    gameScreen.closeInventory();
                } else {
                    gameScreen.openInventory();
                }
                break;
            case E:
                gameScreen.checkMonsterForCombat();
                break;
            case Q:
                if (gameScreen.isQuestViewOpen()) {
                    gameScreen.closeQuestView();
                } else {
                    gameScreen.openQuestView();
                }
                break;
        }
        if(keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.S) {
                GameSaveLoad gameSaveLoad = new GameSaveLoad();
                GameData gameData = new GameData(map.getEntities(), map.getMonsters(), hero);
                gameSaveLoad.saveGame(gameData, "game1.json");
                gameScreen.exit();
        }

    }
}


