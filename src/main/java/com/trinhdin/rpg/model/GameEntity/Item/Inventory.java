package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.controller.LogGameMsg;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Item.Item;

import java.util.ArrayList;

public class Inventory implements LogGameMsg {
    private final int maxSlot = 16;
    private ArrayList<Item> items = new ArrayList<>();
    private String gameMsg = "";
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
            gameMsg = items.get(index) + " removed from inventory";
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
    public void useItem(int index, Hero target){
        if(index >= 0 && index < items.size()){
            gameMsg = items.get(index).getName() + " used";
            if(items.get(index).use(target))
                removeItem(index);
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
    public int getMaxSlot() {
        return maxSlot;
    }
    public ArrayList<Item> getItems() {
        return items;
    }
    @Override
    public String getGameMsg() {
        return gameMsg;
    }
}
