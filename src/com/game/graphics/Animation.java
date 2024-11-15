package com.game.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {

    private final int speed;
    private final int frames;
    private int index = 0;
    private int count = 0;
    private final BufferedImage[] images;
    private BufferedImage currentImage;

    public Animation(int speed, BufferedImage... args){
        this.speed = speed;
        images = new BufferedImage[args.length];
        System.arraycopy(args, 0, images, 0, args.length);
        frames = args.length;
    }
    
    public void runAnimation(){
        index++;
        if (index > speed){
            index = 0;
            nextFrame();
        }
    }

    private void nextFrame() {
        currentImage = images[count];
        count++;

        if (count >= frames){
            count = 0;
        }
    }

    public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY){
        g.drawImage(currentImage, x,y,scaleX,scaleY,null);
    }
}