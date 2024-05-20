package com.trinhdin.rpg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import javafx.geometry.Point2D;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * GameData class to store game data using Jackson
 */
public class GameData {
    private ArrayList<Pair<String, Entity>> entities = new ArrayList<>();
    private ArrayList<Monster> monsters;
    private Hero hero;
    private ArrayList<Pair<String, Item>> inventory = new ArrayList<>();
    public GameData(HashMap<Point2D, Entity> entities,
                    HashMap<Point2D,Monster> monsters,
                    Hero hero) {
        for(Entity entity : entities.values()){
            this.entities.add(new Pair<>(entity.getClass().getSimpleName(), entity));
        }
        this.monsters = new ArrayList<>(monsters.values());
        this.hero = hero;
        for(Item item : hero.getInventory().getItems()){
            inventory.add(new Pair<>(item.getClass().getSimpleName(), item));
        }
    }
    public ArrayList<Pair<String, Entity>> getEntities() {
        return entities;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Pair<String, Item>> getInventory() {
        return inventory;
    }
}