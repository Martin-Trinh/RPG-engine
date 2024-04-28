package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import javafx.geometry.Point2D;

public class Equipment extends Item{
    private Stat statIncrease;


    public Equipment(Point2D pos, String name, String fileName, String description, int weight, Stat statIncrease) {
        super(pos, name, fileName, description, weight);
        this.statIncrease = statIncrease;
    }

    public Stat getStatIncrease() {
        return statIncrease;
    }
    @Override
    public void use(Hero hero) {
        hero.getStat().modifyStat(statIncrease);
    }

}
