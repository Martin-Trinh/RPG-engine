package com.trinhdin.rpg.model;

public class Quest {
    private String name;
    private String description;
    private boolean isCompleted = false;

    public Quest(String name, String description) {
        this.name = name;
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
        return this.name.equals(quest.name);
    }
}
