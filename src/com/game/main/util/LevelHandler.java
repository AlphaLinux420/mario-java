package com.game.main.util;

import com.game.Object.*;
import com.game.Object.Util.Handler;
import com.game.graphics.BufferImageLoader;
import com.game.superMario.Game;

import java.awt.image.BufferedImage;

public class LevelHandler {

    private final BufferImageLoader loader;
    private BufferedImage levelTiles;
    private final Handler handler;
    private final Block blockWithItem = new Block(0, 0, 0, 0, 24, 3);
    private Player player;
    private final Game game;

    public LevelHandler(Handler handler, Game game) {
        this.handler = handler;
        loader = new BufferImageLoader();
        String[] levels = new String[2];
        levels[0] = "level1.png";
        levels[1] = "level2.png";
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void start() {
        String PARENT_FOLDER = "../resource/level/";
        setLevel(PARENT_FOLDER + game.getWindows().getLevelName());
        loadCharacters(PARENT_FOLDER + "mario.png");
    }

    public Player getPlayer() {
        return player;
    }

    public void setLevel(String levelTilesPath) {
        this.levelTiles = loader.loadImage(levelTilesPath);

        int width = levelTiles.getWidth();
        int height = levelTiles.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = levelTiles.getRGB(j, i);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                if (red == 255 && green == 255 && blue == 255) {
                    continue;
                }

                //RGB von Paint einem Block zuweisen und spawnen
                if (blue == 100 && green == 100 && red == 100) {
                    handler.addObj(new Block(j * 16, i * 16, 16, 16, 0, 3));
                } else if (blue == 0 && green == 0 && red == 54) {
                    handler.addObj(new Block(j * 16, i * 16, 16, 16, 2, 3));
                } else if (blue == 64 && green == 200 && red == 210) {
                    Block luckyBlock = new Block(j * 16, i * 16, 16, 16, 24, 3);
                    handler.addObj(luckyBlock);
                    if (blockWithItem.isHit()) {
                        handler.removeObj(luckyBlock);
                        handler.addObj(new Item(j * 16, i * 16, 16, 16, 3, handler));
                    }
                } else if (blue == 0 && green == 200 && red == 0) {
                    handler.addObj(new Pipe(j * 16, i * 16, 32, 16, 1, 3));
                } else if (blue == 0 && green == 240 && red == 0) {
                    handler.addObj(new Pipe(j * 16, i * 16, 32, 16, 0, 3));
                } else if (blue == 0 && green == 220 && red == 0) {
                    handler.addObj(new Pipe(j * 16, i * 16, 32, 16, 2, 3));
                }else if (blue == 0 && green == 255 && red == 255) {
                    handler.addObj(new Coin(j * 16, i * 16, 16, 16, 1, 3));
                } else if (blue == 0 && green == 64 && red == 128) {
                    handler.addObj(new Enemy(j * 16, i * 16, 17, 16, 0, 3, handler));
                } else if (blue == 50 && green == 100 && red == 100) {
                    handler.addObj(new Shell(j * 16, i * 16, 16, 16, 0, 3, handler));
                } else if (blue == 0 && green == 0 && red == 100) {
                    handler.addObj(new Block2(j * 16, i * 16, 16, 16, 0, 3));
                } else if (blue == 0 && green == 100 && red == 0) {
                    handler.addObj(new Bush(j * 16, i * 16, 32, 16, 0, 3));
                } else if (blue == 255 && green == 0 && red == 128) {
                    handler.addObj(new Star(j * 16, i * 16, 16, 16, 3));
                } else if (blue == 0 && green == 128 && red == 255) {
                    handler.addObj(new Flag(j * 16, i * 16, 16, 16, 0, 3));
                } else if (blue == 255 && green == 0 && red == 0) {
                    handler.addObj(new Flag(j * 16, i * 16, 16, 16, 2, 3));
                }
            }
        }
    }

    private void loadCharacters(String levelCharactersPath) {
        this.levelTiles = loader.loadImage(levelCharactersPath);
        processCharacters();
    }

    private void processCharacters() {
        int width = levelTiles.getWidth();
        int height = levelTiles.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = levelTiles.getRGB(j, i);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                if (red == 255 && green == 255 && blue == 255) {
                    continue;
                }
                if (red == green && red == blue && (red == 0)) {
                    player = new Player(j * 16, i * 16, 3, handler, blockWithItem, this, false);
                    handler.setPlayer(player);
                }
            }
        }
    }
}
