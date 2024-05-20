package com.trinhdin.rpg.model;

import com.trinhdin.rpg.controller.LogGameMsg;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.view.GameLog;
import com.trinhdin.rpg.view.SidePane;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
/**
 * Combat class to handle combat logic between hero and monster
 */
public class Combat {
    public enum CombatResult{
        WIN, LOSE, QUIT
    }
    private Hero hero;
    private Monster killedMonster = null;
    private Pane pane;
    private SidePane sidePane;
    private GameLog gameLog;
    private boolean ended = false;
    public Combat(Hero hero, Pane mainPane, SidePane sidePane, GameLog gameLog){
        this.hero = hero;
        this.pane = mainPane;
        this.sidePane = sidePane;
        this.gameLog = gameLog;
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
    public void start(Monster monster){
        pane.requestFocus();
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(event ->{
            switch (event.getCode()){
                case KeyCode.Q:
                    System.out.println("Q");
                        hero.castAbility(0, monster);
                        gameLog.displayLogMsg(hero.getGameMsg());
                    break;
                case KeyCode.W:
                    System.out.println("W");
                        hero.castAbility(1, monster);
                        gameLog.displayLogMsg(hero.getGameMsg());
                    break;
                case KeyCode.E:
                        hero.castAbility(2, monster);
                        gameLog.displayLogMsg(hero.getGameMsg());
                    break;
                case KeyCode.R:
                        hero.castAbility(3, monster);
                        gameLog.displayLogMsg(hero.getGameMsg());
                    break;
                case KeyCode.ESCAPE:
                    this.end();
                    break;
            }
            sidePane.displayHeroStat(hero);
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
