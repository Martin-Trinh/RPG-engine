package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import javafx.geometry.Point2D;

public class Obstacle extends Tile implements Interactable{
    private Item resolveItem;

    public Obstacle(Point2D pos, String name, String fileName, boolean collision, Item resolveItem) {
        super(pos, name, fileName, collision);
        this.resolveItem = resolveItem;
    }
    public void resolveObstacle() {
        collision = false;
    }

    @Override
    public boolean interact(Hero hero) {
      Item item = hero.getInventory().findItem(resolveItem.getName());
        if(item != null){
            resolveObstacle();
            hero.getInventory().removeItem(item);
            return true;
        }
        System.out.println("You need " + resolveItem.getName() + " to resolve this obstacle");
        return false;
    }
}
