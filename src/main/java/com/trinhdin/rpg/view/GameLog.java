package com.trinhdin.rpg.view;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
/**
 * GameLog class for displaying game log on screen
 */
public class GameLog extends VBox {
    private final int MAX_LOG_MSG = 5;
    public GameLog(){
        this.setSpacing(10); // Spacing between log items
        this.setStyle("-fx-border-color: brown; -fx-padding: 10px");
    }
    public void clearLog(){
        this.getChildren().clear();
    }
    public void displayLogMsg(String logMsg){
        String []output = logMsg.split("\n");
        for(String msg : output){
            addLogMsg(msg);
        }
    }
    private void addLogMsg(String logMsg){
        if(this.getChildren().size() >= MAX_LOG_MSG){
            this.getChildren().remove(0);
        }
        this.getChildren().add(new Text(logMsg));
    }
    public void displayLogMsg(ArrayList<String> logMsgs){
        for(String logMsg : logMsgs){
            displayLogMsg(logMsg);
        }
    }
}
