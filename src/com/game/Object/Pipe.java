package com.game.Object;

import com.game.Object.Util.ObjectId;
import com.game.graphics.Texture;
import com.game.superMario.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pipe extends GameObject {

    private final int index;
    private final BufferedImage[] sprite;


    public Pipe(int x, int y, int width, int height, int index, int scale) {
        super(x, y, ObjectId.PIPE, width, height, scale);
        this.index = index;
        Texture texture = Game.getTexture();
        sprite = texture.getPipe_1();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite[index], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
        showBoundsPipe(g);
    }

    public void showBoundsPipe(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.RED);
        g2d.draw(getBoundsTop());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsRight());
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getX() + getWidth() / 2 - getWidth() / 4 ),
                (int) (getY() + getHeight() / 2),
                32,
                16);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) (getX() + getWidth() / 2 - getWidth() / 4) -25,
                (int) getY(),
                100,
                1);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int) getX(),
                (int) getY(),
                5,
                (int) getHeight() );
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) getX(),
                (int) (getY()),
                5,
                (int) (getHeight()));
    }
}
