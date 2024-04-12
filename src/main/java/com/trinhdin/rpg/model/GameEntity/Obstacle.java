package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.Position;

public class Obstacle extends Entity {
    Item resolveItem;
    String description;
    public Obstacle(Position pos, String name, Item resolveItem) {
        super(pos, name);
        this.resolveItem = resolveItem;
    }

    public void applyItem() {}
}
