package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.Position;

public abstract class Entity {
    protected Position pos;
    protected int width;
    protected int height;
    protected String name;

    public Entity(Position pos, String name) {
        this.pos = pos;
        this.name = name;
    }
    protected void loadEntity(String imageFileName){
        //TODO
    }

    public Position getPos() {
        return pos;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }
}
