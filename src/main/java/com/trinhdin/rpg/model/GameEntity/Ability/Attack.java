package com.trinhdin.rpg.model.GameEntity.Ability;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Character.Character;

public class Attack extends Ability{
    private int damage;
    private AttackType type;
    public Attack(String name, int cost, int cooldown, int damage, AttackType type) {
        super(name, cost, cooldown);
        this.damage = damage;
        this.type = type;
    }
    public Attack(JsonNode node){
        super(node);
        this.damage = node.get("damage").asInt();
        this.type = AttackType.valueOf(node.get("type").asText());
    }
    @Override
    public void use(Character caster, Character target) {
        if(caster.getCurrentMana() < cost) {
            gameMsg = "Not enough mana";
            System.out.println("Not enough mana");
        }else{
            gameMsg = caster.getName() + " dealt " + calculateDamage(caster, target) + " damage to " + target.getName();
            target.setCurrentHealth(target.getCurrentHealth() - calculateDamage(caster, target));
            caster.setCurrentMana(caster.getCurrentMana() - cost);
        }
    }

    private int calculateDamage(Character caster, Character target){
        if(type == AttackType.PHYSICAL){
            return caster.getStat().getStrength() * damage - target.getStat().getArmor();
        }else{
            return caster.getStat().getIntelligence() * damage - target.getStat().getMagicArmor();
        }
    }
}
