package com.trinhdin.rpg.view;

import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class EquipmentView {

    private VBox equipmentPane = new VBox();
    private final int equipmentRowCnt = 6;
    private int index = 0;
    private int prevIndex = 0;
    public EquipmentView() {
        createEquipmentPane();
        addKeyHandler();
    }
    private void addKeyHandler(){
        equipmentPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case UP:
                        System.out.println("Up");
                        index--;
                        if(index < 0){
                            index = equipmentRowCnt - 1;
                        }
                        break;
                    case DOWN:
                        index++;
                        if(index > equipmentRowCnt - 1){
                            index = 0;
                        }
                        System.out.println("Down");
                        break;
                    case E:
                        System.out.println("Unequip " + index);
                        break;
                    case TAB:
                        Node node = equipmentPane.getParent().lookup("#inventory");
                        node.setDisable(false);
                        node.setFocusTraversable(true);
                        node.requestFocus();
                        equipmentPane.setDisable(true);
                        break;
                }
                highlightRow(prevIndex,"black");
                prevIndex = index;
                highlightRow(index,"red");
            }
        });
    }
    private HBox createEquipmentRow(String equipmentName){
        HBox equipmentRow = new HBox();
        equipmentRow.setSpacing(10);
        Label label = new Label(equipmentName);
        label.getStyleClass().add("item-label");
        Rectangle item = new Rectangle(32, 32);
        Label equipButton = new Label("Press e to unequip");
        equipmentRow.getChildren().addAll(label, item, equipButton);
        item.getStyleClass().add("item-rectangle");
        return equipmentRow;
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
            createEquipmentRow("Helmet"),
            createEquipmentRow("Armor"),
            createEquipmentRow("Weapon"),
            createEquipmentRow("Shield"),
            createEquipmentRow("Boots"),
            createEquipmentRow("Gloves")
        );
    }
    private void highlightRow(int index, String color){
        System.out.println("Highlight row " + index);
        HBox row = (HBox) equipmentPane.getChildren().get(index+1);
        row.getChildren().get(1).setStyle("-fx-stroke: " + color + "; "); ;
    }
    public VBox getEquipmentPane() {
        return equipmentPane;
    }
}
