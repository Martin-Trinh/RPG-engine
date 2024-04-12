package com.trinhdin.rpg.model;

public class Quest {
    private int questId;
    private String description;
    private boolean isCompleted = false;

    public Quest(int questId, String description) {
        this.questId = questId;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void complete() {
        isCompleted = true;
    }
    public boolean equals(Quest quest){
        return this.questId == quest.questId;
    }
}
