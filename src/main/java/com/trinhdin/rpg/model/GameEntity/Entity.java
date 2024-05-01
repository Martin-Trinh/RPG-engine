package com.trinhdin.rpg.model.GameEntity;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Entity {
    static protected final int HEIGHT = 32;
    static protected final int WIDTH = 32;
    protected Point2D pos;
    protected String fileName;
    protected String name;
    protected Image image;

    public Entity(Point2D pos, String name, String fileName) {
        this.pos = pos;
        this.name = name;
        this.fileName = fileName;
        this.image = new Image(fileName);
    }
    static public int getWidth() {
        return WIDTH;
    }
    public Point2D getPos() {
        return pos;
    }

    static public int getHeight() {
        return HEIGHT;
    }

    public String getFileName() {
        return fileName;
    }
    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

}
