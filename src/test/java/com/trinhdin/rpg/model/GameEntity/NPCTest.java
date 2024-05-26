package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameConfig;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Character.Quest;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.GameEntity.Item.ObstacleItem;
import com.trinhdin.rpg.model.Map;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NPCTest {
    /**
     * Test giving one quest and complete one quest
     * 1. Test dialogues with NPC - printing out dialogues
     * 2. Test giving quest to hero
     * 3. Test completing quest
     * 4. Test giving item to hero
     */
    @Test
    public void testGivingOneQuestAndCompleteOneQuest(){
        NPC npc = GameConfig.getInstance().getNPCFromConfig(null, '!');
        Hero hero = new Hero(null, "hero", "hero.png", 1, new Stat(0,0,0,0,0,0,0));
        // test dialogue
        npc.interact(hero);
        assertEquals("NPC: Hello", npc.getGameMsg());
        npc.interact(hero);
        assertEquals("NPC: I have a quest for you", npc.getGameMsg());
        // test giving quest
        npc.interact(hero);
        assertEquals(1, hero.getQuests().size());
        // check if given quest is not complete
        assertFalse(npc.interact(hero));
        Monster monsterMock = mock(Monster.class);
        when(monsterMock.getName()).thenReturn("Orc");
        // complete quest
        hero.completeQuest(monsterMock);
        // test quest completion
        assertTrue(npc.interact(hero));
        verify(monsterMock, times(1)).getName();
        // test giving item
        assertEquals(npc.getItemForHero(), hero.getInventory().findItem(npc.getItemForHero()));
    }

    /**
     * Test giving multiple quests and completing the right quest
     * 1. Test dialogues with NPC - empty dialogues
     * 2. Test first quest to hero
     * 3. Add 2 more quests to hero with same objective but different name and different objective same name
     * 4. Test completing the right quest
     * 5. Test if quest 2 and 3 still exist and not completed
     */
    @Test
    public void testCompletingTheRightQuest(){
        Quest quest = new Quest("quest", "quest", "monster");
        ObstacleItem obstacleItem = new ObstacleItem(null, "item", "item", "item.png");
        NPC npc = new NPC(null, "npc", "npc.png", new ArrayList<>(), quest, obstacleItem);
        Hero hero = new Hero(null, "hero", "hero.png", 1, new Stat(0,0,0,0,0,0,0));
        // test dialogue
        for(int i = 0; i < npc.getDialogues().size(); i++){
            assertEquals(i,npc.getDialogueIndex());
            npc.interact(hero);
            assertEquals("NPC: Sentence " + (i + 1), npc.getGameMsg());
        }
        // test giving quest
        npc.interact(hero);
        // test adding quest to hero
        assertEquals(1, hero.getQuests().size());
        Monster monsterMock = mock(Monster.class);
        when(monsterMock.getName()).thenReturn("monster");
        // complete quest
        hero.completeQuest(monsterMock);
        // same objective but different name
        Quest secondQuest = new Quest("quest2", "quest", "monster");
        // same name but different objective
        Quest thirdQuest = new Quest("quest", "quest", "monster1");
        hero.addQuest(secondQuest);
        hero.addQuest(thirdQuest);
        // test quest completion
        npc.interact(hero);
        // test if quest 2 and 3 still exist
        assertTrue(hero.getQuests().contains(secondQuest));
        assertFalse(hero.isQuestCompleted(secondQuest));
        // 2 and 3 not completed
        assertFalse(hero.isQuestCompleted(thirdQuest));
        assertTrue(hero.getQuests().contains(thirdQuest));
        // test 1 quest was removed
        assertFalse(hero.getQuests().contains(npc.getQuestForHero()));
        // test giving item
        assertEquals(npc.getItemForHero(), hero.getInventory().findItem(npc.getItemForHero()));
        verify(monsterMock, times(1)).getName();
    }


}
