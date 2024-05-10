package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Item.Item;

import java.util.ArrayList;

public class Inventory {
    private final int maxSlot = 12;
    private ArrayList<Item> items = new ArrayList<>();

    public boolean addItem(Item item){
        if(items.size() < maxSlot){
            return items.add(item);
        }
        return false;
    }
    public boolean removeItem(int index){
        if(index < items.size() && index >= 0){
            items.remove(index);
            return true;
        }
        return false;
    }
    public boolean removeItem(Item item){
        return items.remove(item);
    }
    public void useItem(int index, Hero target){
        items.get(index).use(target);
        removeItem(index);
    }
    public ArrayList<Item> getItems() {
        return items;
    }
}
