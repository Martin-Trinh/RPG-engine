module com.trinhdin.rpg {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.trinhdin.rpg to javafx.fxml;
    exports com.trinhdin.rpg;
}