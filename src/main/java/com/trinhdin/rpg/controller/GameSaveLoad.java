package com.trinhdin.rpg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinhdin.rpg.model.GameData;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Item.*;
import com.trinhdin.rpg.model.GameEntity.NPC;
import com.trinhdin.rpg.model.GameEntity.Obstacle;
import com.trinhdin.rpg.model.Map;
import javafx.geometry.Point2D;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GameSaveLoad {
    ObjectMapper objectMapper = new ObjectMapper();
    private static final String PATH_PREFIX = "src/main/resources/gameData/";

    public void loadGame(String fileName, Map map) {
        try {
            JsonNode node = objectMapper.readTree(new File(PATH_PREFIX + fileName));
            Hero hero = loadHero(node);
            loadEntities(node.get("entities"));
            loadMonsters(node.get("monsters"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGame(GameData data, String fileName) {
        try {
            objectMapper.writeValue(new File(PATH_PREFIX + fileName), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Hero loadHero(JsonNode node) {
        Inventory inventory = new Inventory(node.get("inventory"));
        Hero hero = new Hero(node.get("hero"));
        for(Item item : inventory.getItems()){
            hero.getInventory().addItem(item);
        }
        return hero;
    }
    private void loadEntities(JsonNode node) {
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
        System.out.println(entities.size());
    }
    private void loadMonsters(JsonNode node) {
        HashMap<Point2D, Monster> monsters = new HashMap<>();
        for (JsonNode monsterNode : node) {
            Monster monster = new Monster(monsterNode);
            monsters.put(monster.getPos(), monster);
        }
        System.out.println(monsters.size());
    }
}
