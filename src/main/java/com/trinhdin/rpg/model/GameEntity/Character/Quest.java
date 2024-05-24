package com.trinhdin.rpg.model.GameEntity.Character;

import com.fasterxml.jackson.databind.JsonNode;
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

    /**
     * Constructor for Quest
     *
     * @param name          quest name
     * @param description   quest description
     * @param monsterToKill monster to kill
     */
    public Quest(String name, String description, String monsterToKill) {
        this.name = name;
        this.description = description;
        this.monsterToKill = monsterToKill;
    }

    /**
     * Create a quest from json node
     *
     * @param node json node
     */
    public Quest(JsonNode node) {
        name = node.get("name").asText();
        description = node.get("description").asText();
        monsterToKill = node.get("monsterToKill").asText();
    }

    /**
     * Override toString method
     *
     * @return string representation of quest
     */
    public String toString() {
        return "Name: " + name + " - Description: " + description + " - Monster to kill" + monsterToKill;
    }

    /**
     * Complete quest base on killed monster
     *
     * @param monsterKilled
     * @return
     */
    public boolean complete(Monster monsterKilled) {
        isCompleted = monsterKilled.getName().equals(monsterToKill);
        return isCompleted;
    }

    public boolean equals(Quest quest) {
        return this.name.equals(quest.name) && this.monsterToKill.equals(quest.monsterToKill);
    }
}
