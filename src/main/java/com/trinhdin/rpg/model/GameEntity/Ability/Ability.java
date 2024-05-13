package com.trinhdin.rpg.model.GameEntity.Ability;

import com.trinhdin.rpg.model.GameEntity.Character.Character;

public abstract class Ability {
     String name;
     int cost;
     int cooldown;
     String gameMsg = "";
     public Ability(String name, int cost, int cooldown) {
         this.name = name;
         this.cost = cost;
         this.cooldown = cooldown;
     }

    @Override
    public String toString() {
        return "Name: " + name + " - Cost: " + cost + " - Cooldown: " + cooldown;
    }
    public String getName(){
        return name;
    }

    public abstract void use(Character caster, Character target);

}
