package com.trinhdin.rpg.model;

import java.util.HashMap;

public class Map {
    private HashMap<Position, Entity> map = new HashMap<>();
    public Map(){

    }

    public map getMap() {
        return map;
    }
}
