package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.Quest;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class NPC extends Entity {
    private ArrayList<String> dialogues;
    private int dialogueIndex = 0;
    private Quest questForHero;
    private Item itemForHero;
    public NPC(Point2D pos, String name, String fileName, ArrayList<String> dialogues, Quest questForHero, Item itemForHero){
        super(pos, name, fileName);
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
        if(dialogueIndex < dialogues.size()){
            return null;
        }
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
