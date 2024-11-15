package com.game.graphics;

import javax.swing.*;
import java.awt.*;

public class ImageButton extends JButton {

    private final Image backgroundImage;
    private final String buttonText;

    public ImageButton(String buttonText, Image backgroundImage) {
        this.buttonText = buttonText;
        this.backgroundImage = backgroundImage;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(Color.WHITE); // Textfarbe
        setFont(new Font("Arial", Font.BOLD, 20)); // Schriftart und -größe
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFocusPainted(false);
        setPreferredSize(new Dimension(200,100));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Keinen Standardrahmen zeichnen
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        // Text auf dem Button zeichnen
        g.setColor(getForeground());
        g.setFont(getFont());
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(buttonText)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(buttonText, x, y);
    }
}
