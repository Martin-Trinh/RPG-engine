package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.Quest;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Arrays;

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
    /**
     * Interact with NPC
     * @param hero the hero interact with NPC
     * @return bool true if the want to remove entity from map after interaction
     *
     */
    @Override
    public boolean interact(Hero hero){
        String dialogue = speak();
        if(dialogue != null){
            gameMsg = dialogue;
            System.out.println(dialogue);
        }else {
            if (!questGiven) {
                hero.addQuest(questForHero);
                gameMsg = "Quest given";
                System.out.println("Quest given");
                questGiven = true;
            } else {
                if (hero.isQuestCompleted(questForHero)) {
                    gameMsg = "Quest completed";
                    System.out.println("Quest completed");
                    hero.getInventory().addItem(itemForHero);
                    gameMsg += "\nItem given";
                    System.out.println("Item given");
                    return true;
                }else{
                    gameMsg = "Quest not completed";
                    System.out.println("Quest not completed");
                }
            }
        }
        return false;
    }
}
