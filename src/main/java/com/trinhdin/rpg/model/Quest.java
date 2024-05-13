package com.trinhdin.rpg.model;

import com.trinhdin.rpg.model.GameEntity.Character.Monster;

public class Quest {
    private String name;
    private String description;
    private boolean isCompleted = false;
    private Monster monsterToKill;

    public Quest(String name, String description, Monster monsterToKill) {
        this.name = name;
        this.description = description;
        this.monsterToKill = monsterToKill;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean complete(Monster monsterKilled) {
        if(monsterKilled == monsterToKill)
            return isCompleted = true;
        return isCompleted = false;
    }
    public boolean equals(Quest quest){
        return this.name.equals(quest.name) && this.monsterToKill.getName().equals(quest.monsterToKill.getName());
    }
}
