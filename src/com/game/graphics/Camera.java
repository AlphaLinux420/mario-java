package com.game.graphics;

import com.game.Object.GameObject;
import com.game.superMario.Game;

public class Camera {
    private int x;
    private int y;

    public Camera(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject player){
        x = (int) (-player.getX() + Game.getScreenWidth()/2);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
