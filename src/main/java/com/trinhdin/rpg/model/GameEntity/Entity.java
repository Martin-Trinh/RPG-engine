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
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
@Slf4j
public class Entity implements LogGameMsg {
    static protected final int HEIGHT = 32;
    static protected final int WIDTH = 32;
    @Getter
    @Setter
    protected char symbol;
    @Getter
    @Setter
    protected Point2D pos;
    @Getter
    protected String name;
    @Getter
    protected String fileName;
    @JsonIgnore
    @Getter
    protected Image image;
    @JsonIgnore
    protected String gameMsg = "";
    private static final String IMAGE_PATH = "file:src/main/resources/img/";

    public Entity(Point2D pos, String name, String fileName) {
        this.pos = pos;
        this.name = name;
        this.fileName = fileName;
        try{
            this.image = new Image(IMAGE_PATH + fileName);
        } catch (Exception e) {
            log.warn("Error loading image: " + fileName);
        }
    }
    public Entity(JsonNode node) {
        JsonNode posNode = node.get("pos");
        this.pos = new Point2D(posNode.get("x").asDouble(), posNode.get("y").asDouble());
        this.name = node.get("name").asText();
        this.fileName = node.get("fileName").asText();
        try{
            this.image = new Image(IMAGE_PATH + fileName);
            if(image.isError()){
                log.warn("Error loading image: " + fileName);
            }
        } catch (Exception e) {
            log.warn("Error loading image: " + fileName);
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
    /**
     * Calculate the center of the entity
     * @return center point of the entity
     */
    public Point2D calculateCenter() {
        return new Point2D(pos.getX() * WIDTH + WIDTH /2.0, pos.getY() * HEIGHT +  HEIGHT /2.0);
    }
    /**
     * Return the rectangle around the entity
     * @param entity
     * @return rectangle around entity
     */
    public Rectangle bounds() {
        return new Rectangle(pos.getX() * WIDTH, pos.getY() * HEIGHT, WIDTH, HEIGHT);
    }

}
