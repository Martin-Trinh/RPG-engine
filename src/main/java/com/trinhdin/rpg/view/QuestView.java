package com.trinhdin.rpg.view;

import com.trinhdin.rpg.model.GameEntity.Character.Quest;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
     *
     * @param quests list of quests
     */
    public QuestView(ArrayList<Quest> quests) {
        this.quests = quests;
    }

    /**
     * Create quest pane to display all hero's quests
     *
     * @return quest pane
     */
    public ScrollPane createQuestPane() {
        Label heading = new Label("All quests: ");
        heading.getStyleClass().add("heading");
        VBox questPane = new VBox();
        questPane.setLayoutX(20);
        questPane.setLayoutY(20);
        questPane.setSpacing(10);
        questPane.getChildren().add(heading);
        for (Quest q : quests) {
            VBox questBox = new VBox();
            questBox.getStyleClass().add("questBox");
            questBox.getChildren().addAll(new Label("Name: " + q.getName()),
                    new Label("Description: " + q.getDescription()),
                    new Label("Monster to kill: " + q.getMonsterToKill()),
                    new Label("State: " + (q.isCompleted() ? "Completed" : "Not completed"))
            );
            questPane.getChildren().add(questBox);

        }
        ScrollPane scrollPane = new ScrollPane(questPane);
        scrollPane.setPrefSize(600, 350);
        scrollPane.setId("questPane");
        return scrollPane;
    }
}
