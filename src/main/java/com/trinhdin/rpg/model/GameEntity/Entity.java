package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.controller.LogGameMsg;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Entity implements LogGameMsg {
    static protected final int HEIGHT = 32;
    static protected final int WIDTH = 32;
    protected Point2D pos;
    protected String fileName;
    protected String name;
    protected Image image;
    protected String gameMsg;

    public Entity(Point2D pos, String name, String fileName) {
        this.pos = pos;
        this.name = name;
        this.fileName = fileName;
        try{
            this.image = new Image(fileName);
        } catch (Exception e) {
            System.out.println("Error loading image: " + fileName);
        }
    }
    @Override
    public String getGameMsg(){
        return gameMsg;
    }
    static public int getWidth() {
        return WIDTH;
    }
    static public int getHeight() {return HEIGHT;}
    public Point2D getPos() {return pos;}
    public String getFileName() {
        return fileName;
    }
    public Image getImage() {
        return image;
    }
    public Point2D getCenter() {
        return new Point2D(pos.getX() * WIDTH + WIDTH /2.0, pos.getY() * HEIGHT +  HEIGHT /2.0);
    }
    public String getName() {
        return name;
    }
    public Rectangle getBounds() {
        return new Rectangle(pos.getX() * WIDTH, pos.getY() * HEIGHT, WIDTH, HEIGHT);
    }

}
