package com.trinhdin.rpg.model;

public abstract class Entity {
    protected Position pos;
    protected int width;
    protected int height;
    protected String name;

    public Entity(Position pos, int width, int height, String name) {
        this.pos = pos;
        this.width = width;
        this.height = height;
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
