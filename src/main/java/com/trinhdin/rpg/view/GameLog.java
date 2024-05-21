package com.trinhdin.rpg.view;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
/**
 * GameLog class for displaying game log on screen
 */
@Slf4j
public class GameLog extends VBox {
    private final int MAX_LOG_MSG = 6;
    /**
     * Constructor for GameLog, add style to log box
     */
    public GameLog(){
        this.setSpacing(10); // Spacing between log items
        this.setStyle("-fx-border-color: brown; -fx-padding: 10px");
    }
    public void clearLog(){
        this.getChildren().clear();
    }
    /**
     * Display log message on log box
     * @param logMsg log message to display
     */
    public void displayLogMsg(String logMsg){
        if(logMsg.trim().isEmpty()){
            log.warn("No message to display");
            return;
        }
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
}
