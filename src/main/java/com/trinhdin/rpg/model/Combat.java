package com.trinhdin.rpg.model;

import com.trinhdin.rpg.controller.LogGameMsg;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Combat implements LogGameMsg {


    public enum CombatResult{
        WIN, LOSE, QUIT
    }
    Hero hero;
    Monster killedMonster = null;
    Pane pane;
    Map map;
    String gameMsg = "";
    private boolean ended = false;
    public Combat(Hero hero, Pane pane){
        this.hero = hero;
        this.pane = pane;
    }
    public void end(){
        ended = true;
        System.out.println("Combat end");
        pane.setOnKeyPressed(null);
    }
    public boolean ended(){
        return ended;
    }
    public Monster getKilledMonster(){
        return killedMonster;
    }
    @Override
    public String getGameMsg() {
        return gameMsg;
    }
    public void start(Monster monster){
        pane.requestFocus();
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(event ->{
            switch (event.getCode()){
                case KeyCode.Q:
                    System.out.println("Q");
                        hero.castAbility(0, monster);
                    break;
                case KeyCode.W:
                    System.out.println("W");
                        hero.castAbility(1, monster);
                    break;
                case KeyCode.E:
                        hero.castAbility(2, monster);
                    break;
                case KeyCode.R:
                        hero.castAbility(3, monster);
                    break;
                case KeyCode.ESCAPE:
                    this.end();
                    break;

            }
            event.consume();
            if(monster.isDead()){
                killedMonster = monster;
                this.end();
            }
            if(hero.isDead()) {
                this.end();
            }
        });
    }

}
