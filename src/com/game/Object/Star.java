package com.game.Object;

import com.game.Object.Util.ObjectId;
import com.game.graphics.Texture;
import com.game.superMario.Game;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Star extends GameObject{

    private final BufferedImage[] starImage;

    public Star(int x, int y, int width, int height, int scale) {
        super(x, y, ObjectId.STAR, width, height, scale);
        Texture texture = Game.getTexture();
        this.starImage = texture.getStar_1();
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(starImage[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
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
