package com.trinhdin.rpg.model.GameEntity.Item;

import com.fasterxml.jackson.databind.JsonNode;
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
    public Item(JsonNode node){
        super(node);
        description = node.get("description").asText();
    }
    /**
     * Use the item
     * @param hero target
     * @return true if the item is used and will be removed from inventory, false otherwise
     */
    public abstract boolean use(Hero hero);
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    /**
     * Add the item to the inventory
     * @param hero target
     * @return true if the item is added to the inventory and entity will be removed from map, false otherwise
     */
    @Override
    public boolean interact(Hero hero) {
        if(hero.getInventory().addItem(this)){
            gameMsg = name + " added to inventory";
            return true;
        }
        gameMsg = "Inventory is full";
        return false;
    }
    public boolean equals(Item item){
        return this.name.equals(item.getName());
    }

}
