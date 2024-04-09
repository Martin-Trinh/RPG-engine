package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.model.Entity;

public class Item extends Entity {
    private String name;
    private String description;
    private int value;
    private int weight;

    public Item(String name, String description, int value, int weight) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }
}
