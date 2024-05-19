package com.trinhdin.rpg.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Item.Item;

import java.util.ArrayList;

public class GameConfig {
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<Hero> heroes;
    private ArrayList<Ability> abilities;
//    private ArrayList<Item>
}
