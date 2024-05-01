package com.trinhdin.rpg.model;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.GameEntity.Entity;
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
                default:
            }
        }
    }
    public Hero getHero() {
        return hero;
    }
}
