package com.trinhdin.rpg.model.GameEntity.Item;

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

    public Stat getStatIncrease() {
        return statIncrease;
    }
    @Override
    public boolean use(Hero hero) {
        hero.equip(this);
        return true;
    }
    public EquipmentType getType() {
        return type;
    }

}
