package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Character.Quest;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.GameEntity.Item.ObstacleItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NPCTest {
    NPC npc;
    @BeforeEach
    public void setUp(){
        Quest quest = new Quest("quest", "quest", "monster");
        ObstacleItem obstacleItem = new ObstacleItem(null, "item", "item", "item.png");
        ArrayList<String> dialogues = new ArrayList<>();
        dialogues.add("Sentence 1");
        dialogues.add("Sentence 2");
        dialogues.add("Sentence 3");
        npc = new NPC(null, "npc", "npc.png", dialogues, quest, obstacleItem);
    }
    @Test
    public void testInteraction(){
        Hero hero = new Hero(null, "hero", "hero.png", 1, new Stat(0,0,0,0,0,0,0));
        // test dialogue
        for(int i = 0; i < npc.getDialogues().size(); i++){
            assertEquals(i,npc.getDialogueIndex());
            npc.interact(hero);
            assertEquals("Sentence " + (i + 1), npc.getGameMsg());
        }
        // test giving quest
        npc.interact(hero);
        // test adding quest to hero
        assertEquals(1, hero.getQuests().size());
        Monster monsterMock = mock(Monster.class);
        when(monsterMock.getName()).thenReturn("monster");
        hero.completeQuest(monsterMock);
        // test quest completion
        npc.interact(hero);
        // test giving item
        assertEquals(npc.getItemForHero(), hero.getInventory().findItem(npc.getItemForHero()));
    }
}
