package com.trinhdin.rpg.view;

import com.trinhdin.rpg.controller.LogGameMsg;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import com.trinhdin.rpg.model.GameEntity.Item.Item;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class InventoryView{
    Hero hero;
    VBox inventoryPane = new VBox();
    GridPane gridPane = new GridPane();
    private final int MAX_COL = 4;
    private final int maxRow;
    private final Inventory inventory;
    private EquipmentView equipmentView;
    private GameLog gameLog;
    private SidePane sidepane;
    public InventoryView(Hero hero, GameLog gameLog, SidePane sidePane) {
        this.hero = hero;
        this.inventory = hero.getInventory();
        this.gameLog = gameLog;
        this.sidepane = sidePane;
        maxRow = (int) Math.ceil(inventory.getMaxSlot() * 1.0 / MAX_COL);
        Label text = new Label("Hero Inventory");
        text.getStyleClass().add("heading");
        inventoryPane.setPrefSize(300,250);
        inventoryPane.setId("inventory");
        inventoryPane.getChildren().addAll(text, createInventoryPane());
        addKeyHandler();
    }
    public VBox getInventoryPane() {
        return inventoryPane;
    }
    private void addKeyHandler(){
        inventoryPane.requestFocus();
        inventoryPane.setFocusTraversable(true);
        inventoryPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            int currentRow = 0;
            int currentCol = 0;
            int selectedIndex = 0;
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case RIGHT:
                        if(++currentCol >  MAX_COL - 1){
                            currentCol = 0;
                        }
                        break;
                    case LEFT:
                        if(--currentCol < 0){
                            currentCol = MAX_COL - 1;
                        }
                        break;
                    case UP:
                        if(--currentRow < 0){
                            currentRow = maxRow - 1;
                        }
                        break;
                    case DOWN:
                        if(++currentRow > maxRow - 1){
                            currentRow = 0;
                        }
                        break;
                    case E:
                        System.out.println("Use " + selectedIndex);
                        inventory.useItem(selectedIndex, hero);
                        gameLog.displayLogMsg(inventory.getGameMsg());
                        refreshInventoryPane();
                        equipmentView.refreshEquipmentPane();
                        sidepane.displayHeroStat(hero);
                        break;
                    case D:
                        inventory.removeItem(selectedIndex);
                        gameLog.displayLogMsg(inventory.getGameMsg());
                        refreshInventoryPane();
                        break;
                    case TAB:
                        Node node = inventoryPane.getParent().lookup("#equipment");
                        node.requestFocus();
                        break;
                }
                // unhighlight previous item
                highlightItem(selectedIndex,"black");
                selectedIndex = currentRow * MAX_COL + currentCol;
                // highlight current item
                highlightItem(selectedIndex,"red");
                // prevent event from bubbling up
                if(event.getCode() != KeyCode.I)
                    event.consume();
            }
        });
    }
    public void refreshInventoryPane(){
        gridPane = new GridPane();
        inventoryPane.getChildren().remove(1);
        inventoryPane.getChildren().add(createInventoryPane());
    }
    private GridPane createInventoryPane(){
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        for(int row = 0; row < maxRow; row++){
            for (int col = 0; col < MAX_COL; col++) {
                int index = row * MAX_COL + col;
                StackPane pane = new StackPane();
                pane.getStyleClass().add("item-rectangle");
                if(index < inventory.getItems().size()){
                    Item item = inventory.getItems().get(row * MAX_COL + col);
                    pane.getChildren().add(new ImageView(item.getImage()));
                }
                pane.setPrefSize(Entity.getWidth(), Entity.getHeight());
                gridPane.add(pane, col, row);
            }
        }
        highlightItem(0,"red");
        return gridPane;
    }
    private void highlightItem(int index,String color){
        gridPane.getChildren().get(index).setStyle("-fx-border-color: " + color);
    }
    public void setEquipmentView(EquipmentView equipmentView) {
        this.equipmentView = equipmentView;
    }

}
