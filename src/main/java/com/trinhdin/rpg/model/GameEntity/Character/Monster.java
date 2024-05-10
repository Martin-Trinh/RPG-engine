package com.trinhdin.rpg.model.GameEntity.Character;

import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Ability.AttackType;
import com.trinhdin.rpg.model.GameEntity.Interactable;
import javafx.geometry.Point2D;

public class Monster extends Character {
    private int expWorth;
    private int level;
    Ability ability;

    public Monster(Point2D pos, String name, String fileName, double speed, Stat stat, int expWorth, int level) {
        super(pos, name, fileName, speed, stat);
        this.expWorth = expWorth * level;
        this.level = level;
        int defaultHealth = 5;
        int defaultMana = 5;
        int defaultStrength = 1;
        int defaultIntelligence = 1;
        int defaultAgility = 1;
        int defaultArmor = 1;
        int defaultMagicArmor = 1;
        Stat levelStat = new Stat(defaultHealth*level,
                                defaultMana*level,
                                defaultStrength*level,
                                defaultIntelligence*level,
                                defaultAgility*level,
                                defaultArmor*level,
                                defaultMagicArmor*level);
        stat.modifyStat(levelStat);
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }
    public int getExpWorth() {
        return expWorth;
    }
    public int getLevel() {
        return level;
    }

}
