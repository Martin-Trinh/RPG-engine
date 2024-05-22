package com.trinhdin.rpg.model.GameEntity.Ability;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbilityTest {
    Hero hero;
    Monster monster;
    @BeforeEach
    public void setUp() {
        Stat heroStat = new Stat(10, 10, 10, 10, 10, 10, 5);
        Stat monsterStat = new Stat(10, 10, 10, 5, 10, 10, 5);
        hero = new Hero(null, "hero", "hero.png", 1, heroStat);
        monster = new Monster(null, "monster", "monster.png", 1, monsterStat, 10, 1);
    }
    @Test
    public void testAttackAbility(){
        Attack attackPhysical = new Attack("attack", 1 ,1,2, AttackType.PHYSICAL);
        Attack attackMagical = new Attack("attack", 1 ,1,2, AttackType.MAGICAL);
        attackPhysical.use(hero, monster);
        int expectedHealth = monster.getStat().getMaxHealth() - 2*hero.getStat().getStrength() + monster.getStat().getArmor();
        assertEquals(expectedHealth, monster.getCurrentHealth());
        assertEquals(9, hero.getCurrentMana());
        expectedHealth = hero.getStat().getMaxHealth() - 2*monster.getStat().getIntelligence() + hero.getStat().getMagicArmor();
        attackMagical.use(monster, hero);
        assertEquals(expectedHealth, hero.getCurrentHealth());
        assertEquals(9, monster.getCurrentMana());
    }
    @Test
    public void testHealAbility(){
        Heal heal = new Heal("heal", 1, 1, 2);
        hero.setCurrentHealth(5);
        heal.use(hero, hero);
        assertEquals(7, hero.getCurrentHealth());
        assertEquals(9, hero.getCurrentMana());
    }
    @Test
    public void testModifyStatAbility(){
        Stat buffStat = new Stat(-1, -1, -1, -1, -1, -1, -1);
        ModifyStat buff = new ModifyStat("buff",1,1, buffStat,1);
        buff.use(hero, hero);
        assertEquals(9, hero.getStat().getMaxHealth());
        assertEquals(9, hero.getStat().getMaxMana());
        assertEquals(9, hero.getStat().getStrength());
        assertEquals(9, hero.getStat().getIntelligence());
        assertEquals(9, hero.getStat().getAgility());
        assertEquals(9, hero.getStat().getArmor());
        assertEquals(4, hero.getStat().getMagicArmor());
        assertEquals(9, hero.getCurrentMana());
    }
    @Test
    public void testZeroManaUse(){
        Attack attack = new Attack("attack", 10, 1, 2, AttackType.PHYSICAL);
        ModifyStat buffStat = new ModifyStat("buff",1,1, null ,1);
        Heal heal = new Heal ("heal", 10, 1, 2);
        hero.setCurrentMana(0);
        attack.use(hero, monster);
        assertEquals("Not enough mana", attack.getGameMsg());
        monster.setCurrentMana(0);
        buffStat.use(monster, monster);
        assertEquals("Not enough mana", attack.getGameMsg());
        heal.use(monster, monster);
        assertEquals("Not enough mana", attack.getGameMsg());
    }
}
