package com.trinhdin.rpg.model.GameEntity.Character;

import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import javafx.geometry.Point2D;

public class Monster extends Character{
    private int expWorth;
    Ability ability;

    public Monster(Point2D pos, String name, String fileName, double speed, Stat stat, int expWorth, int level, Ability ability) {
        super(pos, name, fileName, speed, stat);
        this.expWorth = expWorth * level;
        this.ability = ability;
        int levelCount = level - 1;
        Stat levelStat = new Stat(5*levelCount, 5*levelCount, 1*levelCount, 1*levelCount,1*levelCount,1*levelCount,1*levelCount);
        stat.modifyStat(levelStat);
    }

    public int getExpWorth() {
        return expWorth;
    }
}
