package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.controller.GameSaveLoad;
import com.trinhdin.rpg.model.GameData;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Item.Consumable;
import com.trinhdin.rpg.model.GameEntity.Item.Equipment;
import com.trinhdin.rpg.model.GameEntity.Item.EquipmentType;
import com.trinhdin.rpg.model.GameEntity.Item.ObstacleItem;
import com.trinhdin.rpg.model.Map;
import com.trinhdin.rpg.model.MoveDirection;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    /**
     * Integration test for loading new game, moving hero on map, then saving game
     * 1. Load new game
     * 2. Move hero to the right
     * 3. Check if hero position is updated correctly
     * 4. Move hero up to collide with wall
     * 5. Check if hero position is not updated
     * 6. Save game to file
     * 7. Load game from file
     * 8. Check if hero position, monsters, entities are saved and loaded correctly
     */
    @Test
    public void testLoadNewGameMoveThenSave(){
        Map map = new Map();
        map.configureHero("Hunter",0,0);
        Hero hero = map.getHero();
        try{
            map.loadMap(0, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // test hero movement
        Point2D heroPrevPosition = new Point2D(hero.getPos().getX(), hero.getPos().getY());
        // should not collide with wall
        if(!map.isCharacterCollide(MoveDirection.RIGHT)){
            // hero position should not change
            assertEquals(hero.getPos(), heroPrevPosition);
           hero.moveRight();
        }
        // hero should move right
        assertEquals(hero.getPos(), heroPrevPosition.add(hero.getSpeed(), 0));

        // should collide with wall
        while(!map.isCharacterCollide(MoveDirection.UP)){
            heroPrevPosition = new Point2D(hero.getPos().getX(), hero.getPos().getY());
            hero.moveUp();
            assertEquals( hero.getPos(), heroPrevPosition.add(0, -hero.getSpeed()));
        }
        assertTrue(map.isCharacterCollide(MoveDirection.UP));
        GameData gameData = new GameData(map);
        GameSaveLoad  gameSaveLoad = new GameSaveLoad();
        String fileName = "testSave.json";
        Map loadedMap = new Map();
        try{
            gameSaveLoad.saveGame(gameData, fileName);
            gameSaveLoad.loadGame(fileName, loadedMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // test if hero position is saved and loaded correctly
        assertEquals(map.getHero().getPos(), loadedMap.getHero().getPos());
        for(Monster m : map.getMonsters().values()){
            assertTrue(loadedMap.getMonsters().containsKey(m.getPos()));
        }
        for(Entity e : map.getEntities().values()){
            assertTrue(loadedMap.getEntities().containsKey(e.getPos()));
        }
        //delete file after test
       assertTrue(new File("src/main/resources/gameData/" + fileName).delete());
    }

    /**
     *  Integration test for saving game and loading game twice
     * 1. Load invalid map (should throw out of bound exception)
     * 2. Load valid map
     * 3. Move hero to pick up item
     * 4. use consumable item
     * 5. Move hero to pick up equipment
     * 6. Equip equipment
     * 7. Save game to file
     * 8. Load game from none existed file (should throw IOException)
     * 9. Load game again from saved file
     * 10. Check if hero health, equipment are saved and loaded correctly
     */
    @Test
    public void testSaveGameAndLoadGameTwice(){
        Map map = new Map();
        map.configureHero("Hunter",0,0);
        Hero hero = map.getHero();
        try{
            assertThrows(IndexOutOfBoundsException.class, ()->{
                map.loadMap(1, true);
            });
            map.loadMap(0, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // test hero movement
        while(!map.isCharacterCollide(MoveDirection.RIGHT)){
            hero.moveRight();
        }
        // hero should meet item
        Entity entity = map.isEntityNearby();
        assertNotNull(entity);
        assertEquals("Health Potion", entity.getName());
        // interact with item successfully
        assertTrue(((Consumable) entity).interact(hero));
        // hero should pick up item
        assertEquals(1, hero.getInventory().getItems().size());
        hero.setCurrentHealth(0);
        hero.getInventory().useItem(0, hero);
        assertEquals(50, hero.getCurrentHealth());
        // removed health potion from map
        map.getEntities().remove(entity.getPos());
        while(true){
            entity = map.isEntityNearby();
            if(entity != null)
                break;
            hero.moveDown();
        }
        assertNotNull(entity);
        assertEquals("Bow", entity.getName());
        // interact with item successfully
        assertTrue(((Equipment) entity).interact(hero));
        // hero should pick up item
        assertEquals(1, hero.getInventory().getItems().size());
        // item should be removed from map
        hero.getInventory().useItem(0, hero);
        // hero should equip item
        assertNotNull(hero.getEquipments()[0]);
        GameData gameData = new GameData(map);
        GameSaveLoad  gameSaveLoad = new GameSaveLoad();
        String fileName = "testSave.json";
        Map loadedMap = new Map();
        try{
            //save game to file
            gameSaveLoad.saveGame(gameData, fileName);
            //save game to none existed file
            assertThrows(IOException.class, () ->{
                gameSaveLoad.loadGame("noneExistedFile.json", loadedMap);
            });
            gameSaveLoad.loadGame(fileName, loadedMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // test if hero position is saved and loaded correctly
        assertEquals(50, loadedMap.getHero().getCurrentHealth());
        assertNotNull(hero.getEquipments()[0]);
        //delete file after test
        assertTrue(new File("src/main/resources/gameData/" + fileName).delete());
    }
}
