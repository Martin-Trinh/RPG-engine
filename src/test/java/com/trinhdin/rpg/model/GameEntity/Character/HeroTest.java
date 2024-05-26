package com.trinhdin.rpg.model.GameEntity.Character;

import com.trinhdin.rpg.model.GameConfig;
import com.trinhdin.rpg.model.GameEntity.Ability.*;
import com.trinhdin.rpg.model.GameEntity.Item.Consumable;
import com.trinhdin.rpg.model.GameEntity.Item.Equipment;
import com.trinhdin.rpg.model.GameEntity.Item.EquipmentType;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
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
    public void testUnequipNullItem() {
        // every slot in hero
        assertFalse(hero.unequip(0));
        assertFalse(hero.unequip(1));
        assertFalse(hero.unequip(2));
        assertFalse(hero.unequip(3));
        assertFalse(hero.unequip(4));
        assertFalse(hero.unequip(5));
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
        verify(attack, times(1)).use(any(), any());
        verify(attack, times(1)).getGameMsg();
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
    /**
     * Integration test
     * 1. Add equipment to inventory
     * 2. Check if item is added to inventory
     * 3. Equip item
     * 4. Check if item is equipped
     * 5. Check if stat of hero is increased by stat of equipment
     * 6. Unequip item
     * 7. Check if stat of hero is decreased by stat of equipment
     */
    @Test
    public void testPickupUnpEquipUnEquipItem() {
        Stat stat = new Stat(10, 10, 10, 10, 10, 10, 10);
        // mock equipment
        Equipment equipment = new Equipment(null, "item", "item.png", "desc", stat, EquipmentType.WEAPON);
        // add item to inventory
        hero.getInventory().addItem(equipment);
        // check if item is added to inventory
        assertEquals(1, hero.getInventory().getItems().size());
        assertEquals(equipment, hero.getInventory().getItems().get(0));
        assertTrue(hero.getInventory().useItem(0, hero));
        // check if item is equipped
        assertEquals(equipment, hero.getEquipments()[0]);
        // result stat after equip
        Stat statIncreased = new Stat(20, 20, 20, 20, 20, 20, 20);
        // check if stat of hero is increased by stat of equipment
        assertTrue(hero.getStat().equals(statIncreased));
        // unequip item
        hero.unequip(0);
        // check if stat of hero is decreased by stat of equipment
        assertTrue(hero.getStat().equals(stat));
    }
    /**
     * Integration test for hero cast ability on monster with equipment on
     * 1. Load hero and monster from config
     * 2. Load equipment from config
     * 3. Add equipment to inventory
     * 4. Equip equipment
     * 5. Check if item is equipped
     * 6. Calculate expected damage
     * 7. Cast attack ability
     * 8. Check if monster health is decreased
     * 9. Check if hero health is decreased
     */
    @Test
    public void testCastAbilityWithEquipment(){
        GameConfig gameConfig = GameConfig.getInstance();
        Hero hero = gameConfig.getHeroFromConfig(null, "Knight");
        Monster monster = gameConfig.getMonsterFromConfig(null, 'T');
        // get equipment from config
        Item weapon = gameConfig.getItemFromConfig(null, 'b');
        Item helmet = gameConfig.getItemFromConfig(null, '^');
        assertEquals(weapon.getName(), "Bow");
        assertEquals(helmet.getName(), "Helmet");
        // get none exists item
        assertNull(gameConfig.getItemFromConfig(null, '}'));
        // add item to inventory
        hero.getInventory().addItem(weapon);
        hero.getInventory().addItem(helmet);
        //calculate expected damage
        int expectedHeroDamage = 2 * (hero.getStat().getStrength() + ((Equipment)weapon).getStatIncrease().getStrength()) - monster.getStat().getArmor();
        int expectedMonsterDamage = 5 * monster.getStat().getIntelligence() - (hero.getStat().getMagicArmor() + ((Equipment)helmet).getStatIncrease().getMagicArmor());
        // equip item
        hero.getInventory().useItem(0, hero);
        hero.getInventory().useItem(0, hero);
        // check if inventory is empty
        assertTrue(hero.getInventory().getItems().isEmpty());
        // check if item is equipped
        assertEquals(weapon, hero.getEquipments()[0]);
        assertEquals(helmet, hero.getEquipments()[1]);
        int prevHeroHealth = hero.getCurrentHealth();
        // cast attack ability
        hero.castAbility(0, monster);
        // check if monster health is decreased
        int expectedMonsterHealth = monster.getStat().getMaxHealth() - expectedHeroDamage;
        assertEquals(expectedMonsterHealth, monster.getCurrentHealth());
        monster.castAbility(hero);
        // check if hero health is decreased
        int expectedHeroHealth = prevHeroHealth - expectedMonsterDamage;
        assertEquals(expectedHeroHealth, hero.getCurrentHealth());
    }

}
