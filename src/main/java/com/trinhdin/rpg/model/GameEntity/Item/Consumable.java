package com.trinhdin.rpg.model.GameEntity.Item;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import javafx.geometry.Point2D;
import lombok.Getter;
/**
 * Consumable class to represent consumable items
 */
@Getter
public class Consumable extends Item {
    private int health;
    private int mana;

    public Consumable(Point2D pos, String name, String fileName, String description, int health, int mana) {
        super(pos, name, fileName, description);
        this.health = health;
        this.mana = mana;
    }
    public Consumable(JsonNode node){
        super(node);
        health = node.get("health").asInt();
        mana = node.get("mana").asInt();
    }
    /**
     * Use consumable
     * @param hero target
     * @return true if used, false if hero is full health and mana
     */
    @Override
    public boolean use(Hero hero) {
        // use if hero is not full health or mana
        if(hero.getCurrentHealth() < hero.getStat().getMaxHealth() || hero.getCurrentMana() < hero.getStat().getMaxMana()){
            hero.setCurrentHealth(hero.getCurrentHealth() + health);
            hero.setCurrentMana(hero.getCurrentMana() + mana);
            return true;
        }
        gameMsg = "Cannot use " + this.getName() + " hero is full health and mana";
        return false;
    }
}
