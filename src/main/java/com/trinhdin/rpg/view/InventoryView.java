package com.trinhdin.rpg.view;

import com.trinhdin.rpg.model.GameEntity.Item.Inventory;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class InventoryView {
   boolean isInventoryOpen = false;
    VBox inventoryPane = new VBox();
    GridPane gridPane = new GridPane();
    private int currentRow = 0;
    private int currentCol = 0;
    private final int maxCol = 4;
    private final int maxSize = 16;
    private int selectedIndex = 0;
    private int maxRow = (int) Math.ceil(maxSize * 1.0 / maxCol);
    private Inventory inventory;
    public InventoryView(Inventory inventory) {
        Label text = new Label("Hero Inventory");
        text.getStyleClass().add("heading");
        this.inventory = inventory;
        inventoryPane.setPrefSize(300,250);
        inventoryPane.setId("inventory");

        inventoryPane.setStyle("-fx-border-color: black");
        inventoryPane.getChildren().addAll(text, createInventoryPane());
        addKeyHandler();
    }
    public VBox getInventoryPane() {
        return inventoryPane;
    }
    private void addKeyHandler(){
        inventoryPane.setFocusTraversable(true);
        inventoryPane.requestFocus();
        inventoryPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case RIGHT:
                        currentCol++;
                        if(currentCol >  maxCol - 1){
                            currentCol = 0;
                        }
                        break;
                    case LEFT:
                        currentCol--;
                        if(currentCol < 0){
                            currentCol = maxCol - 1;
                        }
                        break;
                    case UP:
                        currentRow--;
                        if(currentRow < 0){
                            currentRow = maxRow - 1;
                        }
                        break;
                    case DOWN:
                        currentRow++;
                        if(currentRow > maxRow - 1){
                            currentRow = 0;
                        }
                        break;
                    case E:
                        System.out.println("Use item " + selectedIndex);
                        break;
                    case D:
                        System.out.println("Drop item " + selectedIndex);
                        break;
                    case TAB:
                        Node node = inventoryPane.getParent().lookup("#equipment");
                        node.setDisable(false);
                        node.setFocusTraversable(true);
                        node.requestFocus();
                        inventoryPane.setDisable(true);
                        break;
                }
                System.out.println("Row: " + currentRow + " Col: " + currentCol);
                highlightItem(selectedIndex,"black");
                selectedIndex = currentRow * maxCol + currentCol;
                highlightItem(selectedIndex,"red");
            }
        });
    }
    private Rectangle createItemRectangle(){
        Rectangle item = new Rectangle(32, 32);
        item.getStyleClass().add("item-rectangle");
        return item;
    }
    private GridPane createInventoryPane(){
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        for(int row = 0; row < maxRow; row++){
            for (int col = 0; col < maxCol; col++) {
                Rectangle item = createItemRectangle();
                gridPane.add(item, col, row);
            }
        }
        highlightItem(0,"red");
        return gridPane;
    }
    private void highlightItem(int index,String color){
        gridPane.getChildren().get(index).setStyle("-fx-stroke: " + color);
    }
}
