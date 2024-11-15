package com.game.Object;

import com.game.Object.Util.Handler;
import com.game.Object.Util.ObjectId;
import com.game.graphics.Texture;
import com.game.superMario.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item extends GameObject {

    private final BufferedImage[] itemImage;
    private boolean movingRight;
    private final Handler handler;

    public Item(int x, int y, int width, int height, int scale, Handler handler) {
        super(x, y, ObjectId.ITEM, width, height, scale);
        Texture texture = Game.getTexture();
        this.itemImage = texture.getItem_1();
        this.handler = handler;
        movingRight = true;
    }

    private void applyGravity1() {
        float gravity = 0.5f;
        float terminalVelocity = 10f;
        float newVelocityY = getVelocityY() + gravity;

        // Begrenze die y-Geschwindigkeit auf die maximale Terminalgeschwindigkeit
        if (newVelocityY > terminalVelocity) {
            newVelocityY = terminalVelocity;
        }
        setVelocityY(newVelocityY);
    }

    @Override
    public void tick() {
        float velocityX = 1.5f;
        applyGravity1();

        float nextX;
        float nextY = getY() + getVelocityY();

        // Wenn das Item nach rechts läuft
        if (movingRight) {
            // Berechne die nächste Position des Items
            nextX = getX() + velocityX;
            // Wenn die nächste Position innerhalb der Spielfläche ist und keine Kollision vorliegt
            if (nextX >= 0 && !collision(nextX, getY())) {
                // Setze die neue Position des Items
                setX(nextX);
            } else {
                // Ändere die Richtung des Items, wenn es die Spielfläche verlässt oder eine Kollision vorliegt
                movingRight = false;
            }
        } else { // Wenn das Item nach links läuft
            nextX = getX() - velocityX;
            if (nextX >= 0 && !collision(nextX, getY())) {
                // Setze die neue Position des Items
                setX(nextX);
            } else {
                movingRight = true;
            }
        }

        if (!collision(getX(), nextY)) {
            setY(nextY);
        }
    }

    private boolean collision(float x, float y) {
        for (GameObject obj : handler.getGameObjects()) {
            if (obj.getId() == ObjectId.BLOCK || obj.getId() == ObjectId.PIPE || obj.getId() == ObjectId.BLOCK_2) {
                if (obj.getBounds().intersects(new Rectangle((int) x, (int) y, (int) getWidth(), (int) getHeight()))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(itemImage[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    @Override
    public Rectangle getBoundsTop() {
        return null;
    }

    @Override
    public Rectangle getBoundsLeft() {
        return null;
    }

    @Override
    public Rectangle getBoundsRight() {
        return null;
    }
}
