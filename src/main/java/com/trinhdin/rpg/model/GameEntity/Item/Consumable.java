package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.Position;

public class Consumable extends Item {
    private int health;
    private int mana;

    public Consumable(Position pos, String name, String name1, String imageFileName, int weight, int health, int mana) {
        super(pos, name, weight);
        this.health = health;
        this.mana = mana;
    }

    @Override
    public void use(Hero hero) {
        hero.setCurrentHealth(hero.getCurrentHealth() + health);
        hero.setCurrentMana(hero.getCurrentMana() + mana);
    }

}
