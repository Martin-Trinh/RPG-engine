package com.trinhdin.rpg.model.GameEntity.Character;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import javafx.geometry.Point2D;

public class Monster extends Character {
    private int expWorth;
    private int level;
    private Ability ability;
    private Item dropItem = null;

    public Monster(Point2D pos, String name, String fileName, double speed, Stat stat, int expWorth, int level) {
        super(pos, name, fileName, speed, stat);
        this.expWorth = expWorth * level;
        this.level = level;
        this.stat = stat.multiply(level);
    }
    public Monster(JsonNode node){
        super(node);
        expWorth = node.get("expWorth").asInt();
        level = node.get("level").asInt();
    }
    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public void setDropItem(Item dropItem) {
        this.dropItem = dropItem;
    }

    public int getExpWorth() {
        return expWorth;
    }
    public int getLevel() {
        return level;
    }
    public boolean equals(Monster monster){
        return this.getName().equals(monster.getName());
    }

}
