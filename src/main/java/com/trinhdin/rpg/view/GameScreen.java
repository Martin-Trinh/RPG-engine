package com.trinhdin.rpg.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameScreen {
    public final int GAME_SCREEN_WIDTH = 800;
    public final int GAME_SCREEN_HEIGHT = 600;
    public GameScreen(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT);
        Canvas canvas = new Canvas(GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        stage.setScene(scene);
        stage.show();

    }
}
