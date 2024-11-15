package com.game.Object.Util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


public class KeyInput extends KeyAdapter {
    private final boolean[] keyDown = new boolean[5];
    private final Handler handler;
    private int currentLevelIndex = 0; // Starte mit dem ersten Level

    public KeyInput(Handler handler){
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A){
            handler.getPlayer().setVelocityX(-8);
            keyDown[1] = true;
            currentLevelIndex--;
        }

        if (key == KeyEvent.VK_SPACE && (!handler.getPlayer().isHasJumped())){
                handler.getPlayer().jump();
                keyDown[0] = true;
        }

        if (key == KeyEvent.VK_D){
            handler.getPlayer().setVelocityX(8);
            keyDown[2] = true;
            currentLevelIndex++;
        }

        if (key == KeyEvent.VK_S) {
            handler.getPlayer().setPlayerSneaking();
            keyDown[4] = true;
        }

        if (key == KeyEvent.VK_LEFT){
            currentLevelIndex = Math.max(0, currentLevelIndex - 1); // Wechsle zum vorherigen Level
        }

        if (key == KeyEvent.VK_RIGHT){
            currentLevelIndex = Math.min(handler.getLevelCount() - 1, currentLevelIndex + 1); // Wechsle zum nächsten Level
        }

        if (key == KeyEvent.VK_ENTER){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.loadLevel(currentLevelIndex); // Lade das ausgewählte Level
                }
            }, 10000);
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE){
            keyDown[0] = false;
        }
        if (key == KeyEvent.VK_A){
            keyDown[1] = false;
        }
        if (key == KeyEvent.VK_D){
            keyDown[2] = false;
        }
        if (key == KeyEvent.VK_S) {
            keyDown[4] = false;
            handler.getPlayer().resetPlayerSize();
        }
        if (!keyDown[1] && !keyDown[2]){
            handler.getPlayer().setVelocityX(0);
        }
    }

}
