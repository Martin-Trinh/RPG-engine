package com.trinhdin.rpg.model.GameEntity.Item;

import com.trinhdin.rpg.model.GameEntity.Item.Equipment;
import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InventoryTest {
    @Test
    public void addItemToFullInventory() {
         Inventory inventory = new Inventory();
         // add full inventory
        for(int i = 0; i < inventory.getMaxSlot(); i++){
            Equipment itemMock = mock(Equipment.class);
            // check if item is added
            assertTrue(inventory.addItem(itemMock));
        }
        Equipment itemMock = mock(Equipment.class);
        // add item to full inventory
         assertFalse(inventory.addItem(itemMock));
    }
    @Test
    public void removeItemFromInventory() {
        Inventory inventory = new Inventory();
        Equipment itemMock = mock(Equipment.class);
        when(itemMock.getName()).thenReturn("item");
        // add item to inventory
        inventory.addItem(itemMock);
        // remove item from inventory
        assertTrue(inventory.removeItem(0));
        // remove item from empty inventory
        assertFalse(inventory.removeItem(0));
        inventory.addItem(itemMock);
        // remove item from inventory using item name
        assertTrue(inventory.removeItem(itemMock));
    }

}
