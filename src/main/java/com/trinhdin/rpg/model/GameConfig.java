package com.trinhdin.rpg.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Ability.Attack;
import com.trinhdin.rpg.model.GameEntity.Ability.Heal;
import com.trinhdin.rpg.model.GameEntity.Ability.ModifyStat;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Character.Quest;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.GameEntity.Item.*;
import com.trinhdin.rpg.model.GameEntity.NPC;
import com.trinhdin.rpg.model.GameEntity.Obstacle;
import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * GameConfig class to load game configuration from json files
 */
@Slf4j
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
    private final HashMap<String, Stat> heroes = new HashMap<>();
    @Getter
    private ArrayList<String> maps;
    @Getter
    private final static GameConfig instance = new GameConfig();
    JsonNode abilitiesNode, heroNode, itemsNode, monstersNode, npcsNode, obstaclesNode;
    /**
     * Constructor to load game configuration from json files to jsonNode
     */
    private GameConfig(){
        try{
            abilitiesNode = objectMapper.readTree(new File(PATH_PREFIX + abilityFile));
            heroNode = objectMapper.readTree(new File(PATH_PREFIX + heroFile));
            itemsNode = objectMapper.readTree(new File(PATH_PREFIX + itemFile));
            monstersNode = objectMapper.readTree(new File(PATH_PREFIX + monsterFile));
            npcsNode = objectMapper.readTree(new File(PATH_PREFIX + npcFile));
            obstaclesNode = objectMapper.readTree(new File(PATH_PREFIX + obstacleFile));
            maps = objectMapper.readValue(new File(PATH_PREFIX + "Levels.json"), ArrayList.class);
            // load hero names and stats for view
            loadHeroNamesAndStats();
        }
        catch (IOException e){
            log.info("Error loading abilities from file");
            e.printStackTrace();
        }
    }
    /**
     * Load hero names and stats from json file
     */
    public void loadHeroNamesAndStats(){
        heroNode.forEach(node -> {
            heroes.put(node.get("name").asText(), objectMapper.convertValue(node.get("stat"), Stat.class));
        });
    }
    public String getMapPath(int level){
        return maps.get(level);
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
            log.error(e.getMessage());
        }
        return null;
    }
    public void setAbilityForHero(Hero hero){
        for(JsonNode node : heroNode){
            if(node.get("name").asText().equals(hero.getName())){
                JsonNode abilityNode = node.get("abilities");
                for (JsonNode ability : abilityNode){
                    String type = ability.fields().next().getKey();
                    int index = ability.get(type).asInt();
                    hero.addAbility(getAbilityFromConfig(type, index));
                    log.info("Add ability: " + type + " index: " + index);
                }
            }
        }
    }
    public void setAbilityForMonster(Monster monster){
        for(JsonNode node: monstersNode){
            if(node.get("name").asText().equals(monster.getName())){
                JsonNode abilityNode = node.get("abilities");
                String type = abilityNode.fields().next().getKey();
                int index = abilityNode.get(type).asInt();
                monster.setAbility(getAbilityFromConfig(type, index));
                log.info("Add ability: " + type + " index: " + index);
            }
        }
    }

    /**
     * Get hero from config file using jsonNode
     * @param pos position
     * @param name hero name
     * @return Hero
     * @throws IllegalArgumentException not found hero in config file
     */
    public Hero getHeroFromConfig(Point2D pos, String name) throws IllegalArgumentException{
        for(JsonNode node : heroNode){
            if(node.get("name").asText().equals(name)){
                String fileName = node.get("fileName").asText();
                int speed = node.get("speed").asInt();
                Stat stat = objectMapper.convertValue(node.get("stat"), Stat.class);
                Hero hero =  new Hero(pos, name, fileName, speed, stat);
                setAbilityForHero(hero);
                return hero;
            }
        }
        throw new IllegalArgumentException("Hero not found, name is invalid: " + name);
    }

    /**
     * Get monster from config file using jsonNode
     * @param pos position
     * @param c character on config map
     * @return Monster
     */
    public Monster getMonsterFromConfig(Point2D pos, Character c) {
        for(JsonNode node : monstersNode){
            if(node.get("char").asText().equals(c.toString())){
                String name = node.get("name").asText();
                String fileName = node.get("fileName").asText();
                double speed = node.get("speed").asDouble();
                Stat stat = objectMapper.convertValue(node.get("stat"), Stat.class);
                int expWorth = node.get("expWorth").asInt();
                int level = node.get("level").asInt();
                Monster monster = new Monster(pos, name, fileName, speed, stat, expWorth, level);
                setAbilityForMonster(monster);
                return monster;
            }
        }
        return null;
    }
    /**
     * Get node from array of jsonNode base on character representation on map
     * @param c character
     * @param node jsonNode
     * @return jsonNode
     */
    private JsonNode getNodeFromArray(Character c, JsonNode node){
        for(JsonNode n : node){
            if(n.get("char").asText().equals(c.toString())){
                return n;
            }
        }
        return null;
    }

    /**
     * Get item from config file using jsonNode
     * @param pos position
     * @param c character representation on map
     * @return
     */
    public Item getItemFromConfig(Point2D pos, Character c) {
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
        return null;
    }

    /**
     * Get NPC from config file using jsonNode
     * @param pos position
     * @param c character representation on map
     * @return NPC
     */
    public NPC getNPCFromConfig(Point2D pos, Character c) {
        JsonNode node = getNodeFromArray(c, npcsNode);
        if(node != null){
            String name = node.get("name").asText();
            String fileName = node.get("fileName").asText();
            ArrayList<String> dialogues = objectMapper.convertValue(node.get("dialogues"), ArrayList.class);
            int index = node.get("itemForHero").asInt();
            ObstacleItem item = getObstacleItemFromConfig(index);
            Quest quest = new Quest(node.get("questForHero"));
            return new NPC(pos, name, fileName, dialogues, quest, item);
        }
        return null;
    }

    /**
     * Get obstacle from config file using jsonNode
     * @param pos position
     * @param c character representation on map
     * @return  Obstacle
     * @throws IllegalArgumentException cannot find resolve item
     */
    public Obstacle getObstacleFromConfig(Point2D pos, Character c) throws IllegalArgumentException{
        JsonNode node = getNodeFromArray(c, obstaclesNode);
        if(node != null){
            String name = node.get("name").asText();
            String fileName = node.get("fileName").asText();
            ObstacleItem item = getObstacleItemFromConfig(node.get("resolveItem").asInt());
            return new Obstacle(pos, name, fileName, true, item);
        }
        return null;
    }

    /**
     * Get obstacle item from config file using index
     * @param index index
     * @return ObstacleItem
     * @throws IllegalArgumentException invalid index
     */
    private ObstacleItem getObstacleItemFromConfig(int index) throws IllegalArgumentException{
        if(index < 0){
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        JsonNode node = itemsNode.get("ObstacleItem");
        if(index >= node.size()){
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        JsonNode itemNode = node.get(index);
        String name = itemNode.get("name").asText();
        String fileName = itemNode.get("fileName").asText();
        String description = itemNode.get("description").asText();
        return new ObstacleItem(new Point2D(0,0), name, fileName, description);
    }
}

