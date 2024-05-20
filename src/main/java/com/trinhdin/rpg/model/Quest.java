package com.trinhdin.rpg.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
/**
 * Quest class to represent hero quest in game
 */
public class Quest {
    private String name;
    private String description;
    private boolean isCompleted = false;
    private String monsterToKill;

    public Quest(String name, String description, String monsterToKill) {
        this.name = name;
        this.description = description;
        this.monsterToKill = monsterToKill;
    }
    public Quest(JsonNode node){
        name = node.get("name").asText();
        description = node.get("description").asText();
        monsterToKill = node.get("monsterToKill").asText();
    }
    public String getDescription() {
        return description;
    }
    public String getName(){
        return name;
    }

    public String getMonsterToKill() {
        return monsterToKill;
    }
    public String toString(){
        return "Name: " + name + " - Description: " + description + " - Monster to kill" + monsterToKill;
    }
    public boolean getCompleted() {
        return isCompleted;
    }

    public boolean complete(Monster monsterKilled) {
        if(monsterKilled.getName().equals(monsterToKill))
            return isCompleted = true;
        return isCompleted = false;
    }
    public boolean equals(Quest quest){
        return this.name.equals(quest.name) && this.monsterToKill.equals(quest.monsterToKill);
    }
}
