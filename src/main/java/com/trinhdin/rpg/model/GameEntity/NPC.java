package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.Position;
import com.trinhdin.rpg.model.Quest;

import java.util.ArrayList;

public class NPC extends Entity {
    ArrayList<String> dialogues;
    private int dialogueIndex = 0;
    Quest questForHero;
    Item itemForHero;
    public NPC(Position pos, String name, ArrayList<String> dialogues, Quest questForHero, Item itemForHero){
        super(pos, name);
        this.questForHero = questForHero;
        this.dialogues = dialogues;
        this.itemForHero = itemForHero;
    }

    public String speak(){
        if(dialogueIndex < dialogues.size()){
            return dialogues.get(dialogueIndex++);
        }
        return null;
    }
    public Quest getQuestForHero(){
        Quest tmp  = questForHero;
        // delete the quest after giving it to the hero
        questForHero = null;
        return tmp;
    }
    public Item getItemForHero(){
        Item tmp = itemForHero;
        // delete the item after giving it to the hero
        itemForHero = null;
        return tmp;
    }
}
