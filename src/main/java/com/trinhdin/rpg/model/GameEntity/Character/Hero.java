package com.trinhdin.rpg.model.GameEntity.Character;

import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.Quest;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Hero extends Character {
    Inventory inventory = new Inventory();
    ArrayList<Ability> abilities = new ArrayList<>();
    ArrayList<Quest> quests = new ArrayList<>();
    int exp = 0;
    int nextLevelExp = 100;
    int level = 1;

    public Hero(Point2D pos, String name,String fileName, double speed, Stat stat) {
        super(pos, name, fileName, speed, stat);
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
    public void addQuest(Quest quest) {
        quests.add(quest);
    }
    public void completeQuest(int index) {
        quests.get(index).complete();
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
