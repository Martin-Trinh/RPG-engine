package com.trinhdin.rpg.model;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Item.Consumable;
import com.trinhdin.rpg.model.GameEntity.Item.Equipment;
import com.trinhdin.rpg.model.GameEntity.NPC;
import com.trinhdin.rpg.model.GameEntity.Obstacle;
import com.trinhdin.rpg.model.GameEntity.Tile;
import javafx.geometry.Point2D;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Map {
    private HashMap<Point2D, Tile> tiles = new HashMap<>();
    private HashMap<Point2D, Entity> entities = new HashMap<>();
    private Hero hero;
    private int width;
    private int height;
    private final String prefixImgPath = "file:src/main/resources/img/";

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Hero getHero() {return hero;}
    public HashMap<Point2D, Tile> getTileMap() {
        return tiles;
    }

    public HashMap<Point2D, Entity> getEntities() {
        return entities;
    }

    public void addTile(Point2D pos, Tile entity){
        tiles.put(pos, entity);
    }
    public void addMapEntity(Point2D pos, Entity entity){
        entities.put(pos, entity);
    }
    public void loadTileMap(String mapFileName) throws IOException {
        int maxLength = 0;
        int lineCnt;
        try {
            FileInputStream inputStream = new FileInputStream(mapFileName);
            Scanner scanner = new Scanner(inputStream);
            for (lineCnt = 0; scanner.hasNextLine(); lineCnt++) {
                String line = scanner.nextLine();
                if(line.length() > maxLength){
                    maxLength = line.length();
                }
                for (int i = 0; i < line.length(); i++) {
                    Point2D pos = new Point2D(i, lineCnt);
                    char c = line.charAt(i);
                    loadCharToEntity(pos, c);
                }
//                System.out.println(line);
            }
            width = maxLength * Entity.getWidth();
            height = lineCnt * Entity.getHeight();
            scanner.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            throw new IOException("File not found");
        }
    }
    public void loadCharToEntity(Point2D pos, char c){
        Stat stat = new Stat(10, 10, 10, 10, 10, 10, 10);
        if(c == '#'){
                Tile wall = new Tile(pos, "wall", prefixImgPath + "wall.png", true);
                tiles.put(pos, wall);
        }else if(c == ' '){

        }else{
            Tile floor = new Tile(pos, "floor", prefixImgPath + "floor_5.png", false);
            tiles.put(pos, floor);
            switch (c){
                case '@':
                    hero = new Hero(pos.multiply(Entity.getWidth()), "Knight", prefixImgPath + "Heroes/knight_run.png", 2, stat);
                    break;
                case 'X':
                    Monster monster = new Monster(pos, "Goblin", prefixImgPath + "Monster/goblin_run.png", 1, stat, 10, 1);
                    entities.put(pos, monster);
                    break;
                case 'p':
                    Consumable potion = new Consumable(pos, "Potion", prefixImgPath + "Item/potion.png", "Health Potion", 10, 10, 0);
                    entities.put(pos, potion);
                    break;
                case 'e':
                    Equipment sword = new Equipment(pos, "Sword", prefixImgPath + "Item/sword.png", "Sword", 10, stat);
                    entities.put(pos, sword);
                    break;
                case '-':
                    Obstacle gate = new Obstacle(pos, "Gate", prefixImgPath + "Obstacle/gate.png", true,null, "Locked gate");
                    entities.put(pos, gate);
                    break;
                case '?':
                    NPC monk = new NPC(pos, "Monk", prefixImgPath + "NPC/monk.png", null, null, null);
                    entities.put(pos, monk);
                    break;
                default:
            }
        }
    }

}
