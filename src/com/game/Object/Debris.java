package com.game.Object;

import com.game.graphics.Texture;
import com.game.superMario.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Debris {

    private final BufferedImage[] debris;
    private final float width;
    private final float height;
    private final float velX;
    private float velY;
    private final float[] x;
    private final float[] y;

    public Debris(float x, float y, float width, float height){
        this.x = new float[4];
        this.y = new float[4];
        this.x[0] = (float) (x-(0.5 * width));
        this.x[1] = (float) (x-(0.5 * width));
        this.x[2] = (float) (x+(0.5 * width));
        this.x[3] = (float) (x+(0.5 * width));

        this.y[0] = (float) (y+(0.5*height));
        this.y[1] = (float) (y-(0.5*height));
        this.y[2] = (float) (y+(0.5*height));
        this.y[3] = (float) (y-(0.5*height));

        this.width = width/2;
        this.height = height/2;
        Texture texture = Game.getTexture();
        debris = texture.getDebris_1();

        velX = 2f;
        velY = -7f;
    }

    public void applyGravity(){
        velY += 0.5f;

    }

    public void tick(){
        x[0] = -velX + x[0];
        x[1] = -velX + x[1];
        x[2] = velX + x[2];
        x[3] = velX + x[3];

        y[0] = velY + y[0];
        y[1] = (velY + y[1] - 2);
        y[2] = velY + y[2];
        y[3] =  (velY + y[3] - 2);

        applyGravity();
    }

    public boolean shouldRemove(){
        return y[1] > Game.getWindowHeight();
    }

    public void draw(Graphics g){
        for (int i = 0; i<4;i++){
            g.drawImage(debris[i],(int) x[i], (int) y[i], (int) width, (int) height, null);

        }
    }
}
