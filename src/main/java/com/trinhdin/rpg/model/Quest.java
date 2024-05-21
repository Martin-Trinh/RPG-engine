package com.trinhdin.rpg.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import lombok.Getter;

/**
 * Quest class to represent hero quest in game
 */
@Getter
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

    public String toString(){
        return "Name: " + name + " - Description: " + description + " - Monster to kill" + monsterToKill;
    }
    /**
     * Complete quest base on killed monster
     * @param monsterKilled
     * @return
     */
    public boolean complete(Monster monsterKilled) {
        if(monsterKilled.getName().equals(monsterToKill))
            return isCompleted = true;
        return isCompleted = false;
    }
    public boolean equals(Quest quest){
        return this.name.equals(quest.name) && this.monsterToKill.equals(quest.monsterToKill);
    }
}
