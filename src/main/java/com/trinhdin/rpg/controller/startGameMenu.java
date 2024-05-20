package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.GameConfig;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
public class startGameMenu extends menuController implements Initializable {
    @FXML
    private ChoiceBox<String> heroType;
    @FXML
    private Label maxHealth;
    @FXML
    private Label maxMana;
    @FXML
    private Label strength;
    @FXML
    private Label intelligence;
    @FXML
    private Label agility;
    @FXML
    private Label armor;
    @FXML
    private Label magicArmor;
    @FXML
    private Label errorMsg;
    private HashMap<String, Stat> heroes = GameConfig.getInstance().getHeroes();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        heroType.setValue("Please select a hero type");
        // select hero type
        heroType.getItems().addAll(heroes.keySet());
        heroType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showStats();
            }
        });

    }
    private void showStats(){
        Stat stat = heroes.get(heroType.getValue());
        maxHealth.setText("Max Health: " + stat.getMaxHealth());;
        maxMana.setText("Max Mana: " + stat.getMaxMana());
        strength.setText("Strength: " + stat.getStrength());
        intelligence.setText("Intelligence: " + stat.getIntelligence());
        agility.setText("Agility: " + stat.getAgility());
        armor.setText("Armor: " + stat.getArmor());
        magicArmor.setText("Magic Armor: " + stat.getMagicArmor());
    }
    public void startGame(ActionEvent event){
        if(heroType.getValue().equals( "Select a hero type")){
            errorMsg.setText("Please select a hero type");
            return;
        }
        // render game screen
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GameScreen gameScreen = new GameScreen(stage);
        gameScreen.loadNewGame(heroType.getValue());

    }
}
