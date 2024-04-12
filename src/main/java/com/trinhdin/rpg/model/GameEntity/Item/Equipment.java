package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.Position;

public class Equipment extends Item{
    private Stat statIncrease;


    public Equipment(Position pos, String name, int weight, Stat statIncrease) {
        super(pos, name,  weight);
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
