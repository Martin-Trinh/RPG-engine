package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import com.trinhdin.rpg.model.GameEntity.Item.ObstacleItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ObstacleTest {
    @Test
    public void testResolveObstacleWithNoItem(){
        ObstacleItem item = mock(ObstacleItem.class);
        when(item.getName()).thenReturn("item");
        Obstacle obstacle = new Obstacle (null, "obstacle", "obstacle.png", true, item);
        Stat stat = new Stat(10, 10, 10, 10, 0, 0, 0);
        Hero hero = new Hero(null, "hero", "hero.png", 1, stat);
        assertFalse(obstacle.interact(hero));
        verify(item, times(1)).getName();
    }
    @Test
    public void testResolveObstacleWithItem(){
        ObstacleItem resolveItem = new ObstacleItem(null, "item", "item.png", "item");
        Obstacle obstacle = new Obstacle (null, "obstacle", "obstacle.png", true, resolveItem);
        Stat stat = new Stat(10, 10, 10, 10, 0, 0, 0);
        Hero hero = new Hero(null, "hero", "hero.png", 1, stat);
        hero.getInventory().addItem(resolveItem);
        // check if obstacle is resolved
        assertTrue(obstacle.interact(hero));
        assertFalse(obstacle.isCollision());
    }
}
