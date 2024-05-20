package com.trinhdin.rpg.model.GameEntity.Item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.controller.LogGameMsg;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Inventory class to store items
 */
public class Inventory implements LogGameMsg {
    @JsonIgnore
    @Getter
    private final int maxSlot = 16;
    @Getter
    private ArrayList<Item> items = new ArrayList<>();
    @JsonIgnore
    private String gameMsg = "";
    public Inventory(){}
    public Inventory(JsonNode node){
        for(JsonNode itemNode : node){
            JsonNode value = itemNode.get("value");
            switch (itemNode.get("key").asText()){
                case "Consumable":
                    items.add(new Consumable(value));
                    break;
                case "Equipment":
                    items.add(new Equipment(value));
                    break;
                case "ObstacleItem":
                    items.add(new ObstacleItem(value));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid item name" + itemNode.get("key").asText());
            }
        }
    }
    /**
     * Add item to inventory
     * @param item
     * @return true if item is added, false if inventory is full
     */
    public boolean addItem(Item item){
        if(items.size() < maxSlot){
            return items.add(item);
        }
        return false;
    }
    /**
     * Remove item from inventory by index
     * @param index
     * @return true if item is removed, false if index is out of bound
     */
    public boolean removeItem(int index){
        if(index < items.size() && index >= 0){
            gameMsg = items.get(index).getName() + " removed from inventory";
            items.remove(index);
            return true;
        }
        return false;
    }
    /**
     * Remove item from inventory by item base on item name
     * @param item
     * @return true if item is removed, false if item is not found
     */
    public boolean removeItem(Item item){
        return items.remove(item);
    }
    /**
     * Use item from inventory by index on target if item is used successfully remove it from inventory
     * @param index
     * @param target
     */
    public void useItem(int index, Hero target){
        if(index >= 0 && index < items.size()){
            if(items.get(index).use(target)){
                gameMsg = items.get(index).getName() + " used";
                removeItem(index);
            }
        }else{
            gameMsg = "No item to use";
        }
    }
    /**
     * Find item in inventory by item name
     * @param itemToFind
     * @return item if found, null if not found
     */
    public Item findItem(Item itemToFind){
        for(Item item : items){
            if(item == itemToFind){
                return item;
            }
        }
        return null;
    }

    @Override
    public String getGameMsg() {
        return gameMsg;
    }
}
