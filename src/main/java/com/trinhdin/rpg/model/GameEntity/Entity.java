package com.trinhdin.rpg.model.GameEntity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.controller.LogGameMsg;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.HashMap;

public class Entity implements LogGameMsg {
    static protected final int HEIGHT = 32;
    static protected final int WIDTH = 32;
    protected Point2D pos;
    protected String name;
    protected String fileName;
    @JsonIgnore
    protected Image image;
    @JsonIgnore
    protected String gameMsg = "";
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
    public Entity(JsonNode node) {
        this.pos = new Point2D(node.get("pos").get("x").asDouble(), node.get("pos").get("y").asDouble());
        this.name = node.get("name").asText();
        this.fileName = node.get("fileName").asText();
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
    public Point2D calculateCenter() {
        return new Point2D(pos.getX() * WIDTH + WIDTH /2.0, pos.getY() * HEIGHT +  HEIGHT /2.0);
    }
    public String getName() {
        return name;
    }
    public Rectangle bounds() {
        return new Rectangle(pos.getX() * WIDTH, pos.getY() * HEIGHT, WIDTH, HEIGHT);
    }
    public void setPos(Point2D pos) {
        this.pos = pos;
    }

}
