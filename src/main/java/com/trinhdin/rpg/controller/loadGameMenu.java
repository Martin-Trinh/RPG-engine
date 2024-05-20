package com.trinhdin.rpg.controller;
import com.trinhdin.rpg.model.GameConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
public class loadGameMenu extends menuController{
    @FXML
    private Button loadGameBtn;
    @FXML
    private TextField fileNameInput;
    @FXML
    private Text errorMsg;
    public void loadGame(ActionEvent event) throws IOException {
        String fileName = fileNameInput.getText();
        try{
            GameScreen gameScreen = new GameScreen(stage);
            gameScreen.loadGameFromFile(fileName);
        }catch (IOException e){
            errorMsg.setText("Error loading game from: " + fileName);
            log.error("Error loading game: " + e.getMessage());
        }
    }

}
