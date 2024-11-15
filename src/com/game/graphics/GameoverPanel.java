package com.game.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class GameoverPanel extends TitelPanel {

    private BufferedImage gameoverImg;

    public GameoverPanel() {
        try {
            URL resource = getClass().getResource("../resource/gameover.png");
            assert resource != null;
            gameoverImg = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameoverImg != null) {
            g.drawImage(gameoverImg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
