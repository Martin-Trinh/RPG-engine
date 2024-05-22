package com.trinhdin.rpg.model.GameEntity.Ability;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Character.Character;
/**
 * Attack class to represent attack ability of character
 */
public class Heal extends Ability{
    private int amount = 0;
    public Heal(String name, int cost, int cooldown, int amount){
        super(name, cost, cooldown);
        if(amount <= 0)
            throw new IllegalArgumentException("Invalid health or mana value");
      this.amount = amount;
    }
    public Heal(JsonNode node){
        super(node);
        this.amount = node.get("amount").asInt();
    }
    @Override
    public void use(Character caster, Character target) {
        if(caster.getCurrentMana() < cost) {
            gameMsg = "Not enough mana";
        }else{
            gameMsg = caster.getName() + " healed " + target.getName() + " for " + amount + " health";
            target.setCurrentHealth(target.getCurrentHealth() + amount);
            caster.setCurrentMana(caster.getCurrentMana() - cost);
        }
    }
}
