package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import javafx.geometry.Point2D;

public class ObstacleItem extends Item{
    public ObstacleItem(Point2D pos, String name, String fileName, String description) {
        super(pos, name, fileName, description);
    }

    @Override
    public void use(Hero hero) {
        System.out.println(name + "\n" + description);
        // find the nearest obstacle to the hero and remove it
    }

}
