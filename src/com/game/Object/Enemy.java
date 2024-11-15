package com.game.Object;

import com.game.Object.Util.Handler;
import com.game.Object.Util.ObjectId;
import com.game.graphics.Animation;
import com.game.graphics.Texture;
import com.game.superMario.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends GameObject {

    private final BufferedImage[] sprite;
    private final int index;
    private final Handler handler;
    private boolean movingRight;

    public Enemy(float x, float y, int width, int height,int index, int scale, Handler handler) {
        super(x, y, ObjectId.ENEMY, width, height, scale);
        Texture texture = Game.getTexture();
        sprite = texture.getGoomba_1();
        this.index = index;
        this.handler = handler;
        movingRight = true;
    }

    private void applyGravity1() {
        float gravity = 0.5f;
        float terminalVelocity = 10f;
        float newVelocityY = getVelocityY() + gravity;

        if (newVelocityY > terminalVelocity) {
            newVelocityY = terminalVelocity;
        }
        setVelocityY(newVelocityY);
    }

    @Override
    public void tick() {
        float velocityX = 3f;
        applyGravity1();

        float nextX;
        float nextY = getY() + getVelocityY();

        // Wenn das Item nach rechts läuft
        if (movingRight) {
            nextX = getX() + velocityX;
            if (nextX >= 0 && !collision(nextX, getY())) {
                setX(nextX);
            } else {
                movingRight = false;
            }
        } else { // Wenn das Item nach links läuft
            nextX = getX() - velocityX;
            if (nextX >= 0 && !collision(nextX, getY())) {
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
        g.drawImage(sprite[index],(int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getX() + getWidth() / 2 - getWidth() / 4),
                (int) (getY() + getHeight() / 2),
                (int) getWidth() / 2,
                (int) getHeight() / 2);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle((int) (getX() + getWidth() / 2 - getWidth() / 4),
                (int) getY(),
                (int) getWidth() / 2,
                (int) getHeight() / 2);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int) (getX() + getWidth() - 5),
                (int) getY() + 5,
                5,
                (int) getHeight() - 10);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int) getX(),
                (int) (getY() + 5),
                5,
                (int) (getHeight() - 10));
    }
}
