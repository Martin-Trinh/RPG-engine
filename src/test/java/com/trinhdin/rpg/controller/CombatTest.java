package com.trinhdin.rpg.controller;


import com.trinhdin.rpg.model.GameConfig;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Character.Quest;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.GameEntity.NPC;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.view.GameLog;
import com.trinhdin.rpg.view.SidePane;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CombatTest {
    private class CombatForTest{
        private Map map;
        private Hero hero;
        @Getter
        private boolean ended = false;

        /**
         * Helper class for testing
         * @param map map
         */
        public CombatForTest(Map map) {
            this.map = map;
            this.hero = map.getHero();
        }
        public void end() {
            ended = true;
        }
        public void start(Monster monster, char key) {
                switch (key) {
                    case 'Q' :
                        hero.castAbility(0, monster);
                        monster.castAbility(hero);
                        break;
                    case 'W':
                        hero.castAbility(1, monster);
                        monster.castAbility(hero);
                        break;
                    case 'E':
                        hero.castAbility(2, monster);
                        monster.castAbility(hero);
                        break;
                    case '!':
                        // escape from combat
                        this.end();
                        break;
                }
                // check if combat ended
                if (monster.isDead()) {
                    // remove monster from map
                    map.removeMonster(monster);
                    // gain exp
                    hero.gainExp(monster.getExpWorth());
                    // check killed monster complete any quest
                    hero.completeQuest(monster);
                    this.end();
                }
                if (hero.isDead()) {
                    this.end();
                }
        }
    }
    /**
     * Integration test for combat lost
     * 1. Get hero from config
     * 2. Test if hero created correctly
     * 3. Get monster from config
     * 4. Test if monster created correctly
     * 5. Enter Combat to fight with monster
     * 6. Check if combat ended
     * 7. Check if hero is dead and monster is still alive
     * */
    @Test
    public void testCombatLost(){
         //get hero from config
        Hero hero = GameConfig.getInstance().getHeroFromConfig(new Point2D(0,0), "Hunter");
        // test stat of hero
        Stat heroStat = new Stat(200, 200, 10, 10, 20, 5, 5);
        assertTrue(heroStat.equals(hero.getStat()));
        assertEquals(hero.getCurrentHealth(), hero.getStat().getMaxHealth());
        assertEquals(hero.getCurrentMana(), hero.getStat().getMaxMana());
        assertEquals(1, hero.getLevel());
        assertEquals(0, hero.getExp());
        assertEquals(100, hero.getNextLevelExp());
        assertEquals("Hunter", hero.getName());
        assertEquals(4, hero.getSpeed());
        // test if ability added
        assertEquals(3, hero.getAbilities().size());
        // get monster from config
        Monster monster = GameConfig.getInstance().getMonsterFromConfig(new Point2D(32,0), 'O');
        //test monster created
        assertEquals("Orc", monster.getName());
        assertEquals(2, monster.getLevel());
        assertEquals(20 * monster.getLevel(), monster.getExpWorth());
        Stat monsterStat = new Stat(200, 30, 20, 0, 2, 10, 0);
        assertTrue(monsterStat.multiply(monster.getLevel()).equals(monster.getStat()));
        assertEquals(monster.getCurrentMana(), monster.getStat().getMaxMana());
        assertEquals(monster.getCurrentHealth(), monster.getStat().getMaxHealth());
        //create map
        Map map = new Map();
        map.setHero(hero);
        map.getMonsters().put(new Point2D(0, 0), monster);
        //create combat
        CombatForTest combat = new CombatForTest(map);
        int prevHeroHealth = hero.getCurrentHealth();
        int prevMonsterHealth = monster.getCurrentHealth();
        int prevHeroMana = hero.getCurrentMana();
        int prevMonsterMana = monster.getCurrentMana();
        while(!combat.isEnded()){
            combat.start(monster, 'Q');
            // check if health decrease
            assertTrue(hero.getCurrentHealth() < prevHeroHealth);
            assertTrue(monster.getCurrentHealth() < prevMonsterHealth);
            assertTrue(hero.getCurrentMana() < prevHeroMana);
            assertTrue(monster.getCurrentMana() < prevMonsterMana);
            prevHeroMana = hero.getCurrentMana();
            prevMonsterMana = monster.getCurrentMana();
            prevHeroHealth = hero.getCurrentHealth();
            prevMonsterHealth = monster.getCurrentHealth();
        }
        // hero must lose
        assertTrue(hero.isDead());
        assertFalse(monster.isDead());
    }

    /**
     * Integration test for combat
     * 1. Get her from config
     * 2. Test if hero created correctly
     * 3. Get monster from config
     * 4. Test if monster created correctly
     * 5. Read NPC from config
     * 6. Check if npc loaded
     * 7. Interact with npc to get quest
     * 8. Check if quest added
     * 9. Enter Combat to fight with monster
     * 10. Check if hero won
     * 11. Check if objective of quest completed
     * 12. Interact with npc to get item
     */
    @Test
    public void testCombatWinAndCompleteQuest(){
        //get hero from config
        Hero hero = GameConfig.getInstance().getHeroFromConfig(null, "Mage");
        // test stat of hero
        Stat heroStat = new Stat(100, 100, 5, 20, 2, 5, 20);
        assertTrue(heroStat.equals(hero.getStat()));
        assertEquals(hero.getCurrentHealth(), hero.getStat().getMaxHealth());
        assertEquals(hero.getCurrentMana(), hero.getStat().getMaxMana());
        assertEquals(1, hero.getLevel());
        assertEquals(0, hero.getExp());
        assertEquals(100, hero.getNextLevelExp());
        assertEquals("Mage", hero.getName());
        assertEquals(4, hero.getSpeed());
        // test if ability added
        assertEquals(3, hero.getAbilities().size());
        // get monster from config
        Monster monster = GameConfig.getInstance().getMonsterFromConfig(new Point2D(32,0), 'G');
        //test monster created
        Stat monsterStat = new Stat(200, 100, 10, 0, 2, 5, 0);
        assertTrue(monsterStat.equals(monster.getStat()));
        assertEquals(10, monster.getExpWorth());
        assertEquals(1, monster.getLevel());
        assertEquals("Goblin", monster.getName());
        // Read NPC from config
        NPC npc = GameConfig.getInstance().getNPCFromConfig(null, '?');
        assertEquals("Monk", npc.getName());
        Map map = new Map();
        map.setHero(hero);
        map.getMonsters().put(new Point2D(0, 0), monster);
        npc.interact(hero);
        npc.interact(hero);
        npc.interact(hero);
        // check if quest added
        assertEquals(1, hero.getQuests().size());
        // interaction with npc after giving quest
        npc.interact(hero);
        // check if quest not completed
        assertFalse(hero.getQuests().get(0).isCompleted());
        //create combat
        CombatForTest combat = new CombatForTest(map);
        while(!combat.isEnded()){
            combat.start(monster, 'Q');
        }
        // hero must win
        assertFalse(hero.isDead());
        assertTrue(monster.isDead());
        // check if quest completed
        assertTrue(hero.getQuests().get(0).isCompleted());
        npc.interact(hero);
        // check if item given
        assertEquals(1, hero.getInventory().getItems().size());
        assertTrue(hero.getInventory().getItems().get(0).equals(npc.getItemForHero()));
    }
}
