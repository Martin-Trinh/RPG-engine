package com.trinhdin.rpg.model.GameEntity.Character;

public class Stat {
    private int maxHealth;
    private int maxMana;
    private int strength;
    private int intelligence;
    private int agility;
    private int armor;
    private int magicArmor;

    public Stat(int maxHealth, int maxMana, int strength, int intelligence, int agility, int armor, int magicArmor) {
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.strength = strength;
        this.intelligence = intelligence;
        this.agility = agility;
        this.armor = armor;
        this.magicArmor = magicArmor;
    }

    public void modifyStat(Stat stat) {
        if(stat.maxHealth >= 0) {
            this.maxHealth += stat.maxHealth;
        }
        if(stat.maxMana >= 0) {
            this.maxMana += stat.maxMana;
        }
        this.strength += stat.strength;
        this.intelligence += stat.intelligence;
        this.agility += stat.agility;
        this.armor += stat.armor;
        this.magicArmor += stat.magicArmor;
        if(this.strength < 0)
            this.strength = 0;
        if(this.intelligence < 0)
            this.intelligence = 0;
        if(this.agility < 0)
            this.agility = 0;
        if(this.armor < 0)
            this.armor = 0;
        if(this.magicArmor < 0)
            this.magicArmor = 0;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getAgility() {
        return agility;
    }

    public int getArmor() {
        return armor;
    }
    public int getMagicArmor() {
        return magicArmor;
    }
}
