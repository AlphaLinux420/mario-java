package com.game.Object;

import com.game.Object.Util.ObjectId;
import com.game.graphics.Texture;
import com.game.superMario.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Coin extends GameObject {

    private final int index;
    private final BufferedImage[] sprite;
    private boolean coinhit = false;
    private int animationFrame = 0;
    private int animationSpeed = 8; // Adjust the speed of the animation
    private int animationCounter = 0;

    public Coin(int x, int y, int width, int height, int index, int scale) {
        super(x, y, ObjectId.COIN, width, height, scale);
        this.index = index;
        Texture texture = Game.getTexture();
        sprite = new BufferedImage[]{texture.getItem_1()[1], texture.getItem_1()[2], texture.getItem_1()[3]};
    }

    @Override
    public void tick() {
        if (!coinhit) {
            // Update the animation frame
            animationCounter++;
            if (animationCounter >= animationSpeed) {
                animationFrame = (animationFrame + 1) % sprite.length;
                animationCounter = 0;
            }
        }
    }

    @Override
    public boolean shouldRemove() {
        return coinhit;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite[animationFrame], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
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