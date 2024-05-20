package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.GameConfig;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.util.ResourceBundle;


public class startGameMenu extends menuController implements Initializable {
    @FXML
    private ChoiceBox<String> heroType;
    @FXML
    private Text errorMsg;
    private String[] heroTypes = {"Mage" ,"Warrior", "Archer"};
    @FXML
    private Spinner<Integer> strengthInput;
    @FXML
    private Spinner<Integer> intelligenceInput;
    @FXML
    private Spinner<Integer> agilityInput;
    @FXML
    private Spinner<Integer> armorInput;
    @FXML
    private Spinner<Integer> magicArmorInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // select hero type
        heroType.getItems().addAll(heroTypes);
        heroType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(heroType.getValue());
            }
        });
        // select stats for hero
        SpinnerValueFactory<Integer> strengthFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        SpinnerValueFactory<Integer> intelligenceFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        SpinnerValueFactory<Integer> agilityFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        SpinnerValueFactory<Integer> armorFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        SpinnerValueFactory<Integer> magicArmorFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        strengthFactory.setValue(1);
        intelligenceFactory.setValue(1);
        agilityFactory.setValue(1);
        magicArmorFactory.setValue(1);
        armorFactory.setValue(1);
        strengthInput.setValueFactory(strengthFactory);
        intelligenceInput.setValueFactory(intelligenceFactory);
        agilityInput.setValueFactory(agilityFactory);
        armorInput.setValueFactory(armorFactory);
        magicArmorInput.setValueFactory(magicArmorFactory);
    }
    public void startGame(ActionEvent event){
        if(heroType.getValue() == null){
            errorMsg.setText("Please select hero type!");
        }
        System.out.println("Strength: " + strengthInput.getValue());
        System.out.println("Intelligence: " + intelligenceInput.getValue());
        System.out.println("Agility : " + agilityInput.getValue());
        System.out.println("Armor: " + armorInput.getValue());
        System.out.println("Magic Armor: " + magicArmorInput.getValue());

        // render game screen
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new GameScreen(stage);




    }
}
