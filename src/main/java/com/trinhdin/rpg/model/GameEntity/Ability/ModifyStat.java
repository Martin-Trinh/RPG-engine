package com.trinhdin.rpg.model.GameEntity.Ability;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinhdin.rpg.model.GameEntity.Character.Character;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;

/**
 * Ability to modify stat of character
 */
public class ModifyStat extends Ability {
    private Stat stat;
    private int duration;

    public ModifyStat(String name, int cost, int cooldown, Stat stat, int duration) {
        super(name, cost, cooldown);
        this.stat = stat;
        this.duration = duration;
    }

    public ModifyStat(JsonNode node) {
        super(node);
        ObjectMapper objectMapper = new ObjectMapper();
        this.stat = objectMapper.convertValue(node.get("stat"), Stat.class);
    }

    @Override
    public void use(Character caster, Character target) {
        if (caster.getCurrentMana() < cost) {
            gameMsg = "Not enough mana";
        } else {
            gameMsg = name + " used - ";
            gameMsg += caster.getName() + " buffed " + target.getName();
            target.getStat().add(stat);
            caster.setCurrentMana(caster.getCurrentMana() - cost);
        }
    }

    public int getDuration() {
        return duration;
    }

    public Stat getStat() {
        return stat;
    }
}
