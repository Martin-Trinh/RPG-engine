package com.trinhdin.rpg.model.GameEntity.Character;

import com.trinhdin.rpg.model.GameEntity.Ability.*;
import com.trinhdin.rpg.model.GameEntity.Item.Consumable;
import com.trinhdin.rpg.model.GameEntity.Item.Equipment;
import com.trinhdin.rpg.model.GameEntity.Item.EquipmentType;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HeroTest {
    Hero hero;
    @BeforeEach
    public void setUp() {
        Stat baseStat = new Stat(10, 10, 10, 10, 10, 10, 10);
        hero = new Hero(null, "hero", "hero.png", 1, baseStat);
    }
    @Test
    public void testEquipUnEquipItem() {
        Stat stat = new Stat(10, 10, 10, 10, 10, 10, 10);
        // mock equipment
        Equipment equipmentMock  = mock(Equipment.class);
        when(equipmentMock.getStatIncrease()).thenReturn(stat);
        when(equipmentMock.getType()).thenReturn(EquipmentType.WEAPON);
        when(equipmentMock.getName()).thenReturn("item");
        // equip item
        hero.equip(equipmentMock);
        // check if item is equipped
        assertEquals(equipmentMock, hero.getEquipments()[0]);
        // result stat after equip
        Stat statIncreased = new Stat(20, 20, 20, 20, 20, 20, 20);
        // check if stat of hero is increased by stat of equipment
        assertTrue(hero.getStat().equals(statIncreased));
        // unequip item
        hero.unequip(0);
        // check if stat of hero is decreased by stat of equipment
        assertTrue(hero.getStat().equals(stat));
    }
    @Test
    public void testUnequipNullItem() {
        assertFalse(hero.unequip(0));
        assertFalse(hero.unequip(1));
        assertFalse(hero.unequip(2));
        assertFalse(hero.unequip(3));
        assertFalse(hero.unequip(4));
    }
    @Test
    public void testAddQuestAndCompleteQuest(){
        Quest quest = new Quest("quest", "desc", "monster");
        // add quest to hero
        hero.addQuest(quest);
        // check if quest is added to hero
        assertEquals(hero.getQuests().get(0), quest);
        // check if quest is not completed
        assertFalse(hero.isQuestCompleted(quest));
        Monster monsterMock = mock(Monster.class);
        when(monsterMock.getName()).thenReturn("monster");
        // complete quest
        hero.getQuests().get(0).complete(monsterMock);
        // check if quest is completed
        assertTrue(hero.isQuestCompleted(quest));
    }
    @Test
    public void testGainExpAndLevelUp(){
        int nextLevelExp = hero.getNextLevelExp();
        // check if hero exp is 0
        assertEquals(0, hero.getExp());
        hero.gainExp(nextLevelExp - 1);
        // check if hero haven't level up
        assertEquals(1, hero.getLevel());
        hero.gainExp(1);
        // check if hero level up
        assertEquals(2, hero.getLevel() );
        // check if hero exp is reset
        assertEquals(0, hero.getExp());
        Stat baseStat = new Stat(10, 10, 10, 10, 10, 10, 10);
        // check if hero stat is increased
        assertTrue(hero.getStat().equals(baseStat.add(hero.getLevelUpStat())));
    }
    @Test
    public void testCastAbility(){
        Attack attack = mock(Attack.class);
        Heal heal = mock(Heal.class);
        ModifyStat modifyStat = mock(ModifyStat.class);
        Heal heal1 = mock(Heal.class);
        Monster monster = mock(Monster.class);
        // do nothing when ability is used
        doNothing().when(attack).use(any(), any());
        when(attack.getGameMsg()).thenReturn("attacked");
        when(attack.getName()).thenReturn("attack");
        when(monster.getName()).thenReturn("monster");
        // add abilities to hero
        hero.addAbility(attack);
        hero.addAbility(heal);
        hero.addAbility(modifyStat);
        hero.addAbility(heal1);
        // check if abilities are added
        assertEquals(hero.getAbilities().size(), 3);
        // cast attack ability
        hero.castAbility(0,monster);
        // cast none exists ability
        hero.castAbility(4,null);
        // check if ability is used
        assertEquals("Ability not found", hero.getGameMsg());
    }
    @Test
    public void testUseConsumable(){
        Consumable consumable = new Consumable(null, "consumable", "consumable.png", "desc", 10, 10);
        hero.getInventory().addItem(consumable);
        // set hero health and mana to 0
        hero.setCurrentMana(0);
        hero.setCurrentHealth(0);
        // check if consumable is used
        assertTrue(hero.getInventory().useItem(0, hero));
        assertEquals(10, hero.getCurrentHealth());
        assertEquals(10, hero.getCurrentMana());
        // check if consumable is removed from inventory
        assertEquals(0, hero.getInventory().getItems().size());
    }
}
