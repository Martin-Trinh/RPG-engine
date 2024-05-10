package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.Quest;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class NPC extends Entity implements Interactable{
    private ArrayList<String> dialogues;
    private int dialogueIndex = 0;
    private Quest questForHero;
    private Item itemForHero;
    private boolean questGiven = false;
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

    @Override
    public boolean interact(Hero hero){
        String dialogue = speak();
        if(dialogue != null){
            System.out.println(dialogue);
        }else {
            if (!questGiven) {
                System.out.println("Quest given");
                hero.addQuest(questForHero);
                questGiven = true;
            } else {
                if (hero.isQuestCompleted(questForHero)) {
                    System.out.println("Quest completed");
                    System.out.println("Item given");
                    hero.getInventory().addItem(itemForHero);
                    return true;
                }else{
                    System.out.println("Quest not completed");
                }
            }
        }
        return false;
    }
}
