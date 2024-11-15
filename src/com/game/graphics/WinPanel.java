package com.game.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class WinPanel extends GameoverPanel {
    private BufferedImage winImg;

    public WinPanel() {
        try {
            URL resource = getClass().getResource("../resource/introscreen.png");
            assert resource != null;
            winImg = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (winImg != null) {
            g.drawImage(winImg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
