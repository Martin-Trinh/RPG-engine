package com.trinhdin.rpg.model;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * GameData class to store game data using Jackson
 */
@Getter
public class GameData {
    private int level;
    private ArrayList<Pair<String, Entity>> entities = new ArrayList<>();
    private ArrayList<Monster> monsters;
    private Hero hero;
    private ArrayList<Pair<String, Item>> inventory = new ArrayList<>();

    /**
     * Constructor for GameData convert object to serializable format
     * @param map map object
     */
    public GameData(Map map) {
        for (Entity entity : map.getEntities().values()) {
            this.entities.add(new Pair<>(entity.getClass().getSimpleName(), entity));
        }
        this.monsters = new ArrayList<>(map.getMonsters().values());
        this.hero = map.getHero();
        for (Item item : hero.getInventory().getItems()) {
            inventory.add(new Pair<>(item.getClass().getSimpleName(), item));
        }
        this.level = map.getLevel();
    }
}