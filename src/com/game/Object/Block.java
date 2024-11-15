package com.game.Object;

import com.game.Object.Util.Handler;
import com.game.Object.Util.ObjectId;
import com.game.graphics.Texture;
import com.game.superMario.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

    private final int index;
    private final BufferedImage[] sprite;
    private boolean hit;
    private Debris debris;
    private final Handler handler = new Handler();

    public Block(int x, int y, int width, int height, int index, int scale) {
        super(x, y, ObjectId.BLOCK, width, height, scale);
        this.index = index;
        Texture texture = Game.getTexture();
        sprite = texture.getTile_1();
        this.debris = null;
    }

    @Override
    public void tick() {
        if (hit && debris != null) {
            debris.tick();
        }
    }

    @Override
    public boolean shouldRemove() {
        return debris != null && debris.shouldRemove();
    }

    @Override
    public void render(Graphics g) {
        if (!hit) {
            g.drawImage(sprite[index], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
        } else {
            if (debris != null) {
                debris.draw(g);
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        if (!hit) {
            return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        }
        else {
            return new Rectangle(0,0,0,0);
        }
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

    public void hit() {
        if (index == 24) {
            hit = true;
            debris = new Debris(getX(), getY(), getWidth(), getHeight());
            handler.removeObj(this);
        }
    }

    public int getIndex() {
        return index;
    }

    public boolean isHit() {
        return hit;
    }
}
