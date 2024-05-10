package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public interface Interactable {
    boolean interact(Hero hero);
    Rectangle getBounds();
}