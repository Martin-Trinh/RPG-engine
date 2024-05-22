package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
/**
 * Interface represents intractable entity with F key
 */
public interface Interactable {
    boolean interact(Hero hero);
}