package com.trinhdin.rpg.model;

import com.trinhdin.rpg.model.GameEntity.*;
import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Ability.AttackAbility;
import com.trinhdin.rpg.model.GameEntity.Ability.AttackType;
import com.trinhdin.rpg.model.GameEntity.Ability.ModifyStatAbility;
import com.trinhdin.rpg.model.GameEntity.Character.Character;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.GameEntity.Item.Consumable;
import com.trinhdin.rpg.model.GameEntity.Item.Equipment;
import com.trinhdin.rpg.model.GameEntity.Item.ObstacleItem;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Map {
    private HashMap<Point2D, Tile> tiles = new HashMap<>();
    private HashMap<Point2D, Entity> entities = new HashMap<>();
    private HashMap<Point2D, Monster> monsters = new HashMap<>();
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
    public HashMap<Point2D, Monster> getMonsters() {return monsters;}

    public void addTile(Point2D pos, Tile entity){
        tiles.put(pos, entity);
    }
    public void addMapEntity(Point2D pos, Entity entity){
        entities.put(pos, entity);
    }
    public void removeEntity(Entity entity){entities.remove(entity.getPos());}
    public void removeMonster(Monster monster){monsters.remove(monster.getPos());}
    public Entity returnCollisionEntity(Point2D newPos, MoveDirection direction){
        int [] corner = hero.calculateNewPosition4Corner(newPos);
        int leftX = corner[0];
        int topY = corner[1];
        int rightX = corner[2];
        int bottomY = corner[3];

        Entity entity1 = null, entity2 = null;
        if(direction == MoveDirection.UP){
            entity1 = entities.get(new Point2D(leftX, topY));
            entity2 = entities.get(new Point2D(rightX, topY));
        }else if(direction == MoveDirection.DOWN) {
            entity1 = entities.get(new Point2D(leftX, bottomY));
            entity2 = entities.get(new Point2D(rightX, bottomY));
        }else if (direction == MoveDirection.LEFT){
            entity1 = entities.get(new Point2D(leftX, topY));
            entity2 = entities.get(new Point2D(leftX, bottomY));
        }else if(direction == MoveDirection.RIGHT){
            entity1 = entities.get(new Point2D(rightX, topY));
            entity2 = entities.get(new Point2D(rightX, bottomY));
        }
        if(entity1 != null && entity2 != null){
           // return closer entity
            if(hero.getPos().distance(entity1.getPos()) < hero.getPos().distance(entity2.getPos()))
                return entity1;
            return entity2;
       }
        if(entity1 != null){
            return entity1;
        }
        return entity2;
    }
    public Tile[] return2CollisionTile(Point2D newPos, MoveDirection direction) {
        int [] corner = hero.calculateNewPosition4Corner(newPos);
        int leftX = corner[0];
        int topY = corner[1];
        int rightX = corner[2];
        int bottomY = corner[3];
        Tile [] tiles = new Tile[2];

        if(direction == MoveDirection.UP){
            tiles[0] = this.tiles.get(new Point2D(leftX, topY));
            tiles[1] = this.tiles.get(new Point2D(rightX, topY));
        }else if(direction == MoveDirection.DOWN){
            tiles[0] = this.tiles.get(new Point2D(leftX, bottomY));
            tiles[1] = this.tiles.get(new Point2D(rightX, bottomY));
        }else if(direction == MoveDirection.LEFT){
            tiles[0] = this.tiles.get(new Point2D(leftX, topY));
            tiles[1] = this.tiles.get(new Point2D(leftX, bottomY));
        }else if(direction == MoveDirection.RIGHT){
            tiles[0] = this.tiles.get(new Point2D(rightX, topY));
            tiles[1] = this.tiles.get(new Point2D(rightX, bottomY));
        }
        return tiles;
    }
    public Entity isCollideWithEntity(){
        Bounds heroBound = hero.getBounds().getBoundsInParent();
       for (Entity entity : entities.values()) {
           if(heroBound.intersects(entity.getBounds().getBoundsInParent())){
               return entity;
           }
       }
       return null;
    }
    public Monster isMonsterNearby(){
        // distance from monster
        int distance = 30;
        for (Monster monster : monsters.values()) {
            if(hero.getCenter().distance(monster.getCenter()) < distance){
                return monster;
            }
        }
        return null;
    }
    public boolean isCharacterCollide(MoveDirection direction) throws IllegalStateException {
        Point2D newPos;
        switch (direction) {
            case UP:
                newPos = hero.getPos().add(0, -hero.getSpeed());
                break;
            case DOWN:
                newPos = hero.getPos().add(0, hero.getSpeed());
                break;
            case LEFT:
                newPos = hero.getPos().add(-hero.getSpeed(), 0);
                break;
            case RIGHT:
                newPos = hero.getPos().add(hero.getSpeed(), 0);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        // check tile collision
        Tile[] tiles = return2CollisionTile(newPos, direction);
        if((tiles[0] != null && tiles[0].isCollision()) || (tiles[1] != null && tiles[1].isCollision())){
            return true;
        }
        // check entity collision
        Entity entity = returnCollisionEntity(newPos , direction);
        return entity != null;
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
        ObstacleItem key = new ObstacleItem(pos, "Key", prefixImgPath + "Items/key.png", "Key for gate");
        if(c == '#'){
                Tile wall = new Tile(pos, "wall", prefixImgPath + "wall.png", true);
                tiles.put(pos, wall);
        }else if(c == ' '){

        }else{
            Tile floor = new Tile(pos, "floor", prefixImgPath + "floor_5.png", false);
            Monster monster = new Monster(pos, "Goblin", prefixImgPath + "Monster/goblin_run.png", 1, stat, 10, 1);
            tiles.put(pos, floor);
            Ability pAbility = new AttackAbility("Attack", 10, 1, 1, AttackType.MAGICAL );
            Ability mAbility = new AttackAbility("Attack", 10, 1, 1, AttackType.PHYSICAL );
            Stat abilityStat = new Stat(10, 10, 0, 1, 1, 1, 1);
            Ability buffAbility = new ModifyStatAbility("Buff", 10, 1, abilityStat );
            switch (c){
                case '@':
                    hero = new Hero(pos.multiply(Entity.getWidth()), "Knight", prefixImgPath + "Heroes/knight_run.png", 2, stat);
                    hero.addAbility(pAbility);
                    hero.addAbility(buffAbility);
                    break;
                case 'X':
                    monster.setAbility(mAbility);
                    monsters.put(pos, monster);
                    break;
                case 'p':
                    Consumable potion = new Consumable(pos, "Potion", prefixImgPath + "Items/flasks_red.png", "Health Potion", 10, 10);
                    entities.put(pos, potion);
                    break;
                case 'e':
                    Equipment sword = new Equipment(pos, "Sword", prefixImgPath + "Items/weapons/weapon01crystalsword.png", "Sword", stat);
                    entities.put(pos, sword);
                    break;
                case '-':
                    Obstacle gate = new Obstacle(pos, "Gate", prefixImgPath + "Obstacle/door_closed.png", true,key);
                    entities.put(pos, gate);
                    break;
                case '?':
                    ArrayList<String> dialogues = new ArrayList<>(Arrays.asList("Hello", "I have a quest for you"));
                    Quest quest = new Quest("Quest", "Find the key", monster);
                    NPC monk = new NPC(pos, "Monk", prefixImgPath + "NPC/monk.png", dialogues, quest, key);
                    entities.put(pos, monk);
                    break;
                case 'k':
                    entities.put(pos, key);
                    break;
                default:
            }
        }
    }

}
