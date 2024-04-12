package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.Position;

import java.util.ArrayList;

public class NPC extends Entity {
    ArrayList<String> dialogues;
    private int dialogueIndex = 0;

    public NPC(Position pos, String name, ArrayList<String> dialogues) {
        super(pos, name);
        this.dialogues = dialogues;
    }

    public String speak(){
        if(dialogueIndex < dialogues.size()){
            return dialogues.get(dialogueIndex++);
        }
        return null;
    }
}
