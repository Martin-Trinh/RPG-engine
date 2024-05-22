package com.trinhdin.rpg.model.GameEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Item.ObstacleItem;
import com.trinhdin.rpg.model.GameEntity.Character.Quest;
import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * NPC class to represent NPC entity
 */
@Slf4j
@Getter
public class NPC extends Entity implements Interactable{
    private ArrayList<String> dialogues;
    private int dialogueIndex = 0;
    private Quest questForHero;
    private ObstacleItem itemForHero;
    private boolean questGiven = false;
    public NPC(Point2D pos, String name, String fileName, ArrayList<String> dialogues, Quest questForHero, ObstacleItem itemForHero){
        super(pos, name, fileName);
        this.questForHero = questForHero;
        this.dialogues = dialogues;
        this.itemForHero = itemForHero;
    }
    public NPC(JsonNode node){
        super(node);
        dialogues = new ArrayList<>();
        for(JsonNode dialogueNode : node.get("dialogues")){
            dialogues.add(dialogueNode.asText());
        }
        questForHero = new Quest(node.get("questForHero"));
        itemForHero = new ObstacleItem(node.get("itemForHero"));
    }
    private String speak(){
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
            gameMsg = "NPC: " + dialogue;
        }else {
            if (!questGiven) {
                hero.addQuest(questForHero);
                gameMsg = "NPC: Quest given kill " + questForHero.getMonsterToKill() + " to complete quest";
                log.info(gameMsg);
                questGiven = true;
            } else {
                if (hero.isQuestCompleted(questForHero)) {
                    gameMsg = "NPC: Quest completed - " + itemForHero.getName() + " given to hero";
                    log.info(gameMsg);
                    hero.getInventory().addItem(itemForHero);
                    return true;
                }else{
                    gameMsg = "NPC: Quest not completed, kill " + questForHero.getMonsterToKill() + " to complete quest";
                    log.info(gameMsg);
                }
            }
        }
        return false;
    }
}
