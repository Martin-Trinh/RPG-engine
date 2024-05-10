package com.trinhdin.rpg.controller;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameLog extends VBox {
    private ArrayList<Text> logItems = new ArrayList<>();
    private int maxLogItems = 5;
    public GameLog(){
        this.setSpacing(10); // Spacing between log items
        this.setStyle("-fx-border-color: brown; -fx-padding: 10px");
    }
    public void addLogItem(String logItem){
        if(logItems.size() >= maxLogItems){
            logItems.remove(0);
        }
        Text text = new Text(logItem);
        logItems.add(text);
    }
    public void clearLog(){
        this.getChildren().clear();
    }
    public void displayLog(){
        for(Text logItem : logItems){
            this.getChildren().add(logItem);
        }
    }
}
