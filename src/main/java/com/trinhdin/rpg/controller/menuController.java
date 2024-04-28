package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class menuController {
    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    private void switchToScene(ActionEvent event, String path) throws  IOException{
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + path));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
         String css = App.class.getResource("/fxml/mainMenu.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
    public void ExitAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit out of this game");
        alert.setHeaderText("Do you want to exit?");
        if(alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void loadGameMenu(ActionEvent event) throws IOException{
        switchToScene(event, "loadGame.fxml");
    }

    public void newGameAction(ActionEvent event) throws IOException {
        switchToScene(event, "HeroConfig.fxml");

    }
    public void returnToMenu(ActionEvent event) throws IOException {
        switchToScene(event, "MainMenu.fxml");
    }


}
