package com.trinhdin.rpg.model.GameEntity.Character;

import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.MoveDirection;
import com.trinhdin.rpg.model.Position;

public class Character extends Entity {
    protected int speed;
    protected MoveDirection direction;
    protected Stat stat;
    protected int currentHealth;
    protected int currentMana;

    public Character(Position pos, String name, int speed, Stat stat) {
        super(pos, name);
        this.speed = speed;
        this.stat = stat;
        currentHealth = stat.getMaxHealth();
        currentMana = stat.getMaxMana();
    }

    public int getSpeed() {
        return speed;
    }
    public MoveDirection getDirection() {
        return direction;
    }

    public void moveUp() {
        this.pos.setY(this.pos.getY() - speed);
    }
    public void moveDown() {
        this.pos.setY(this.pos.getY() + speed);
    }
    public void moveLeft() {
        this.pos.setX(this.pos.getX() - speed);
    }
    public void moveRight() {
        this.pos.setX(this.pos.getX() + speed);
    }


    public void setCurrentHealth(int amount){
        currentHealth = amount;
        if(currentHealth > stat.getMaxHealth()){
            currentHealth = stat.getMaxHealth();
        }
        if(currentHealth < 0){
            currentHealth = 0;
        }
    }
    public void setCurrentMana(int amount) {
        this.currentMana = amount;
        if(this.currentMana > stat.getMaxMana()) {
            this.currentMana = stat.getMaxMana();
        }
        if(this.currentMana < 0) {
            this.currentMana = 0;
        }
    }

    public boolean isDead() {
        return currentHealth == 0;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
    public int getCurrentMana() {
        return currentMana;
    }
    public Stat getStat() {
        return stat;
    }


}
