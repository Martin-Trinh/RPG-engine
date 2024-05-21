module com.trinhdin.rpg {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;
    requires static lombok;


    exports com.trinhdin.rpg;
    exports com.trinhdin.rpg.controller;
    exports com.trinhdin.rpg.view;
    opens com.trinhdin.rpg to javafx.fxml;
    opens com.trinhdin.rpg.controller to javafx.fxml;
    opens com.trinhdin.rpg.view to javafx.fxml;
    opens com.trinhdin.rpg.model to com.fasterxml.jackson.databind;
    opens com.trinhdin.rpg.model.GameEntity to com.fasterxml.jackson.databind;
    opens com.trinhdin.rpg.model.GameEntity.Character to com.fasterxml.jackson.databind;
    opens com.trinhdin.rpg.model.GameEntity.Ability to com.fasterxml.jackson.databind;
    opens com.trinhdin.rpg.model.GameEntity.Item to com.fasterxml.jackson.databind;
}