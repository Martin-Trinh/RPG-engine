package com.trinhdin.rpg.model.GameEntity.Item;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import javafx.geometry.Point2D;

public class ObstacleItem extends Item{
    public ObstacleItem(Point2D pos, String name, String fileName, String description) {
        super(pos, name, fileName, description);
    }
    public ObstacleItem(JsonNode node){
        super(node);
    }
    @Override
    public boolean use(Hero hero) {
        System.out.println(name + "\n" + description);
        // find the nearest obstacle to the hero and remove it
        return false;
    }

}
