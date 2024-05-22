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
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Combat class to handle combat logic between hero and monster
 */
@Slf4j
public class Combat {
    private Hero hero;
    @Getter
    private Monster killedMonster = null;
    private Pane pane;
    private SidePane sidePane;
    private GameLog gameLog;
    @Getter
    private boolean ended = false;
    public Combat(Hero hero, Pane mainPane, SidePane sidePane, GameLog gameLog){
        this.hero = hero;
        this.pane = mainPane;
        this.sidePane = sidePane;
        this.gameLog = gameLog;
    }
    public void end(){
        ended = true;
        gameLog.displayLogMsg("Combat end");
        log.info("Combat end");
        pane.setOnKeyPressed(null);
    }

    /**
     * Handle key event for combat
     * @param monster monster to fight
     */
    public void start(Monster monster){
        pane.requestFocus();
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(event ->{
            switch (event.getCode()){
                case KeyCode.Q:
                        hero.castAbility(0, monster);
                        gameLog.displayLogMsg(hero.getGameMsg());
                        monster.castAbility(hero);
                        gameLog.displayLogMsg(monster.getGameMsg());
                    break;
                case KeyCode.W:
                        hero.castAbility(1, monster);
                        gameLog.displayLogMsg(hero.getGameMsg());
                        monster.castAbility(hero);
                        gameLog.displayLogMsg(monster.getGameMsg());
                    break;
                case KeyCode.E:
                        hero.castAbility(2, monster);
                        gameLog.displayLogMsg(hero.getGameMsg());
                        monster.castAbility(hero);
                        gameLog.displayLogMsg(monster.getGameMsg());
                    break;
                case KeyCode.ESCAPE:
                    // escape from combat
                    this.end();
                    gameLog.displayLogMsg("You ran away from " + monster.getName());
                    log.info("You quit from combat");
                    break;
            }
            sidePane.displayHeroStat(hero);
            sidePane.displayMonsterStat(monster);

            // check if combat ended
            if(monster.isDead()){
                gameLog.displayLogMsg("You defeated " + monster.getName());
                log.info("You defeated " + monster.getName());
                // monster is dead for gamescreen to remove from map
                killedMonster = monster;
                // gain exp
                gameLog.displayLogMsg("You gained " + monster.getExpWorth() + " exp");
                // check if hero leveled up
                if(hero.gainExp(monster.getExpWorth())){
                    gameLog.displayLogMsg(hero.getGameMsg());
                }
                sidePane.displayHeroStat(hero);

                // check killed monster complete any quest
                if(hero.completeQuest(monster)){
                    gameLog.displayLogMsg(monster.getGameMsg());
                }
                this.end();
            }
            if(hero.isDead()) {
                gameLog.displayLogMsg("You have been defeated by " + monster.getName());
                log.info("You have been defeated by " + monster.getName());
                this.end();
            }
            if(event.getCode() != KeyCode.ESCAPE)
                event.consume();
        });
    }

}
