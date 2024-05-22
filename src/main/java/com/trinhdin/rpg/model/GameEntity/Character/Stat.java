package com.trinhdin.rpg.model.GameEntity.Character;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Stat class to represent stat of character
 */
public class Stat {
    private int maxHealth;
    private int maxMana;
    private int strength;
    private int intelligence;
    private int agility;
    private int armor;
    private int magicArmor;

    @JsonCreator
    public Stat(@JsonProperty("maxHealth") int maxHealth,
                @JsonProperty("maxMana") int maxMana,
                @JsonProperty("strength") int strength,
                @JsonProperty("intelligence") int intelligence,
                @JsonProperty("agility") int agility,
                @JsonProperty("armor") int armor,
                @JsonProperty("magicArmor") int magicArmor) {
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.strength = strength;
        this.intelligence = intelligence;
        this.agility = agility;
        this.armor = armor;
        this.magicArmor = magicArmor;
    }

    public Stat add(Stat stat) {
        maxHealth += stat.maxHealth;
        maxMana += stat.maxMana;
        strength += stat.strength;
        intelligence += stat.intelligence;
        agility += stat.agility;
        armor += stat.armor;
        magicArmor += stat.magicArmor;
        negativeStatCheck();
        return this;
    }
    private void negativeStatCheck(){
        if(maxHealth < 0)
            maxHealth = 0;
        if(maxMana < 0)
            maxMana = 0;
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
    public Stat subtract(Stat stat) {
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
        negativeStatCheck();
        return this;
    }
    public Stat multiply(int num) {
        maxHealth *= num;
        maxMana *= num;
        strength *= num;
        intelligence *= num;
        agility *= num;
        armor *= num;
        magicArmor *= num;
        return this;
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
    public boolean equals(Stat other) {
        return maxHealth == other.maxHealth &&
                maxMana == other.maxMana &&
                strength == other.strength &&
                intelligence == other.intelligence &&
                agility == other.agility &&
                armor == other.armor &&
                magicArmor == other.magicArmor;
    }
}
