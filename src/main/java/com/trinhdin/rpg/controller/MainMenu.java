package com.trinhdin.rpg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {
    private Parent root;
    private Stage stage;
    public void switchToGameScene(ActionEvent event) throws IOException{
        this.root = FXMLLoader.load(getClass().getResource("hello-vew.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
