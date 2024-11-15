package com.game.Object;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import com.game.Object.Util.Handler;
import com.game.Object.Util.ObjectId;
import com.game.graphics.Animation;
import com.game.graphics.Texture;
import com.game.main.util.LevelHandler;
import com.game.superMario.Game;

public class Player extends GameObject {

    private boolean isSneaking = false;
    private static final float WIDTH = 16;
    private static final float HEIGHT = 16;
    private final Handler handler;
    private final Texture texture;
    private final BufferedImage[] spriteL;
    private final BufferedImage[] spriteS;
    private final BufferedImage[] spriteSneak;
    private final Animation playerWalkL;
    private final Animation playerWalkS;
    private BufferedImage[] currSprite;
    private Animation currAnimation;
    private boolean forward = false;
    private boolean hasJumped = false;
    private int health = 200;
    private final Block block;
    private int coinCount = 0;
    private Clip coinSound;
    private Clip jumpSound;
    private Clip pwSound;
    private Clip enemySound;
    private Clip blockSound;
    private boolean playCoinSound = false;
    private boolean playJumpSound = false;
    private boolean playPwSound = false;
    private boolean enmySound = false;
    private boolean playBlockSound;
    private final LevelHandler levelHandler;
    private BufferedImage healthImage;
    private BufferedImage[] coinImage;
    public boolean gameWon = false;
    public boolean respawn = false;

