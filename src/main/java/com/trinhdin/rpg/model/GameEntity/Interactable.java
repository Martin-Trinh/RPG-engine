package com.trinhdin.rpg.model.GameEntity;

import com.trinhdin.rpg.model.GameEntity.Character.Hero;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
/**
 * Interface represents intractable entity with F key
 */
public interface Interactable {
    /**
     * Define how to interact with hero
     * @param hero hero to interact with
     * @return true if item will be removed after interaction
     */
    boolean interact(Hero hero);
}