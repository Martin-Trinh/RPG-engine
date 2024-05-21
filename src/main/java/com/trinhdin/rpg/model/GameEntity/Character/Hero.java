package com.trinhdin.rpg.model.GameEntity.Character;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Item.Equipment;
import com.trinhdin.rpg.model.GameEntity.Item.EquipmentType;
import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import com.trinhdin.rpg.model.Quest;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
/**
 * Hero class to represent hero in game
 */
public class Hero extends Character {
    @JsonIgnore
    @Getter
    private Inventory inventory = new Inventory();
    @JsonIgnore
    @Getter
    private ArrayList<Ability> abilities = new ArrayList<>();
    @Getter
    private ArrayList<Quest> quests = new ArrayList<>();
    @Getter
    private Equipment[] equipments = new Equipment[MAX_EQUIPMENT]; // [weapon, helmet, shield, boots, armor, accessory
    @Getter
    private int exp = 0;
    @Getter
    private int level = 1;
    @Getter
    private int nextLevelExp = 100;
    @Getter
    @Setter
    private Point2D screenPos;
    private final int MAX_ABILITY = 3;
    private static final int MAX_EQUIPMENT = 6;

    public Hero(Point2D pos, String name, String fileName, double speed, Stat stat) {
        super(pos, name, fileName, speed, stat);
    }
    /**
     * Constructor for hero from json node
     * @param node json node
     */
    public Hero(JsonNode node){
        super(node);
        for(JsonNode questNode : node.get("quests")){
            quests.add(new Quest(questNode));
        }
        for(int i = 0; i < MAX_EQUIPMENT; i++){
            JsonNode equipmentNode = node.get("equipments").get(i);
            if(!equipmentNode.isNull())
                equipments[i] = new Equipment(equipmentNode);
        }
        exp = node.get("exp").asInt();
        level = node.get("level").asInt();
        screenPos = new Point2D(node.get("screenPos").get("x").asDouble(), node.get("screenPos").get("y").asDouble());
    }
    /**
     * Add ability to hero
     * @param ability
     */
    public void addAbility(Ability ability) {
        if (abilities.size() < MAX_ABILITY) {
            abilities.add(ability);
        }
    }
    /**
     * Cast ability on monster
     * @param index index of ability
     * @param target monster to cast ability on
     */
    public void castAbility(int index, Monster target) {
        if (index >= abilities.size() || index < 0) {
            gameMsg = "Ability not found";
        } else {
            gameMsg = abilities.get(index).getName() + " used on " + target.getName();
            abilities.get(index).use(this, target);
            gameMsg += "\n" + abilities.get(index).getGameMsg();
        }
    }
    /**
     * Equip equipment to hero
     * @param equipment
     * @return true if equipped
     */
    public boolean equip(Equipment equipment) {
        switch (equipment.getType()) {
            case WEAPON:
                return equip(0, equipment);
            case HELMET:
                return equip(1, equipment);
            case SHIELD:
                return equip(2, equipment);
            case BOOTS:
                return equip(3, equipment);
            case ARMOR:
                return equip(4, equipment);
            case ACCESSORY:
                return equip(5, equipment);
        }
        return false;
    }
    /**
     * Helper function to equip equipment to hero
     * @param index index of equipment
     * @param equipment
     * @return true if equipped
     */
    private boolean equip(int index, Equipment equipment) {
        if (equipments[index] == null) {
            stat.add(equipment.getStatIncrease());
            equipments[index] = equipment;
            return true;
        }
        return false;
    }
    /**
     * Unequip equipment from hero
     * @param index index of equipment
     * @return true if unequipped
     */
    public boolean unequip(int index) {
        if (equipments[index] != null) {
            // subtract the stat increase from the equipment
            stat.subtract(equipments[index].getStatIncrease());
            // check if current health and mana is greater than max health and mana
            if(currentHealth > stat.getMaxHealth())
                currentHealth = stat.getMaxHealth();
            if(currentMana > stat.getMaxMana())
                currentMana = stat.getMaxMana();
            inventory.addItem(equipments[index]);
            gameMsg = equipments[index].getName() + " unequipped";
            equipments[index] = null;
            return true;
        }
        gameMsg = "Nothing to unequip!";
        return false;
    }
    /**
     * Unequip equipment from hero
     * @param type
     * @return true if unequipped
     */
    public boolean unequip(EquipmentType type) {
        switch (type) {
            case WEAPON:
                return unequip(0);
            case HELMET:
                return unequip(1);
            case SHIELD:
                return unequip(2);
            case BOOTS:
                return unequip(3);
            case ARMOR:
                return unequip(4);
            case ACCESSORY:
                return unequip(5);
        }
        return false;
    }
    /**
     * Add quest to the hero from npc
     * @param quest
     */
    public void addQuest(Quest quest) {
        quests.add(quest);
    }
    /**
     * Complete quest after killing monster
     * @param index
     * @param monsterKilled
     */
    public void completeQuest(int index, Monster monsterKilled) {
        quests.get(index).complete(monsterKilled);
    }

    public boolean isQuestCompleted(Quest quest) {
        return quests.contains(quest) && quest.getCompleted();
    }

    /**
     * Gain exp after killing monster
     * @param exp
     */
    public void gainExp(int exp) {
        gameMsg = "You gain " + exp + " exp";
        this.exp += exp;
        if (this.exp >= nextLevelExp) {
            levelUp();
        }
    }
    /**
     * Level up hero increase stats by level coefficient
     */
    private void levelUp() {
        gameMsg = "Level up";
        level++;
        nextLevelExp *= 2;
        Stat levelUpStat = new Stat(10, 10, 2, 2, 2, 2, 2);
        stat.add(levelUpStat);
    }
    /**
     * Check if hero is in the bounds of the rectangle
     * @param rect
     * @return rectangle around hero
     */
    public Rectangle bounds() {
        return new Rectangle(pos.getX(), pos.getY(), WIDTH, HEIGHT);
    }
    /**
     * Calculate the center of the hero
     * @return center point

     */
    public Point2D calculateCenter() {
        return new Point2D(pos.getX() + WIDTH / 2.0, pos.getY() + HEIGHT / 2.0);
    }


}
