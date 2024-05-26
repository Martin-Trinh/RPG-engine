package com.trinhdin.rpg.model.GameEntity.Ability;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Character.Character;

/**
 * Attack class to represent attack ability of character
 */
public class Attack extends Ability {
    private int damage;
    private AttackType type;

    public Attack(String name, int cost, int cooldown, int damage, AttackType type) {
        super(name, cost, cooldown);
        this.damage = damage;
        this.type = type;
    }

    public Attack(JsonNode node) {
        super(node);
        this.damage = node.get("damage").asInt();
        this.type = AttackType.valueOf(node.get("attackType").asText());
    }

    @Override
    public void use(Character caster, Character target) {
        if (caster.getCurrentMana() < cost) {
            gameMsg = "Not enough mana";
        } else {
            gameMsg = name + " used - ";
            int damage = calculateDamage(caster, target);
            gameMsg += caster.getName() + " dealt " + damage + " damage to " + target.getName();
            target.setCurrentHealth(target.getCurrentHealth() - damage);
            caster.setCurrentMana(caster.getCurrentMana() - cost);
        }
    }

    private int calculateDamage(Character caster, Character target) {
        int res;
        if (type == AttackType.PHYSICAL) {
            res = caster.getStat().getStrength() * damage - target.getStat().getArmor();
        } else {
            res = caster.getStat().getIntelligence() * damage - target.getStat().getMagicArmor();
        }
        return Math.max(res, 0);
    }
}
