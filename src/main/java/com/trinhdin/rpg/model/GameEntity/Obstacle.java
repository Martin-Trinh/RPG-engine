package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Item.Item;
import javafx.geometry.Point2D;

public class Obstacle extends Tile {
    Item resolveItem;
    String description;

    public Obstacle(Point2D pos, String name, String fileName, boolean collision, Item resolveItem, String description) {
        super(pos, name, fileName, collision);
        this.resolveItem = resolveItem;
        this.description = description;
    }

    public void resolveObstacle(Item item) {

    }
}
