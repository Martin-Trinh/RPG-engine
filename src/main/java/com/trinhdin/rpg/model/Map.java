package com.trinhdin.rpg.model;

import com.trinhdin.rpg.model.GameEntity.*;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Item.*;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
/**
 * Map class that represents game map
 */
@Getter
@Slf4j
public class Map {
    private static final String PATH_PREFIX= "src/main/resources/gameMap/";
    @Setter
    // level of the map
    @Getter
    private int level;
    private final HashMap<Point2D, Tile> tiles = new HashMap<>();
    @Setter
    private HashMap<Point2D, Entity> entities = new HashMap<>();
    @Setter
    private HashMap<Point2D, Monster> monsters = new HashMap<>();
    @Setter
    private Hero hero;
    public void removeEntity(Entity entity){entities.remove(entity.getPos());}
    public void removeMonster(Monster monster){monsters.remove(monster.getPos());}

    /**
     * Check collision with entity
     * @param newPos new position of the hero
     * @param direction direction of the movement
     * @return entity if hero is collided with entity, null otherwise
     */
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
    /**
     * Return 2 tiles that hero is going to collide with
     * @param newPos new position of hero
     * @param direction direction of the movement
     * @return 2 tiles that hero is going to collide with
     */
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

    /**
     * Check if character is collided with entity using rectangle intersection
     * @return entity if hero is collided with entity, null otherwise
     */
    public Entity isCollideWithEntity(){
        Bounds heroBound = hero.bounds().getBoundsInParent();
       for (Entity entity : entities.values()) {
           if(heroBound.intersects(entity.bounds().getBoundsInParent())){
               return entity;
           }
       }
       return null;
    }
    /**
     * Check if monster is nearby hero
     * @return monster if monster is nearby, null otherwise
     */
    public Monster isMonsterNearby(){
        // distance from monster
        int distance = 30;
        for (Monster monster : monsters.values()) {
            if(hero.calculateCenter().distance(monster.calculateCenter()) < distance){
                return monster;
            }
        }
        return null;
    }
    /**
     * Check if character is collided with wall or other entity
     * @param direction direction of the movement
     * @return true if character is collided with wall or other entity
     */
    public boolean isCharacterCollide(MoveDirection direction) {
        Point2D newPos = hero.getPos();
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
                log.error("Unexpected direction: " + direction);
                return true;
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

    /**
     * Load map from file of character
     * @param level level of the map
     * @param loadEntity if true, load entity to map, otherwise just load character to tile
     * @throws IndexOutOfBoundsException level is not found in configuration
     * @throws IOException error reading map file
     */
    public void loadMap(int level, boolean loadEntity) throws IndexOutOfBoundsException, IOException{
        this.level = level;
        log.info("Loading map level: " + level);
        String mapPath = GameConfig.getInstance().getMapPath(level);
        try {
            int maxLength = 0;
            int lineCnt;
            FileInputStream inputStream = new FileInputStream(PATH_PREFIX + mapPath);
            Scanner scanner = new Scanner(inputStream);
            // read map line per line
            for (lineCnt = 0; scanner.hasNextLine(); lineCnt++) {
                String line = scanner.nextLine();
                if (line.length() > maxLength) {
                    maxLength = line.length();
                }
                for (int i = 0; i < line.length(); i++) {
                    Point2D pos = new Point2D(i, lineCnt);
                    char c = line.charAt(i);
                    if(!loadCharToTile(pos, c) && loadEntity){
                        loadCharToEntity(pos, c);
                    }
                }
            }
            scanner.close();
            inputStream.close();
        }catch (FileNotFoundException e) {
            throw new FileNotFoundException("Map file not found: " + PATH_PREFIX + mapPath);
        }catch (IOException e){
            throw new IOException("Error reading map file: " + PATH_PREFIX + mapPath);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid character in map file: " + e.getMessage());
        }
    }
    /**
     * Configure hero from game init configuration
     * @param name hero name
     * @throws IllegalArgumentException if hero name is not found in configuration
     */
    public void configureHero(String name) throws IllegalArgumentException{
        log.info("Configuring hero: " + name);
        hero = GameConfig.getInstance().getHeroFromConfig(new Point2D(0,0), name);
    }
    /**
     * Load character to tile and add to map
     * @param pos position of the character
     * @param c character
     * @return true if character is loaded to tile, false if character is not a tile
     */
    private boolean loadCharToTile(Point2D pos,char c){
        Tile floor = new Tile(pos, "floor", "floor_5.png", false);
        switch (c){
            case '#': // wall
                Tile wall = new Tile(pos, "wall", "wall.png", true);
                tiles.put(pos, wall);
                return true;
            case ' ': // empty space
                return true;
            case '.': // floor
                tiles.put(pos, floor);
                return true;
            default: // other character
                tiles.put(pos, floor);
                return false;
        }
    }

    /**
     * Load character to entity from game init configuration and add to map
     * @param pos position of the character
     * @param c character
     */
    private void loadCharToEntity(Point2D pos, char c){
        GameConfig config = GameConfig.getInstance();
        if(c == '@'){
            hero.setPos(pos.multiply(Entity.getWidth()));
            return;
        }
        Monster monster1 = config.getMonsterFromConfig(pos, c);
        if(monster1 != null){
            monsters.put(pos, monster1);
            return;
        }
        Item item = config.getItemFromConfig(pos, c);
        if(item != null){
            entities.put(pos, item);
            return;
        }
        Obstacle obstacle = config.getObstacleFromConfig(pos, c);
        if(obstacle != null){
            entities.put(pos, obstacle);
            return;
        }
        NPC npc = config.getNPCFromConfig(pos, c);
        if(npc != null){
            entities.put(pos, npc);
            return;
        }
        log.warn("Invalid character in map file: " + c);
    }

}
