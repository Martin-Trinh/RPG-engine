package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.GameData;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.model.MoveDirection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
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
     * Handles key event for canvas
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
                // enter combat with monster if monster is nearby
                gameScreen.enterCombatWithMonster();
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
                gameScreen.showSavingView();
        // exit game using ctrl + c
        } else if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.C) {
            if(gameScreen.showAlert("Exit out of this game", "Do you want to exit?"))
                gameScreen.exit();
        // return to menu using ctrl + w
        } else if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.W) {
            if(gameScreen.showAlert("Return to menu", "Quit game without saving?"))
                gameScreen.returnToMenu();
        }
    }
}


