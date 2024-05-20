package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.GameData;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.model.MoveDirection;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Handle main key event for canvas
 */
@Slf4j
public class MainKeyHandler implements EventHandler<KeyEvent>{
    private GameScreen gameScreen;
    private Map map;
    private Hero hero;

    public MainKeyHandler(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.map = gameScreen.getMap();
        this.hero = map.getHero();
    }
    /**
     * Handle key event for canvas
     * @param keyEvent
     */
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
                // interact with entity pressing F key
                gameScreen.interactionWithFKey();
                break;
            case I:
                // open or close inventory
                if (gameScreen.isInventoryOpen()) {
                    gameScreen.closeInventory();
                } else {
                    gameScreen.openInventory();
                }
                break;
            case E:
                // enter comabat with monster if monster is nearby
                gameScreen.checkMonsterForCombat();
                break;
            case Q:
                // open or close quest view
                if (gameScreen.isQuestViewOpen()) {
                    gameScreen.closeQuestView();
                } else {
                    gameScreen.openQuestView();
                }
                break;
        }
        // save game using ctrl + s
        if(keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.S) {
                GameSaveLoad gameSaveLoad = new GameSaveLoad();
                GameData gameData = new GameData(map.getEntities(), map.getMonsters(), hero);
                try{
                    gameSaveLoad.saveGame(gameData, "game1.json");
                    log.info("Game saved");
                    gameScreen.exit();
                }catch (IOException e){
                    log.error("Error saving game: " + e.getMessage());
                }
        }
    }
}


