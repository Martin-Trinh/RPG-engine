package com.trinhdin.rpg.view;

import com.trinhdin.rpg.model.Quest;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
/**
 * QuestView class to display all quests from hero
 */
public class QuestView {
    private ArrayList<Quest> quests;
    /**
     * Constructor for QuestView
     * Assign list of quests to quests
     * @param quests list of quests
     */
    public QuestView(ArrayList<Quest> quests) {
        this.quests = quests;
    }
    /**
     * Create quest pane to display all hero's quests
     * @return quest pane
     */
    public VBox createQuestPane(){
        Label heading = new Label("All quests: ");
        heading.getStyleClass().add("heading");
        VBox questPane = new VBox();
        questPane.setLayoutX(20);
        questPane.setLayoutY(20);
        questPane.setSpacing(10);
        questPane.setId("questPane");
        questPane.getChildren().add(heading);
        HBox columDesc = new HBox();
        columDesc.getStyleClass().add("questBox");
        columDesc.getStyleClass().add("stat-heading");
        columDesc.setSpacing(30);
        columDesc.getChildren().addAll(new Label("Name"), new Label("Description"), new Label("Monster to kill"));
        questPane.getChildren().add(columDesc);
        for(Quest q: quests){
            HBox questBox = new HBox();
            questBox.setSpacing(20);
            questBox.getStyleClass().add("questBox");
            questBox.getChildren().addAll(new Label(q.getName()),
                                        new Label(q.getDescription()),
                                        new Label(q.getMonsterToKill()));
            questPane.getChildren().add(questBox);
        }
        return questPane;
    }
}
