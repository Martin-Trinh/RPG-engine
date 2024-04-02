package com.trinhdin.rpg;

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

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/MainMenu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            String css = getClass().getResource("fxml/mainMenu.css").toExternalForm();
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if(keyEvent.getCode() == KeyCode.UP){

                    }else if(keyEvent.getCode() == KeyCode.DOWN){

                    }
                }
            });
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setTitle("RPG");
            stage.show();

            stage.setOnCloseRequest(event -> {
                event.consume();
                this.exit(stage);
            });

        }catch (Exception e){
            e.printStackTrace();
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



