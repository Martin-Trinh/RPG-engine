package com.trinhdin.rpg.model.GameEntity;

import javafx.geometry.Point2D;

public class Entity {
    protected Point2D pos;
    static protected final int WIDTH = 20;
    static protected final int HEIGHT = 20;
    protected String fileName;
    protected String name;

    public Entity(Point2D pos, String name, String fileName) {
        this.pos = pos;
        this.name = name;
        this.fileName = fileName;
    }
    public Point2D getPos() {
        return pos;
    }
    static public int getWidth() {
        return WIDTH;
    }

    static public int getHeight() {
        return HEIGHT;
    }

    public String getFileName() {
        return fileName;
    }

    public String getName() {
        return name;
    }

}
