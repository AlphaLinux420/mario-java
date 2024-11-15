package com.game.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BufferImageLoader {
    private BufferedImage image;

    public BufferedImage loadImage(String path){
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
