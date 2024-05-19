package com.trinhdin.rpg.model.GameEntity;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Tile extends Entity{
    protected boolean collision;
    public Tile(Point2D pos, String name, String fileName, boolean collision) {
        super(pos, name, fileName);
        this.collision = collision;
    }
    public Tile(JsonNode node) {
        super(node);
        this.collision = node.get("collision").asBoolean();
    }
    public boolean isCollision() {
        return collision;
    }
}
