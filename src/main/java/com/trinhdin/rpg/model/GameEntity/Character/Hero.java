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

import java.util.ArrayList;

public class Hero extends Character {
    @JsonIgnore
    private Inventory inventory = new Inventory();
    @JsonIgnore
    private ArrayList<Ability> abilities = new ArrayList<>();
    private ArrayList<Quest> quests = new ArrayList<>();
    private Equipment[] equipments = new Equipment[MAX_EQUIPMENT]; // [weapon, helmet, shield, boots, armor, accessory
    private int exp = 0;
    private int level = 1;
    private int nextLevelExp = 100;
    private Point2D screenPos;
    private final int MAX_ABILITY = 3;
    private static final int MAX_EQUIPMENT = 6;

    public Hero(Point2D pos, String name, String fileName, double speed, Stat stat) {
        super(pos, name, fileName, speed, stat);
    }
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
    public void setScreenPos(Point2D screenPos) {
        this.screenPos = screenPos;
    }

    public Point2D getScreenPos() {
        return screenPos;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void addAbility(Ability ability) {
        if (abilities.size() < MAX_ABILITY) {
            abilities.add(ability);
        }
    }

    public void castAbility(int index, Monster target) {
        if (index >= abilities.size() || index < 0) {
            gameMsg = "Ability not found";
        } else {
            gameMsg = abilities.get(index).getName() + " used on " + target.getName();
            abilities.get(index).use(this, target);
            gameMsg += "\n" + abilities.get(index).getGameMsg();
        }
    }

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

    private boolean equip(int index, Equipment equipment) {
        if (equipments[index] == null) {
            stat.add(equipment.getStatIncrease());
            equipments[index] = equipment;
            return true;
        }
        return false;
    }

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
        return false;
    }
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

    public Inventory getInventory() {
        return inventory;
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
    }

    public void completeQuest(int index, Monster monsterKilled) {
        quests.get(index).complete(monsterKilled);
    }

    public boolean isQuestCompleted(Quest quest) {
        return quests.contains(quest) && quest.getCompleted();
    }

    public void gainExp(int exp) {
        gameMsg = "You gain " + exp + " exp";
        this.exp += exp;
        if (this.exp >= nextLevelExp) {
            levelUp();
        }
    }

    private void levelUp() {
        gameMsg = "Level up";
        level++;
        nextLevelExp *= 2;
        Stat levelUpStat = new Stat(10, 10, 2, 2, 2, 2, 2);
        stat.add(levelUpStat);
    }

    public Rectangle bounds() {
        return new Rectangle(pos.getX(), pos.getY(), WIDTH, HEIGHT);
    }

    public Point2D calculateCenter() {
        return new Point2D(pos.getX() + WIDTH / 2.0, pos.getY() + HEIGHT / 2.0);
    }

    public int getExp() {
        return exp;
    }

    public int getLevel() {return level;}

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }

    public Equipment[] getEquipments() {
        return equipments;
    }

}
