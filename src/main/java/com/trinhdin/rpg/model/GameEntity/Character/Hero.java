package com.trinhdin.rpg.model.GameEntity.Character;

import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.Position;

import java.util.ArrayList;

public class Hero extends Character {
    Inventory inventory = new Inventory();
    ArrayList<Ability> abilities = new ArrayList<>();
    int exp = 0;
    int nextLevelExp = 100;
    int level = 1;

    public Hero(Position pos, String name, int speed, Stat stat) {
        super(pos, name, speed, stat);
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public void castAbility(int index, Monster target) {
        abilities.get(index).use(this, target);
    }

    public void useItem(int index) {
        inventory.useItem(index, this);
    }

    public void pickupItem(Item item) {
        inventory.addItem(item);
    }
    public void dropItem(int index) {
        inventory.removeItem(index);
    }

    public void gainExp(int exp) {
        this.exp += exp;
        if (this.exp >= nextLevelExp) {
            levelUp();
        }
    }
    private void levelUp() {
        level++;
        nextLevelExp *= 2;
        Stat levelUpStat = new Stat(10, 10, 2, 2, 2, 2, 2);
        stat.modifyStat(levelUpStat);
    }

}
