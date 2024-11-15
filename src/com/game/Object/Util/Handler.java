package com.game.Object.Util;

import com.game.Object.GameObject;
import com.game.Object.Item;
import com.game.Object.Player;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Handler {
    private final List<GameObject> gameObjects;
    private final List<GameObject> objectsToRemove;
    private Player player;
    private final List<String> levelNames;
    private int currentLevelIndex;

    public Handler() {
        gameObjects = new LinkedList<>();
        objectsToRemove = new LinkedList<>();
        levelNames = new LinkedList<>();
        currentLevelIndex = 0; // Starte mit dem ersten Level
    }

    public void tick() {
        for (GameObject tempObject : new LinkedList<>(gameObjects)) {
            tempObject.tick();
            if (tempObject.shouldRemove()) {
                objectsToRemove.add(tempObject);
            }
            if (tempObject instanceof Item) {
                tempObject.tick();
            }
        }
        gameObjects.removeAll(objectsToRemove);
        objectsToRemove.clear();
    }

    public void render(Graphics g) {
        for (GameObject obj : gameObjects) {
            obj.render(g);
        }
    }

    public void addObj(GameObject obj) {
        gameObjects.add(obj);
    }

    public void removeObj(GameObject obj) {
        gameObjects.remove(obj);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setPlayer(Player player) {
        if (this.player != null) {
            return;
        }
        addObj(player);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void loadLevel(int levelIndex) {
        if (levelIndex < 0 || levelIndex >= levelNames.size()) {
            return;
        }
        gameObjects.clear();
        objectsToRemove.clear();
        currentLevelIndex = levelIndex;
    }

    public int getLevelCount() {
        return levelNames.size();
    }

}