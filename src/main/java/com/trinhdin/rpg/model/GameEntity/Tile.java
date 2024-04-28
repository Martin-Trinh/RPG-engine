package com.trinhdin.rpg.model.GameEntity;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Tile extends Entity{
    private boolean collision;
    public Tile(Point2D pos, String name, String fileName, boolean collision) {
        super(pos, name, fileName);
        this.collision = collision;
    }

    public Rectangle getBounds() {
        return new Rectangle(pos.getX(), pos.getY(), WIDTH, HEIGHT);
    }
    public boolean isCollision() {
        return collision;
    }
}
