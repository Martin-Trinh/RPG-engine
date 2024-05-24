package com.trinhdin.rpg.model.GameEntity.Item;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import javafx.geometry.Point2D;

/**
 * ObstacleItem class to represent obstacle items
 */
public class ObstacleItem extends Item {
    public ObstacleItem(Point2D pos, String name, String fileName, String description) {
        super(pos, name, fileName, description);
    }

    /**
     * Constructor for loading from json
     *
     * @param node json node
     */
    public ObstacleItem(JsonNode node) {
        super(node);
    }

    /**
     * Item can not be use directly just print description for player to read
     *
     * @param hero target
     * @return false always item cannot be removed from inventory
     */
    @Override
    public boolean use(Hero hero) {
        gameMsg = description;
        return false;
    }

}
