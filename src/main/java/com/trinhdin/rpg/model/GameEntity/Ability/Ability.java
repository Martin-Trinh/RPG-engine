package com.trinhdin.rpg.model.GameEntity.Ability;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.controller.LogGameMsg;
import com.trinhdin.rpg.model.GameEntity.Character.Character;

public abstract class Ability implements LogGameMsg {
     protected String name;
     protected int cost;
     protected int cooldown;
     @JsonIgnore
     protected String gameMsg = "";
     public Ability(String name, int cost, int cooldown) {
         this.name = name;
         this.cost = cost;
         this.cooldown = cooldown;
     }
    public Ability(JsonNode node){
            name = node.get("name").asText();
            cost = node.get("cost").asInt();
            cooldown = node.get("cooldown").asInt();
     }
    @Override
    public String toString() {
        return "Name: " + name + " - Cost: " + cost + " - Cooldown: " + cooldown;
    }
    public String getName(){
        return name;
    }
    public int getCooldown(){
        return cooldown;
    }

    public abstract void use(Character caster, Character target);
     @Override
    public String getGameMsg(){
        return gameMsg;
    }
}
