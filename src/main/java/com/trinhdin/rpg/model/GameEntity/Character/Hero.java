package com.trinhdin.rpg.model.GameEntity.Character;

import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Ability.AttackType;
import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.Quest;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Hero extends Character {
    Inventory inventory = new Inventory();
    ArrayList<Ability> abilities = new ArrayList<>();
    ArrayList<Quest> quests = new ArrayList<>();
    int exp = 0;
    int nextLevelExp = 100;
    int level = 1;
    private Point2D screenPos;
    private final int MAX_ABILITY = 3;
    public Hero(Point2D pos, String name,String fileName, double speed, Stat stat) {
        super(pos, name, fileName, speed, stat);
    }
    public void setScreenPos(Point2D screenPos) {
        this.screenPos = screenPos;
    }
    public Point2D getScreenPos() {
        return screenPos;
    }
    public void addAbility(Ability ability) {
        if(abilities.size() < MAX_ABILITY){
            abilities.add(ability);
        }
    }

    public void castAbility(int index, Monster target) {
        if(index >= abilities.size() || index < 0) {
            gameMsg = "Ability not found";
            System.out.println("Ability not found");
        }else{
            abilities.get(index).use(this, target);
        }
    }

    public void useItem(int index) {
        inventory.useItem(index, this);
    }

    public Inventory getInventory() {
        return inventory;
    }
    public void addQuest(Quest quest) {
        quests.add(quest);
    }
    public void completeQuest(int index, Monster monsterKilled){
        quests.get(index).complete(monsterKilled);
    }
    public boolean isQuestCompleted(Quest quest){
        return quests.contains(quest) && quest.isCompleted();
    }
    public void gainExp(int exp) {
        gameMsg = "You gain " + exp + " exp";
        this.exp += exp;
        if (this.exp >= nextLevelExp) {
            levelUp();
        }
    }
    private void levelUp() {
        gameMsg = "Level up";
        level++;
        nextLevelExp *= 2;
        Stat levelUpStat = new Stat(10, 10, 2, 2, 2, 2, 2);
        stat.modifyStat(levelUpStat);
    }

    public Rectangle getBounds(){
        return new Rectangle(pos.getX(), pos.getY(), WIDTH, HEIGHT);
    }
    public Point2D getCenter(){
        return new Point2D(pos.getX() + WIDTH/2.0, pos.getY() + HEIGHT/2.0);
    }
    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }
    public ArrayList<Ability> getAbilities() {
        return abilities;
    }
    public ArrayList<Quest> getQuests (){
        return quests;
    }
}
