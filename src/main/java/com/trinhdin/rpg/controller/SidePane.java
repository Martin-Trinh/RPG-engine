package com.trinhdin.rpg.controller;

import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SidePane extends VBox {
    private VBox heroStatPane = new VBox();
    private VBox monsterStatPane = new VBox();
    private VBox abilityPane = new VBox();
    public SidePane(int width){
        this.setStyle("-fx-border-color: black");
        this.setPrefWidth(width);
        this.getChildren().addAll(heroStatPane,monsterStatPane,abilityPane);
        heroStatPane.setStyle("-fx-border-color: black; -fx-padding: 5px");
        monsterStatPane.setStyle("-fx-border-color: black; -fx-padding: 5px");
        abilityPane.setStyle("-fx-border-color: black; -fx-padding: 5px");
        heroStatPane.setPrefHeight(200);
        monsterStatPane.setPrefHeight(200);
        abilityPane.setPrefHeight(200);
    }

    public void displayHeroStat(Hero hero){
        SplitPane splitPane = new SplitPane();
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        heroStatPane.getChildren().clear();
        Label heroLabel = new Label("Hero Stat:");
        heroLabel.getStyleClass().add("stat-heading");
        vBox1.getChildren().addAll(
            new Label("Current Health: " + hero.getCurrentHealth()),
            new Label("Current Mana: " + hero.getCurrentMana()),
            new Label("Max Health: " + hero.getStat().getMaxHealth()),
            new Label("Max Mana: " + hero.getStat().getMaxMana()),
            new Label("Strength: " + hero.getStat().getStrength()),
            new Label("Intelligence: " + hero.getStat().getIntelligence()),
            new Label("Agility: " + hero.getStat().getAgility()),
            new Label("Armor: " + hero.getStat().getArmor()),
            new Label("Magic Armor: " + hero.getStat().getMagicArmor())
        );
        vBox2.getChildren().addAll(
            new Label("Level: " + hero.getLevel()),
            new Label("Exp: " + hero.getExp())
        );
        splitPane.getItems().addAll(vBox1,vBox2);
        splitPane.setDividerPositions(0.65);
        heroStatPane.getChildren().addAll(heroLabel,splitPane);
    }
    public void displayMonsterStat(Monster monster){
        SplitPane splitPane = new SplitPane();
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        monsterStatPane.getChildren().clear();
        Label monsterLabel = new Label("Hero Stat:");
        monsterLabel.getStyleClass().add("stat-heading");
        vBox1.getChildren().addAll(
                new Label("Current Health: " + monster.getCurrentHealth()),
                new Label("Current Mana: " + monster.getCurrentMana()),
                new Label("Max Health: " + monster.getStat().getMaxHealth()),
                new Label("Max Mana: " + monster.getStat().getMaxMana()),
                new Label("Strength: " + monster.getStat().getStrength()),
                new Label("Intelligence: " + monster.getStat().getIntelligence()),
                new Label("Agility: " + monster.getStat().getAgility()),
                new Label("Armor: " + monster.getStat().getArmor()),
                new Label("Magic Armor: " + monster.getStat().getMagicArmor())
        );
        vBox2.getChildren().addAll(
                new Label("Level: " + monster.getLevel()),
                new Label("Exp worth: " + monster.getExpWorth())
        );
        splitPane.getItems().addAll(vBox1,vBox2);
        splitPane.setDividerPositions(0.65);
        monsterStatPane.getChildren().addAll(monsterLabel,splitPane);
    }
    public void displayAbility(ArrayList<Ability> abilities){
        for(Ability ability: abilities){
            abilityPane.getChildren().add(new Label(ability.toString()));
        }
    }


}
