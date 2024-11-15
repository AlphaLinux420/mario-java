package com.game.superMario;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import com.game.Object.Util.Handler;
import com.game.Object.Util.KeyInput;
import com.game.graphics.Camera;
import com.game.graphics.Texture;
import com.game.graphics.Windows;
import com.game.main.util.LevelHandler;

public class Game extends Canvas implements Runnable {
    // GAME CONSTANTS
    private static final int MILIS_PER_SEC = 1000;
    private static final int NANOS_PER_SEC = 1000000000;
    private static final double NUM_TICKS = 60.0;
    private static final int WINDOW_WIDTH = 1920;
    private static final int WINDOW_HEIGHT = 1080;
    private static final int SCREEN_WIDTH = WINDOW_WIDTH - 67;
    private static final int SCREEN_OFFSET = 16 * 3;
    // GAME VARIABLES
    private boolean running;
    private BufferedImage skyImage;
    private BufferedImage blueImage;
    // GAME COMPONENTS
    private Thread thread;
    public Handler handler;
    private static Texture texture;
    private LevelHandler levelHandler;
    private Camera camera;
    private final Windows windows;

    public Game(Windows win) {
        windows = win;
    }

    public Windows getWindows() {
        return windows;
    }

    public void initialize() {
        texture = new Texture();
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        levelHandler = new LevelHandler(handler, this);
        levelHandler.start();

        try {
            skyImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/sky.png")));
            blueImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/Blue.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera = new Camera(0, SCREEN_OFFSET);
        start();
    }

    public LevelHandler getLevelHandler() {
        return levelHandler;
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double ns = NANOS_PER_SEC / NUM_TICKS;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                delta--;
            }
            render();

            if (System.currentTimeMillis() - timer > MILIS_PER_SEC) {
                timer += MILIS_PER_SEC;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
        camera.tick(handler.getPlayer());
    }

    private void render() {
        BufferStrategy buf = this.getBufferStrategy();
        if (buf == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = buf.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        g.drawImage(skyImage, 0, 0, 1920, 1080, this);
        g.drawImage(blueImage, 0, 500, 1920, 200, this);
        g2d.translate(camera.getX(), camera.getY());
        handler.render(g);
        g2d.translate(-camera.getX(), -camera.getY());

        g.dispose();
        buf.show();
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static Texture getTexture() {
        return texture;
    }

    public static void setTexture(Texture texture) {
        Game.texture = texture;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }
}