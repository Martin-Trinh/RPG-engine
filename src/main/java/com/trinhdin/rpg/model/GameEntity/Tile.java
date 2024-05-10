package com.trinhdin.rpg.model.GameEntity;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Tile extends Entity{
    protected boolean collision;
    public Tile(Point2D pos, String name, String fileName, boolean collision) {
        super(pos, name, fileName);
        this.collision = collision;
    }
    public boolean isCollision() {
        return collision;
    }
}
