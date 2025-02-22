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
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Controller for menu handle switching between scenes
 */
@Slf4j
public class menuController {
    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    /**
     * Switch to scene using fxml file
     *
     * @param event action event
     * @param path  fxml file path
     */
    private void switchToScene(ActionEvent event, String path) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + path));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            String css = App.class.getResource("/fxml/mainMenu.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch (NullPointerException e) {
            log.error("Error switching scene from file: " + path + " " + e.getMessage());
        } catch (IOException e) {
            log.error("Error loading scene from file: " + path + " " + e.getMessage());
        }
    }

    /**
     * Exit the game action
     *
     * @param event action event
     */
    public void ExitAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit out of this game");
        alert.setHeaderText("Do you want to exit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Load game menu button handling
     *
     * @param event action event
     */
    public void loadGameMenu(ActionEvent event) {
        switchToScene(event, "loadGame.fxml");
    }

    /**
     * New game menu button handling
     *
     * @param event action event
     */
    public void newGameAction(ActionEvent event) {
        switchToScene(event, "HeroConfig.fxml");
    }

    /**
     * Return to main menu button handling
     *
     * @param event action event
     */
    public void returnToMenu(ActionEvent event) {
        switchToScene(event, "MainMenu.fxml");
    }
}
