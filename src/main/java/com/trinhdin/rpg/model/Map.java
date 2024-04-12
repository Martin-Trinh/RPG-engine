package com.trinhdin.rpg.model;

import com.trinhdin.rpg.model.GameEntity.Entity;

import java.util.HashMap;

public class Map {
    private HashMap<Position, Entity> map = new HashMap<>();
    public HashMap<Position, Entity> getMap() {
        return map;
    }
    public void addEntity(Position pos, Entity entity){
        map.put(pos, entity);
    }
}
