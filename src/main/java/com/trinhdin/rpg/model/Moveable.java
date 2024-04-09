package com.trinhdin.rpg.model;

public class Moveable extends Entity{
    protected int speed;
    protected MoveDirection direction;

    public Moveable(Position pos, int width, int height, String name, int speed) {
        super(pos, width, height, name);
        this.speed = speed;
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

}
