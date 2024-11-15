package com.game.Object;

import com.game.Object.Util.ObjectId;
import com.game.graphics.Texture;
import com.game.superMario.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Coin extends GameObject{

    private final int index;
    private final BufferedImage[] sprite;
    private boolean coinhit = false;

    public Coin(int x, int y, int width, int height, int index, int scale) {
        super(x, y, ObjectId.COIN, width, height, scale);
        this.index = index;
        Texture texture = Game.getTexture();
        sprite = texture.getItem_1();
    }
    @Override
    public void tick() {

    }

    @Override
    public boolean shouldRemove() {
        return coinhit;
    }

    @Override
    public void render(Graphics g) {
            g.drawImage(sprite[index], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
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
