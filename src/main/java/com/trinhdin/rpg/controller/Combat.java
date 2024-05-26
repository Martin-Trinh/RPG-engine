package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.view.GameLog;
import com.trinhdin.rpg.view.SidePane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Combat class to handle combat logic between hero and monster
 */
@Slf4j
public class Combat {
    private Pane pane;
    private SidePane sidePane;
    private GameLog gameLog;
    private Map map;
    private Hero hero;
    @Getter
    private boolean ended = false;

    public Combat(Map map, Pane mainPane, SidePane sidePane, GameLog gameLog) {
        this.map = map;
        this.hero = map.getHero();
        this.pane = mainPane;
        this.sidePane = sidePane;
        this.gameLog = gameLog;
    }

    public void end() {
        ended = true;
        gameLog.displayLogMsg("Combat end");
        log.info("Combat end");
        pane.setOnKeyPressed(null);
    }

    /**
     * Handle key event for combat
     *
     * @param monster monster to fight
     */
    public void start(Monster monster) {
        pane.requestFocus();
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
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
            if (monster.isDead()) {
                gameLog.displayLogMsg("You defeated " + monster.getName());
                log.info("You defeated " + monster.getName());
                // remove monster from map
                map.removeMonster(monster);
                // gain exp
                gameLog.displayLogMsg("You gained " + monster.getExpWorth() + " exp");
                // check if hero leveled up
                if (hero.gainExp(monster.getExpWorth())) {
                    gameLog.displayLogMsg(hero.getGameMsg());
                }
                sidePane.displayHeroStat(hero);
                // check killed monster complete any quest
                if (hero.completeQuest(monster)) {
                    gameLog.displayLogMsg(monster.getGameMsg());
                }
                this.end();
            }
            if (hero.isDead()) {
                gameLog.displayLogMsg("You have been defeated by " + monster.getName());
                log.info("You have been defeated by " + monster.getName());
                this.end();
            }
            if (event.getCode() != KeyCode.ESCAPE)
                event.consume();
        });
    }

}
