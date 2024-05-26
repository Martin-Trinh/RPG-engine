package com.trinhdin.rpg.model.GameEntity.Character;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.Setter;

/**
 * Monster class to represent monster in game
 */
public class Monster extends Character {
    @Getter
    private int expWorth;
    @Getter
    private int level;
    @Setter
    private Ability ability = null;

    public Monster(Point2D pos, String name, String fileName, double speed, Stat stat, int expWorth, int level) {
        super(pos, name, fileName, speed, stat);
        this.expWorth = expWorth * level;
        this.level = level;
        this.stat = stat.multiply(level);
        currentHealth = stat.getMaxHealth();
        currentMana = stat.getMaxMana();
    }

    public Monster(JsonNode node) {
        super(node);
        expWorth = node.get("expWorth").asInt();
        level = node.get("level").asInt();
    }

    public void castAbility(Hero target) {
        ability.use(this, target);
        gameMsg = ability.getGameMsg();
    }

    public boolean equals(Monster monster) {
        return this.getName().equals(monster.getName());
    }

}
