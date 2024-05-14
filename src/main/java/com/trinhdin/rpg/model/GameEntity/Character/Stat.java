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

    public void add(Stat stat) {
        maxHealth += stat.maxHealth;
        maxMana += stat.maxMana;
        strength += stat.strength;
        intelligence += stat.intelligence;
        agility += stat.agility;
        armor += stat.armor;
        magicArmor += stat.magicArmor;
    }
    public void subtract(Stat stat) {
        if(stat.maxHealth >= 0) {
            maxHealth -= stat.maxHealth;
        }
        if(stat.maxMana >= 0) {
            maxMana -= stat.maxMana;
        }
        strength -= stat.strength;
        intelligence -= stat.intelligence;
        agility -= stat.agility;
        armor -= stat.armor;
        magicArmor -= stat.magicArmor;
        if(strength < 0)
            strength = 0;
        if(intelligence < 0)
            intelligence = 0;
        if(agility < 0)
            agility = 0;
        if(armor < 0)
            armor = 0;
        if(magicArmor < 0)
            magicArmor = 0;
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
