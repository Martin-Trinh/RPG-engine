package com.trinhdin.rpg.view;

import com.trinhdin.rpg.controller.LogGameMsg;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Entity;
import com.trinhdin.rpg.model.GameEntity.Item.Equipment;
import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

/**
 * EquipmentView class to display hero equipment and handle key event
 */
public class EquipmentView {
    private final Hero hero;
    @Getter
    private final VBox equipmentPane = new VBox();
    private final int equipmentRowCnt = 6;
    private int index = 0;
    private int prevIndex = 0;
    @Setter
    private InventoryView inventoryView;
    private GameLog gameLog;
    private SidePane sidePane;
    public EquipmentView(Hero hero, GameLog gameLog, SidePane sidePane){
        this.hero = hero;
        this.gameLog = gameLog;
        this.sidePane = sidePane;
        createEquipmentPane();
        addKeyHandler();
    }

    /**
     * Add key handler for equipment pane
     */
    private void addKeyHandler(){
        equipmentPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case UP:
                        if(--index < 0){
                            index = equipmentRowCnt - 1;
                        }
                        break;
                    case DOWN:
                        if(++index > equipmentRowCnt - 1){
                            index = 0;
                        }
                        break;
                    case E:
                        // unequip equipment
                        if(hero.unequip(index)){
                            gameLog.displayLogMsg(hero.getGameMsg());
                            refreshEquipmentPane();
                            inventoryView.refreshInventoryPane();
                            sidePane.displayHeroStat(hero);
                        }
                        break;
                    case TAB:
                        // switching focus to inventory pane
                        Node node = equipmentPane.getParent().lookup("#inventory");
                        node.requestFocus();
                        break;
                }
                // Unhighlight previous row
                highlightRow(prevIndex,"black");
                prevIndex = index;
                // Highlight current row
                highlightRow(index,"red");
                // Prevent event from bubbling up
                if(event.getCode() != KeyCode.I) {
                    // Prevent event from bubbling up to main event handler
                    event.consume();
                }
            }
        });
    }
    private HBox createEquipmentRow(String equipmentName, Equipment equipment){
        HBox equipmentRow = new HBox();
        equipmentRow.setSpacing(10);
        Label label = new Label(equipmentName);
        label.getStyleClass().add("item-label");
        StackPane itemPane = new StackPane();
        itemPane.getStyleClass().add("item-rectangle");
        itemPane.setPrefSize(Entity.getWidth(), Entity.getHeight());
        if(equipment != null){
            ImageView itemView = new ImageView(equipment.getImage());
            itemPane.getChildren().add(itemView);
        }
        Label equipButton = new Label("Press e to unequip");
        equipmentRow.getChildren().addAll(label, itemPane, equipButton);
        return equipmentRow;
    }
    public void refreshEquipmentPane(){
        equipmentPane.getChildren().clear();
        createEquipmentPane();
    }
    private void createEquipmentPane(){
        Label text = new Label("Hero equipment");
        text.getStyleClass().add("heading");
        equipmentPane.setSpacing(10);
        equipmentPane.setId("equipment");
        equipmentPane.setPrefSize(300, 250);
        equipmentPane.setLayoutX(300);
        equipmentPane.getChildren().addAll(
                text,
            createEquipmentRow("Weapon" , hero.getEquipments()[0]),
            createEquipmentRow("Helmet", hero.getEquipments()[1]),
            createEquipmentRow("Shield", hero.getEquipments()[2]),
            createEquipmentRow("Boots", hero.getEquipments()[3]),
            createEquipmentRow("Armor", hero.getEquipments()[4]),
            createEquipmentRow("Accessory", hero.getEquipments()[5])
        );
    }
    /**
     * Highlight row at index
     * @param index index of row to highlight
     * @param color color to highlight
     */
    private void highlightRow(int index, String color){
        System.out.println("Highlight row " + index);
        HBox row = (HBox) equipmentPane.getChildren().get(index+1);
        row.getChildren().get(1).setStyle("-fx-border-color: " + color); ;
    }
}
