package com.trinhdin.rpg.controller;
import com.trinhdin.rpg.model.GameConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    public void loadGame(ActionEvent event) {
        String fileName = fileNameInput.getText();
        try{
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            GameScreen gameScreen = new GameScreen(stage);
            scene = gameScreen.loadGameFromFile(fileName + ".json");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            errorMsg.setText("Error loading game from: " + fileName);
            log.error(e.getMessage());
        }
    }

}
