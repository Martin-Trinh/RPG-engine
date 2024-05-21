package com.trinhdin.rpg;

import com.trinhdin.rpg.controller.GameScreen;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
public class App extends Application {
    @Override
    public void start(Stage stage){
        try{
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            String css = getClass().getResource("/fxml/mainMenu.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setTitle("RPG");
            stage.show();
            stage.setOnCloseRequest(event -> {
                event.consume();
                this.exit(stage);
            });
        }catch (Exception e){
            log.error("Error starting game: " + e.getMessage());
        }
    }
    public void exit (Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit out of this game");
        alert.setHeaderText("Do you want to exit?");
        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}



