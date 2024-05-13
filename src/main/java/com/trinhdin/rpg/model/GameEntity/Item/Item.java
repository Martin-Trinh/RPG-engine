package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Interactable;
import javafx.geometry.Point2D;

public abstract class Item extends Entity implements Interactable{
    protected String description = "";
    public Item(Point2D pos, String name, String fileName, String description) {
        super(pos, name, fileName);
        this.description = description;
    }

    public abstract void use(Hero hero);
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean interact(Hero hero) {
        if(hero.getInventory().addItem(this)){
            System.out.println("Item added to inventory");
            return true;
        }
        System.out.println("Inventory is full");
        return false;
    }

    public boolean equals(Item item){
        return this.name.equals(item.getName());
    }
}
