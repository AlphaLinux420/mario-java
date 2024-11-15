package com.game.superMario;

import com.game.graphics.Windows;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().startGame();
        });
    }

    private void startGame() {

        // Create the Windows object and pass the desired window size and title
        Windows windows = new Windows(Game.getWindowWidth(), Game.getWindowHeight(), "Super Mario Bros");
    }
}