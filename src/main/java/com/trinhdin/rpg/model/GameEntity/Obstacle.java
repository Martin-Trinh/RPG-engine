package com.trinhdin.rpg.model.GameEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.GameEntity.Item.ObstacleItem;
import javafx.geometry.Point2D;
/**
 * Obstacle class to represent obstacle entity
 */
public class Obstacle extends Tile implements Interactable{
    private ObstacleItem resolveItem;
    public Obstacle(Point2D pos, String name, String fileName, boolean collision, ObstacleItem resolveItem) {
        super(pos, name, fileName, collision);
        this.resolveItem = resolveItem;
    }
    public Obstacle(JsonNode node){
        super(node);
        this.resolveItem = new ObstacleItem(node.get("resolveItem"));
    }
    public void resolveObstacle() {
        collision = false;
    }

    @Override
    public boolean interact(Hero hero) {
        Item item = hero.getInventory().findItem(resolveItem);
        if(item != null){
            gameMsg = "Obstacle resolved";
            resolveObstacle();
            hero.getInventory().removeItem(item);
            return true;
        }

        gameMsg = "You need " + resolveItem.getName() + " to resolve this obstacle";
        return false;
    }

    public ObstacleItem getResolveItem() {
        return resolveItem;
    }
}
