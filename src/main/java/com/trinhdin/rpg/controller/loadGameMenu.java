package com.trinhdin.rpg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class loadGameMenu extends menuController{
    @FXML
    private Button loadGameBtn;
    @FXML
    private TextField fileNameInput;
    @FXML
    private Text errorMsg;
    public void loadGame(ActionEvent event) throws IOException {
        try{
            String fileName = fileNameInput.getText();
            System.out.println(fileName);
        }catch (Exception e){
            errorMsg.setText("Invalid file path");
            System.out.println(e.getMessage());
        }
    }

}
