package com.trinhdin.rpg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinhdin.rpg.model.GameConfig;
import com.trinhdin.rpg.model.GameData;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Item.*;
import com.trinhdin.rpg.model.GameEntity.NPC;
import com.trinhdin.rpg.model.GameEntity.Obstacle;
import com.trinhdin.rpg.model.Map;
import javafx.geometry.Point2D;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class to save and load game from json file
 */
@Slf4j
public class GameSaveLoad {
    ObjectMapper objectMapper = new ObjectMapper();
    GameConfig gameConfig = GameConfig.getInstance();
    private static final String PATH_PREFIX = "src/main/resources/gameData/";

    /**
     * Load game data from json file to jsonNode using Jackson library
     *
     * @param fileName file name
     * @param map      map object
     * @throws IOException if file not found
     */
    public void loadGame(String fileName, Map map) throws IOException {
        try {
            log.info("Loading game from " + fileName);
            JsonNode node = objectMapper.readTree(new File(PATH_PREFIX + fileName));
            map.setLevel(node.get("level").asInt());
            map.setHero(loadHero(node));
            map.setEntities(loadEntities(node.get("entities")));
            map.setMonsters(loadMonsters(node.get("monsters")));
        } catch (IOException e) {
            log.error("Error loading game from file: " + PATH_PREFIX + fileName);
            throw new IOException("Error loading game from file: " + PATH_PREFIX + fileName);
        }
    }

    /**
     * Save game data to json file using Jackson library
     *
     * @param data     game data
     * @param fileName file name
     * @throws IOException if file not found
     */
    public void saveGame(GameData data, String fileName) throws IOException {
        try {
            objectMapper.writeValue(new File(PATH_PREFIX + fileName), data);
        } catch (IOException e) {
            log.error("Error saving game to file: " + PATH_PREFIX + fileName);
            throw new IOException("Error saving game to file: " + PATH_PREFIX + fileName);
        }
    }

    /**
     * Load hero from json node
     *
     * @param node json node
     * @return hero object
     */
    private Hero loadHero(JsonNode node) {
        Inventory inventory = new Inventory(node.get("inventory"));
        Hero hero = new Hero(node.get("hero"));
        gameConfig.setAbilityForHero(hero);
        for (Item item : inventory.getItems()) {
            hero.getInventory().addItem(item);
        }
        log.info("Hero loaded: " + hero.getName());
        return hero;
    }

    /**
     * Load all monsters from json node to hashmap
     *
     * @param node json node
     * @return monsters hashmap
     */
    private HashMap<Point2D, Monster> loadMonsters(JsonNode node) {
        HashMap<Point2D, Monster> monsters = new HashMap<>();
        for (JsonNode monsterNode : node) {
            Monster monster = new Monster(monsterNode);
            gameConfig.setAbilityForMonster(monster);
            monsters.put(monster.getPos(), monster);
        }
        log.info("Monsters loaded: " + monsters.size());
        return monsters;
    }

    /**
     * Load all entities from json node to hashmap
     *
     * @param node json node
     * @return entities hashmap
     * @throws IllegalArgumentException if entity name is invalid
     */
    private HashMap<Point2D, Entity> loadEntities(JsonNode node) throws IllegalArgumentException {
        HashMap<Point2D, Entity> entities = new HashMap<>();
        for (JsonNode entityNode : node) {
            JsonNode value = entityNode.get("value");
            switch (entityNode.get("key").asText()) {
                case "ObstacleItem":
                    ObstacleItem item = new ObstacleItem(value);
                    entities.put(item.getPos(), item);
                    break;
                case "Equipment":
                    Equipment equipment = new Equipment(value);
                    entities.put(equipment.getPos(), equipment);
                    break;
                case "Obstacle":
                    Obstacle obstacle = new Obstacle(value);
                    entities.put(obstacle.getPos(), obstacle);
                    break;
                case "Consumable":
                    Consumable consumable = new Consumable(value);
                    entities.put(consumable.getPos(), consumable);
                    break;
                case "NPC":
                    NPC npc = new NPC(value);
                    entities.put(npc.getPos(), npc);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid entity name" + entityNode.get("key").asText());
            }
        }
        log.info("Entities loaded: " + entities.size());
        return entities;
    }

}
