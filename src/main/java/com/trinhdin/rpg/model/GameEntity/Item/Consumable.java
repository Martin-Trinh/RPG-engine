package com.trinhdin.rpg.model.GameEntity.Item;

public class Consumable extends Item {
    private int health;
    private int mana;

    public Consumable(String name, int health, int mana) {
        super(name);
        this.health = health;
        this.mana = mana;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }
}
