package com.trinhdin.rpg.model.GameEntity.Item;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import javafx.geometry.Point2D;
import lombok.Getter;

/**
 * Equipment class to represent equipment items
 */
@Getter
public class Equipment extends Item {
    private Stat statIncrease;
    private EquipmentType type;

    public Equipment(Point2D pos, String name, String fileName, String description, Stat statIncrease, EquipmentType type) {
        super(pos, name, fileName, description);
        this.statIncrease = statIncrease;
        this.type = type;
    }

    public Equipment(JsonNode node) {
        super(node);
        ObjectMapper objectMapper = new ObjectMapper();
        statIncrease = objectMapper.convertValue(node.get("statIncrease"), Stat.class);
        type = EquipmentType.valueOf(node.get("type").asText());
    }

    /**
     * Equip the equipment to the hero
     *
     * @param hero the hero to equip the equipment
     * @return true if the equipment is equipped successfully
     */
    @Override
    public boolean use(Hero hero) {
        if (hero.equip(this)) {
            gameMsg = hero.getName() + " equipped " + this.getName();
            return true;
        }
        gameMsg = "Cannot equip " + this.getName() + " unequip equipment first";
        return false;
    }


}
