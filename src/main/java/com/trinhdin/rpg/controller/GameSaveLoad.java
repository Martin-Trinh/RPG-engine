package com.trinhdin.rpg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GameSaveLoad {
    ObjectMapper objectMapper = new ObjectMapper();
    public void saveGame(Object object, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveGame(List<Object> object, String fileName) {
        File file = new File(fileName);
        try {
            for(Object o : object) {
                objectMapper.writeValue(file, o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadGame() {
    }
}
