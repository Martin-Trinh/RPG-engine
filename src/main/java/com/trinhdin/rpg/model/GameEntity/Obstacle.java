package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Item.Item;
import javafx.geometry.Point2D;

public class Obstacle extends Entity {
    Item resolveItem;
    String description;
    public Obstacle(Point2D pos, String name, String fileName, Item resolveItem) {
        super(pos, name, fileName);
        this.resolveItem = resolveItem;
    }

    public void applyItem() {}
}
