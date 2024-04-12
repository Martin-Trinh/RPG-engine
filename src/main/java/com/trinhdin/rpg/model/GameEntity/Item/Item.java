package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.Position;

public abstract class Item extends Entity {
    private String name;
    private String description = "";
    private int weight;

    public Item(Position pos, String name, int weight) {
        super(pos, name);
        this.name = name;
        this.weight = weight;
    }

    public abstract void use(Hero hero);
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public int getWeight() {
        return weight;
    }
}
