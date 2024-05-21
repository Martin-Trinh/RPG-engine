package com.trinhdin.rpg.view;

import com.trinhdin.rpg.model.GameEntity.Ability.Ability;
import com.trinhdin.rpg.model.GameEntity.Character.Character;
import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import com.trinhdin.rpg.model.GameEntity.Character.Monster;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
/**
 * SidePane class to display hero and monster stat and abilities
 */
public class SidePane extends VBox {
    private final VBox heroStatPane = new VBox();
    private final VBox monsterStatPane = new VBox();
    private final VBox abilityPane = new VBox();
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
    private VBox createStatPane(Character character){
        VBox vBox = new VBox();
        vBox.getChildren().addAll(
        new Label("Current Health: " + character.getCurrentHealth()),
                new Label("Current Mana: " + character.getCurrentMana()),
                new Label("Max Health: " + character.getStat().getMaxHealth()),
                new Label("Max Mana: " + character.getStat().getMaxMana()),
                new Label("Strength: " + character.getStat().getStrength()),
                new Label("Intelligence: " + character.getStat().getIntelligence()),
                new Label("Agility: " + character.getStat().getAgility()),
                new Label("Armor: " + character.getStat().getArmor()),
                new Label("Magic Armor: " + character.getStat().getMagicArmor())
        );
        return vBox;
    }
    public void displayHeroStat(Hero hero){
        SplitPane splitPane = new SplitPane();
        VBox vBox2 = new VBox();
        heroStatPane.getChildren().clear();
        Label heroLabel = new Label(hero.getName() + " stat: ");
        heroLabel.getStyleClass().add("stat-heading");
        VBox vBox1 = createStatPane(hero);
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
        VBox vBox2 = new VBox();
        monsterStatPane.getChildren().clear();
        Label monsterLabel = new Label(monster.getName() + " stat: ");
        monsterLabel.getStyleClass().add("stat-heading");
        VBox vBox1 = createStatPane(monster);
        vBox2.getChildren().addAll(
                new Label("Level: " + monster.getLevel()),
                new Label("ExpWorth: " + monster.getExpWorth())
        );
        splitPane.getItems().addAll(vBox1,vBox2);
        splitPane.setDividerPositions(0.65);
        monsterStatPane.getChildren().addAll(monsterLabel,splitPane);
    }
    public void displayAbility(ArrayList<Ability> abilities){
        Label heading = new Label("Abilities: ");
        heading.getStyleClass().add("stat-heading");
        abilityPane.getChildren().add(heading);
        for(Ability ability: abilities){
            String [] output = ability.toString().split("\n");
            for(String s : output){
                abilityPane.getChildren().add(new Label(s));
            }
        }
    }


}
