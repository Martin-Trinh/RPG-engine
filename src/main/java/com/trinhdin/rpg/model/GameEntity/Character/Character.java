package com.trinhdin.rpg.model.GameEntity.Character;

import com.trinhdin.rpg.model.GameEntity.Ability.AttackAbility;
import com.trinhdin.rpg.model.GameEntity.Ability.AttackType;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.MoveDirection;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Character extends Entity {
    protected double speed;
    protected MoveDirection direction;
    protected Stat stat;
    protected int currentHealth;
    protected int currentMana;
    static protected int boundOffset = 8;


   static public int getOffset() {
        return boundOffset;
    }

    public Character(Point2D pos, String name, String fileName, double speed, Stat stat) {
        super(pos, name, fileName);
        this.speed = speed;
        this.stat = stat;
        this.currentHealth = stat.getMaxHealth();
        this.currentMana = stat.getMaxMana();
    }
    public double getSpeed() {
        return speed;
    }
    public MoveDirection getDirection() {
        return direction;
    }
    public void moveUp() {
        direction = MoveDirection.UP;
        pos = pos.add(0, -speed);
    }
    public void moveDown() {
        direction = MoveDirection.DOWN;
        pos = pos.add(0, speed);
    }
    public void moveLeft() {
        direction = MoveDirection.LEFT;
        pos = pos.add(-speed, 0);
    }
    public void moveRight() {
        direction = MoveDirection.RIGHT;
        pos = pos.add(speed, 0);
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
        return currentHealth <= 0;
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

    public int[] calculateNewPosition4Corner(Point2D pos){
        int [] corner = new int[4];
        corner[0] = (int) (pos.getX() + boundOffset) /WIDTH;
        corner[1] = (int) (pos.getY() + boundOffset) / HEIGHT;
        corner[2] = (int) (pos.getX() + Entity.getWidth() - boundOffset) / WIDTH;
        corner[3] = (int) (pos.getY() + Entity.getHeight() - boundOffset) / HEIGHT;
        return corner;
    }
    public Rectangle getOffsetBounds(Point2D pos){
       return new Rectangle (pos.getX() + boundOffset, pos.getY() + boundOffset, WIDTH - 2*boundOffset, HEIGHT - 2*boundOffset);
    }

}
