package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.view.GameScreen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class gameConfigMenu extends menuController implements Initializable {
    @FXML
    private ChoiceBox<String> heroType;
    @FXML
    private Text errorMsg;
    private String[] heroTypes = {"Mage" ,"Warrior", "Archer"};
    @FXML
    private Spinner<Integer> strengthInput;
    @FXML
    private Spinner<Integer> inteligenceInput;
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
        SpinnerValueFactory<Integer> inteligenceFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        SpinnerValueFactory<Integer> agilityFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        SpinnerValueFactory<Integer> armorFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        SpinnerValueFactory<Integer> magicArmorFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        strengthFactory.setValue(1);
        inteligenceFactory.setValue(1);
        agilityFactory.setValue(1);
        magicArmorFactory.setValue(1);
        armorFactory.setValue(1);
        strengthInput.setValueFactory(strengthFactory);
        inteligenceInput.setValueFactory(inteligenceFactory);
        agilityInput.setValueFactory(agilityFactory);
        armorInput.setValueFactory(armorFactory);
        magicArmorInput.setValueFactory(magicArmorFactory);
    }
    public void startGame(){
        if(heroType.getValue() == null){
            errorMsg.setText("Please select hero type!");
        }
        System.out.println("Strength: " + strengthInput.getValue());
        System.out.println("Inteligence: " + inteligenceInput.getValue());
        System.out.println("Agility : " + agilityInput.getValue());
        System.out.println("Armor: " + armorInput.getValue());
        System.out.println("Magic Armor: " + magicArmorInput.getValue());
        // render game screen
//        GameScreen gameScreen = new GameScreen(stage);
    }
}
