package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import javafx.geometry.Point2D;

public class Consumable extends Item {
    private int health;
    private int mana;

    public Consumable(Point2D pos, String name, String fileName, String description, int weight, int health, int mana) {
        super(pos, name, fileName, description, weight);
        this.health = health;
        this.mana = mana;
    }

    @Override
    public void use(Hero hero) {
        hero.setCurrentHealth(hero.getCurrentHealth() + health);
        hero.setCurrentMana(hero.getCurrentMana() + mana);
    }


}
