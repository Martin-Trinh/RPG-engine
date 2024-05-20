package com.trinhdin.rpg.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Ability.Attack;
import com.trinhdin.rpg.model.GameEntity.Ability.Heal;
import com.trinhdin.rpg.model.GameEntity.Ability.ModifyStat;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.GameEntity.Item.*;
import com.trinhdin.rpg.model.GameEntity.NPC;
import com.trinhdin.rpg.model.GameEntity.Obstacle;
import javafx.geometry.Point2D;
import lombok.Getter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameConfig {
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String PATH_PREFIX = "src/main/resources/gameConfig/";
    private static final String abilityFile = "Abilities.json";
    private static final String heroFile = "Hero.json";
    private static final String itemFile = "Item.json";
    private static final String monsterFile = "Monsters.json";
    private static final String npcFile = "NPC.json";
    private static final String obstacleFile = "Obstacle.json";
    @Getter
    private ArrayList<String> maps;
    @Getter
    private static GameConfig instance = new GameConfig();
    JsonNode abilitiesNode, heroNode, itemsNode, monstersNode, npcsNode, obstaclesNode, levelsNode;
    private GameConfig(){
        try{
            abilitiesNode = objectMapper.readTree(new File(PATH_PREFIX + abilityFile));
            heroNode = objectMapper.readTree(new File(PATH_PREFIX + heroFile));
            itemsNode = objectMapper.readTree(new File(PATH_PREFIX + itemFile));
            monstersNode = objectMapper.readTree(new File(PATH_PREFIX + monsterFile));
            npcsNode = objectMapper.readTree(new File(PATH_PREFIX + npcFile));
            obstaclesNode = objectMapper.readTree(new File(PATH_PREFIX + obstacleFile));
            maps = objectMapper.readValue(new File("Levels"), maps.getClass());
            for (String map : maps){
                System.out.println(map);
            }
        }
        catch (IOException e){
            System.out.println("Error loading abilities from file");
            e.printStackTrace();
        }
    }
    public Ability getAbilityFromConfig(String type, int index){
        Ability newAbility = null;
        try{
            if(index < 0 ){
                throw new IllegalArgumentException("Invalid index: " + index);
            }
            JsonNode abilityArray;
            switch (type){
                case "Attack":
                     abilityArray = abilitiesNode.get(type);
                    if(index >= abilityArray.size()){
                        throw new IllegalArgumentException("Invalid index: " + index);
                    }
                    newAbility = new Attack(abilityArray.get(index));
                    break;
                case "ModifyStat":
                    abilityArray = abilitiesNode.get(type);
                    if(index >= abilityArray.size()){
                        throw new IllegalArgumentException("Invalid index: " + index);
                    }
                    newAbility = new ModifyStat(abilityArray.get(index));
                    break;
                case "Heal":
                    abilityArray = abilitiesNode.get(type);
                    if(index >= abilityArray.size()){
                        throw new IllegalArgumentException("Invalid index: " + index);
                    }
                    newAbility = new Heal(abilityArray.get(index));
                    break;
                default:

                    throw new IllegalArgumentException("Invalid ability type" + type);
            }
            return newAbility;
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Hero getHeroFromConfig(Point2D pos, String name) throws IllegalArgumentException{
        for(JsonNode node : heroNode){
            if(heroNode.get("name").asText().equals(name)){
                String fileName = heroNode.get("fileName").asText();
                int speed = heroNode.get("speed").asInt();
                Stat stat = objectMapper.convertValue(heroNode.get("stat"), Stat.class);
                Hero hero =  new Hero(pos, name, fileName, speed, stat);
                JsonNode abilityNode = heroNode.get("abilities");
                for (JsonNode ability : abilityNode){
                    hero.addAbility(getAbilityFromConfig(ability.fields().next().getKey(), ability.get("index").asInt()));
                }
                return hero;
            }
        }
        throw new IllegalArgumentException("Hero not found, name is invalid" + name);
    }
    public Monster getMonsterFromConfig(Point2D pos, Character c) throws IllegalArgumentException{
        for(JsonNode node : monstersNode){
            if(node.get("char").asText().equals(c.toString())){
                String name = node.get("name").asText();
                String fileName = node.get("fileName").asText();
                double speed = node.get("speed").asDouble();
                Stat stat = objectMapper.convertValue(node.get("stat"), Stat.class);
                int expWorth = node.get("expWorth").asInt();
                int level = node.get("level").asInt();
                Monster monster = new Monster(pos, name, fileName, speed, stat, expWorth, level);
                JsonNode abilityNode = node.get("abilities");
                monster.setAbility(getAbilityFromConfig(abilityNode.fields().next().getKey(), abilityNode.get("index").asInt()));
                return monster;
            }
        }
        throw new IllegalArgumentException("Monster not found, character is invalid" + c.toString());
    }
    private JsonNode getNodeFromArray(Character c, JsonNode node){
        for(JsonNode n : node){
            if(n.get("char").asText().equals(c.toString())){
                return n;
            }
        }
        return null;
    }
    public Item getItemFromConfig(Point2D pos, Character c) throws IllegalArgumentException{
        Item item = null;
        JsonNode consumableNode = itemsNode.get("Consumable");
        JsonNode node = getNodeFromArray(c, consumableNode);
        if(node != null){
            String name = node.get("name").asText();
            String fileName = node.get("fileName").asText();
            String description = node.get("description").asText();
            int health = node.get("health").asInt();
            int mana = node.get("mana").asInt();
            return new Consumable(pos, name, fileName, description, health, mana);
        }
        JsonNode equipmentNode = itemsNode.get("Equipment");
        node = getNodeFromArray(c, equipmentNode);
        if(node != null){
            String name = node.get("name").asText();
            String fileName = node.get("fileName").asText();
            String description = node.get("description").asText();
            Stat stat = objectMapper.convertValue(node.get("stat"), Stat.class);
            EquipmentType type = EquipmentType.valueOf(node.get("type").asText());
            return new Equipment(pos, name, fileName, description, stat, type);
        }
        JsonNode obstacleItemNode = itemsNode.get("ObstacleItem");
        node = getNodeFromArray(c, obstacleItemNode);
        if(node != null){
            String name = node.get("name").asText();
            String fileName = node.get("fileName").asText();
            String description = node.get("description").asText();
            return new ObstacleItem(pos, name, fileName,description);
        }
        throw new IllegalArgumentException("Item not found, character is invalid" + c.toString());
    }
    public NPC getNPCFromConfig(Point2D pos, Character c) throws IllegalArgumentException{
        JsonNode node = getNodeFromArray(c, npcsNode);
        if(node != null){
            String name = node.get("name").asText();
            String fileName = node.get("fileName").asText();
            ArrayList<String> dialogues = objectMapper.convertValue(node.get("dialogues"), ArrayList.class);
            ObstacleItem item = new ObstacleItem(node.get("itemForHero"));
            Quest quest = new Quest(node.get("questForHero"));
            return new NPC(pos, name, fileName, dialogues, quest, item);
        }
        throw new IllegalArgumentException("NPC not found, character is invalid" + c.toString());
    }
    public Obstacle getObstacleFromConfig(Point2D pos, Character c) throws IllegalArgumentException{
        JsonNode node = getNodeFromArray(c, obstaclesNode);
        if(node != null){
            String name = node.get("name").asText();
            String fileName = node.get("fileName").asText();
            ObstacleItem item = getObstacleItemFromConfig(node.get("resolveItem").asInt());
            return new Obstacle(pos, name, fileName, true, item);
        }
        throw new IllegalArgumentException("Obstacle not found, character is invalid" + c.toString());
    }
    private ObstacleItem getObstacleItemFromConfig(int index) throws IllegalArgumentException{
        if(index < 0){
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        JsonNode node = obstaclesNode.get("ObstacleItem");
        if(index >= node.size()){
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        return new ObstacleItem(node.get(index));
    }
}

