package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Item.Item;

import java.util.ArrayList;

public class Inventory {
    private int weight = 0;
    private int maxWeight = 10;
    private ArrayList<Item> items = new ArrayList<>();

    public boolean addItem(Item item){
        if(weight + item.getWeight() < maxWeight){
            items.add(item);
            weight += item.getWeight();
            return true;
        }
        return false;
    }

    public boolean removeItem(int index){
        if(index < items.size()){
            weight -= items.get(index).getWeight();
            items.remove(index);
            return true;
        }
        return false;
    }

    public boolean removeItem(Item item){
        if(items.contains(item)){
            weight -= item.getWeight();
            items.remove(item);
            return true;
        }
        return false;
    }
    public void useItem(int index, Hero target){
        items.get(index).use(target);
        removeItem(index);
    }
    public int getWeight() {
        return weight;
    }

}
