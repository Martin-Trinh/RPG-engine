package com.trinhdin.rpg.model.GameEntity.Item;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import javafx.geometry.Point2D;

public class Equipment extends Item{
    private Stat statIncrease;
    private EquipmentType type;

    public Equipment(Point2D pos, String name, String fileName, String description, Stat statIncrease, EquipmentType type) {
        super(pos, name, fileName, description);
        this.statIncrease = statIncrease;
        this.type = type;
    }
    public Equipment(JsonNode node){
        super(node);
        ObjectMapper objectMapper = new ObjectMapper();
        statIncrease = objectMapper.convertValue(node.get("statIncrease"), Stat.class);
        type = EquipmentType.valueOf(node.get("type").asText());
    }

    public Stat getStatIncrease() {
        return statIncrease;
    }
    public EquipmentType getType() {
        return type;
    }
    @Override
    public boolean use(Hero hero) {
        hero.equip(this);
        return true;
    }


}
