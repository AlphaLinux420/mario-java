package com.game.Object;

import com.game.Object.Util.ObjectId;
import com.game.graphics.Texture;
import com.game.superMario.Game;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Flag extends GameObject{

    private final BufferedImage[] flagImage;
    private final int index;

    public Flag(int x, int y, int width, int height, int index, int scale) {
        super(x, y, ObjectId.FLAG, width, height, scale);
        this.index = index;
        Texture texture = Game.getTexture();
        this.flagImage = texture.getFlag_1();
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(flagImage[index], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
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
