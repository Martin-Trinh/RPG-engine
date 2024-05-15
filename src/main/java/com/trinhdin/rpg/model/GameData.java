package com.trinhdin.rpg.model;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Tile;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;

public class GameData {
    private ArrayList<Entity> entities;
    private ArrayList<Monster> monsters;
    private Hero hero;
    public GameData (ArrayList<Tile> tiles, ArrayList<Entity> entities, ArrayList<Monster> monsters, Hero hero){
        this.entities = entities;
        this.monsters = monsters;
        this.hero = hero;
    }
}