    public void loadAudioData() {
        try {
            // Lade die Sounddatei für den Sprung mit dem ClassLoader
            InputStream audioInputStream = getClass().getResourceAsStream("../resource/soundeffect.wav");
            assert audioInputStream != null;
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioInputStream);
            InputStream audioInputStream1 = getClass().getResourceAsStream("../resource/jumpeffect.wav");
            assert audioInputStream1 != null;
            AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(audioInputStream1);
            coinSound = AudioSystem.getClip();
            coinSound.open(audioStream);
            jumpSound = AudioSystem.getClip();
            jumpSound.open(audioStream1);
            InputStream audioInputStream2 = getClass().getResourceAsStream("../resource/powerup.wav");
            assert audioInputStream2 != null;
            AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(audioInputStream2);
            pwSound = AudioSystem.getClip();
            pwSound.open(audioStream2);
            InputStream audioInputStream3 = getClass().getResourceAsStream("../resource/goomba.wav");
            assert audioInputStream3 != null;
            AudioInputStream audioStream3 = AudioSystem.getAudioInputStream(audioInputStream3);
            enemySound = AudioSystem.getClip();
            enemySound.open(audioStream3);
            InputStream audioInputStream4 = getClass().getResourceAsStream("../resource/block.wav");
            assert audioInputStream4 != null;
            AudioInputStream audioStream4 = AudioSystem.getAudioInputStream(audioInputStream4);
            blockSound = AudioSystem.getClip();
            blockSound.open(audioStream4);
            FloatControl gainControl =
                    (FloatControl) jumpSound.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.

            if (playCoinSound) {
                playCoinSound();
                playCoinSound = false;
            }
            if (playJumpSound) {
                playJumpSound();
                playJumpSound = false;
            }
            if (playPwSound) {
                playPowerSound();
                playPwSound = false;
            }
            if (enmySound) {
                playEnemySound();
                enmySound = false;
            }
            if (playBlockSound) {
                playBlockSound();
                playBlockSound = false;
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playCoinSound() {
        coinSound.setFramePosition(0);
        coinSound.start();
    }

    public void playEnemySound() {
        enemySound.setFramePosition(0);
        enemySound.start();
    }

    public void playJumpSound() {
        jumpSound.setFramePosition(0);
        jumpSound.start();
    }

    public void playPowerSound() {
        pwSound.setFramePosition(0);
        pwSound.start();
    }

    public void playBlockSound() {
        blockSound.setFramePosition(0);
        blockSound.start();
    }

    public Player(float x, float y, int scale, Handler handler, Block block, LevelHandler levelHandler,
            boolean isSneaking) {
        super(x, y, ObjectId.PLAYER, WIDTH, HEIGHT, scale);
        this.handler = handler;
        texture = Game.getTexture();
        this.levelHandler = levelHandler;

        spriteL = texture.getMario_l();
        spriteS = texture.getMario_s();
        spriteSneak = texture.getMario_sneak();

        playerWalkL = new Animation(5, spriteL[1], spriteL[2], spriteL[3]);
        playerWalkS = new Animation(5, spriteS[1], spriteS[2], spriteS[3]);

        this.isSneaking = isSneaking;

        currSprite = spriteS;
        currAnimation = playerWalkS;
        this.block = block;
    }

    @Override
    public void tick() {
        collision();
        if (getVelocityY() > 25) {
            setVelocityY(getVelocityY() - 10f);
        }
        setX(getVelocityX() + getX());
        setY(getVelocityY() + getY());
        applyGravity();
        if (getY() > 1200) {
            setX(60);
            setY(block.getY());
            health--;
        }

        if (health <= 0) {
            System.out.println(health);
            levelHandler.getGame().getWindows().showGameOverPanel();
            respawn = true;
        }

        collision();
        updateCoinCount(coinCount);
        currAnimation.runAnimation();
    }

    public void resetPlayerSize() {
        isSneaking = false;
        currSprite = spriteS;
        currAnimation = playerWalkS;
    }

    public void setPlayerSneaking() {
        isSneaking = true;
        currSprite = spriteSneak;
    }

    private void collectItem() {
        currSprite = spriteL;
        currAnimation = playerWalkL;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                resetPlayerSize();
            }
        }, 10000);
    }

    private void collectCoin(Coin coin) {
        handler.removeObj(coin);
        coinCount++;
    }

    public void jump() {
        if (currSprite != spriteSneak) {
            handler.getPlayer().setVelocityY(-15);
            handler.getPlayer().setHasJumped(true);
            playJumpSound = true;
            loadAudioData();
        }
    }

    @Override
    public void render(Graphics g) {
        var time = new Date();
        try {
            healthImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/heart.png")));
            coinImage = texture.getItem_1();
        } catch (Exception e) {
            e.printStackTrace();
        }

        g.setColor(Color.BLACK);
        Font newFont = new Font("Arial", Font.BOLD, 40);
        g.setFont(newFont);
        g.drawString("" + getHealth(), (int) getX() + 620, 40);
        g.drawImage(healthImage, (int) getX() + 565, 5, 40, 40, null, null);
        g.drawImage(coinImage[1], (int) getX() + 700, -1, 50, 50, null, null);
        g.drawString(coinCount + "", (int) getX() + 750, 40);
        g.drawString((time.getTime()/ 1000) + "", (int) getX() + 750, 80);

        // Draw the player's character
        if (hasJumped) {
            if (forward) {
                g.drawImage(currSprite[5], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
            } else {
                g.drawImage(currSprite[5], (int) (getX() + getWidth()), (int) getY(), (int) -getWidth(),
                        (int) getHeight(), null);
            }
        } else if (getVelocityX() > 0) {
            currAnimation.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
            forward = true;
        } else if (getVelocityX() < 0) {
            currAnimation.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), (int) -getWidth(),
                    (int) getHeight());
            forward = false;
        } else {
            g.drawImage(currSprite[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
        }
    }

    public void updateCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    private void collision() {
        for (int i = 0; i < handler.getGameObjects().size(); i++) {
            GameObject temp = handler.getGameObjects().get(i);

            //Enemy Collision
            if (temp.getId() == ObjectId.ENEMY) {
                if (getBounds().intersects(temp.getBounds())) {
                    enemyHit((Enemy) temp);
                    coinCount = coinCount + 5;
                    enmySound = true;
                    loadAudioData();
                } else if (getBoundsRight().intersects(temp.getBounds())) {
                    setX(temp.getX() - getWidth());
                    health = health - 1;
                } else if (getBoundsLeft().intersects(temp.getBounds())) {
                    setX(temp.getX() + getWidth());
                    health = health - 1;
                }
            }
            //Coin Collision
            else if (temp.getId() == ObjectId.COIN) {
                if (getBounds().intersects(temp.getBounds())) {
                    collectCoin((Coin) temp);
                    playCoinSound = true;
                    loadAudioData();
                }
            }

            //Shell Collision
            else if (temp.getId() == ObjectId.SHELL) {
                if (getBounds().intersects(temp.getBounds())) {
                    shellHit((Shell) temp);
                    coinCount = coinCount + 5;
                } else if (getBoundsRight().intersects(temp.getBounds())) {
                    setX(temp.getX() - getWidth());
                    health = health - 1;
                } else if (getBoundsLeft().intersects(temp.getBounds())) {
                    setX(temp.getX() + getWidth());
                    health = health - 1;
                }
            }
            //Item Collision
            else if (temp.getId() == ObjectId.ITEM) {
                if (getBounds().intersects(temp.getBounds())) {
                    handler.removeObj(temp);
                    System.out.println("Hit Item");
                    collectItem();
                    coinCount = coinCount + 10;
                    playPwSound = true;
                    loadAudioData();
                }
            }

            //Star Collision
            else if (temp.getId() == ObjectId.STAR) {
                if (getBounds().intersects(temp.getBounds())) {
                    handler.removeObj(temp);
                    coinCount = coinCount + 10;
                }
            }

            //Flag Collision
            else if (temp.getId() == ObjectId.FLAG) {
                if (getBoundsTop().intersects(temp.getBounds())) {
                    setY(temp.getY() + temp.getHeight());
                    setVelocityY(0);
                    coinCount = coinCount + 100;
                    levelHandler.getGame().getWindows().showWinPanel(coinCount);
                    gameWon = true;
                } else if (getBounds().intersects(temp.getBounds())) {
                    setY(temp.getY() - getHeight());
                    setVelocityY(0);
                    coinCount = coinCount + 100;
                    levelHandler.getGame().getWindows().showWinPanel(coinCount);
                    gameWon = true;
                }
                if (getBoundsTop().intersects(temp.getBounds())) {
                    setY(temp.getY() + temp.getHeight());
                    setVelocityY(0);
                    coinCount = coinCount + 100;
                    levelHandler.getGame().getWindows().showWinPanel(coinCount);
                    gameWon = true;
                }
                if (getBoundsRight().intersects(temp.getBounds())) {
                    setX(temp.getX() - getWidth());
                    coinCount = coinCount + 100;
                    levelHandler.getGame().getWindows().showWinPanel(coinCount);
                    gameWon = true;
                }
                if (getBoundsLeft().intersects(temp.getBounds())) {
                    setX(temp.getX() + getWidth());
                    coinCount = coinCount + 100;
                    levelHandler.getGame().getWindows().showWinPanel(coinCount);
                    gameWon = true;
                }
            }

            // Pipe Collision
            if (temp.getId() == ObjectId.PIPE) {
                if (isSneaking) {
                    if (getBoundsBottom().intersects(temp.getBoundsTop()) && ((Pipe) temp).getIndex() == 0) {
                        setY(temp.getY() + handler.getGameObjects()
                                .stream()
                                .filter(gameObject -> gameObject.getX() == temp.getX()
                                        && gameObject.getId() == ObjectId.PIPE && temp != gameObject)
                                .reduce(0.0f, (sub, gameobject) -> sub + gameobject.getHeight(), Float::sum)
                                + getHeight());
                    }
                }  else if (getBoundsBottom().intersects(temp.getBoundsTop()) && ((Pipe) temp).getIndex() == 0
                        && !isSneaking) {
                    setY(temp.getY() - temp.getHeight());
                    setVelocityY(0f);
                    hasJumped = false;
                }
                if (getBoundsTop().intersects(temp.getBounds()) && (((Pipe) temp).getIndex() == 2)) {
                    setY(temp.getY() - handler.getGameObjects()
                            .stream()
                            .filter(gameObject -> gameObject.getX() == temp.getX()
                                    && gameObject.getId() == ObjectId.PIPE && temp != gameObject)
                            .reduce(0.0f, (sub, gameobject) -> sub + gameobject.getHeight(), Float::sum)
                            - 2 * getHeight());
                }
                if (getBoundsLeft().intersects(temp.getBoundsRight())) {
                    setX(temp.getX() + getWidth());
                }
                if (getBoundsRight().intersects(temp.getBounds())) {
                    setX(temp.getX() - getWidth());
                }
            }

            //Block Collision
            else if (temp.getId() == ObjectId.BLOCK && (temp.getHeight() < 220)) {
                if (getBoundsTop().intersects(temp.getBounds())) {
                    setY(temp.getY() + temp.getHeight());
                    setVelocityY(0);
                    if (((Block) temp).getIndex() == 24) {
                        playBlockSound = true;
                        loadAudioData();
                        ((Block) temp).hit();
                        Item item = new Item((int) (getX() + 120), (int) (getY() + 96), 48, 48, 1,
                                handler);
                        handler.addObj(item);
                        System.out.println("Hit");
                    }
                } else if (getBounds().intersects(temp.getBounds())) {
                    setY(temp.getY() - getHeight());
                    setVelocityY(0);
                    hasJumped = false;
                }
                intersectsBoundsTop(temp);
            }

            //Block2 Collision
            else if (temp.getId() == ObjectId.BLOCK_2 && (temp.getHeight() < 210)) {
                if (getBoundsTop().intersects(temp.getBounds())) {
                    setY(temp.getY() + temp.getHeight());
                    setVelocityY(0);
                    if (Objects.equals(temp.getId().toString(), "24")) {
                        ((Block) temp).hit();
                        Item item = new Item((int) (getX() + 120), (int) (getY() + 100), 48, 48, 1,
                                handler);
                        handler.addObj(item);
                        System.out.println("Hit");
                    }
                } else if (getBounds().intersects(temp.getBounds())) {
                    setY(temp.getY() - getHeight());
                    setVelocityY(0);
                    hasJumped = false;
                }
                intersectsBoundsTop(temp);

            }
        }
    }

    private void intersectsBoundsTop(GameObject temp) {
        if (getBoundsTop().intersects(temp.getBounds())) {
            setY(temp.getY() + temp.getHeight());
            setVelocityY(0);
        }
        if (getBoundsRight().intersects(temp.getBounds())) {
            setX(temp.getX() - getWidth());
        }
        if (getBoundsLeft().intersects(temp.getBounds())) {
            setX(temp.getX() + getWidth());
        }
    }

    private void enemyHit(Enemy enemy) {
        handler.removeObj(enemy);
    }

    private void shellHit(Shell shell) {
        handler.removeObj(shell);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getX() + getWidth() / 2 - getWidth() / 4),
                (int) (getY() + getHeight() / 2),
                (int) getWidth() / 2,
                (int) getHeight() / 2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) (getX() + getWidth() / 2 - getWidth() / 4),
                (int) getY(),
                (int) getWidth() / 2,
                (int) getHeight() / 2);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle((int) (getX() + getWidth() / 2 - getWidth() / 4),
                (int) (getY() + getHeight() / 2),
                (int) getWidth() / 2,
                (int) getHeight() / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) (getX() + getWidth() - 5),
                (int) getY() + 5,
                5,
                (int) getHeight() - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) getX(),
                (int) (getY() + 5),
                5,
                (int) (getHeight() - 10));
    }

    public boolean isHasJumped() {
        return hasJumped;
    }

    public void setHasJumped(boolean hasJumped) {
        this.hasJumped = hasJumped;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}