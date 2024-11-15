package com.game.graphics;

import java.awt.image.BufferedImage;

public class Texture {

    private final int MARIO_L_COUNT = 21;
    private final int MARIO_S_COUNT = 14;
    private final int MARIO_SNEAK_COUNT = 14;
    private final int TILE_1_COUNT = 28;
    private final int TILE_2_COUNT = 33;
    private BufferedImage player_sheet,enemy_sheet,npm_sheet,block_sheet,tile_sheet, game_over_sheet, intro_sheet, flag_sheet;
    public BufferedImage[] intro_1,flag_1,star_1,mario_l, mario_s, tile_1, tile_2, tile_3, tile_4, pipe_1, debris_1, item_1, item_2, goomba_1,shell_1,block_1, bush_1, mario_sneak;

    public Texture(){
        mario_l = new BufferedImage[MARIO_L_COUNT];
        mario_sneak = new BufferedImage[MARIO_SNEAK_COUNT];
        mario_s = new BufferedImage[MARIO_S_COUNT];
        tile_1 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];
        tile_2 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];
        tile_3 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];
        tile_4 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];
        pipe_1 = new BufferedImage[4];
        debris_1 = new BufferedImage[4];
        item_1 = new BufferedImage[4];
        item_2 = new BufferedImage[0];
        goomba_1 = new BufferedImage[3];
        shell_1 = new BufferedImage[25];
        block_1 = new BufferedImage[1];
        bush_1 = new BufferedImage[1];
        star_1 = new BufferedImage[1];
        flag_1 = new BufferedImage[3];
        intro_1 = new BufferedImage[1];

        BufferImageLoader loader = new BufferImageLoader();

        try {
            String PARENT_FOLDER = "../resource";
            player_sheet = loader.loadImage(PARENT_FOLDER + "/mario.png");
            enemy_sheet = loader.loadImage(PARENT_FOLDER + "/enemy.png");
            block_sheet = loader.loadImage(PARENT_FOLDER + "/blocks.png");
            tile_sheet = loader.loadImage(PARENT_FOLDER + "/tiles.png");
            game_over_sheet = loader.loadImage(PARENT_FOLDER + "/gameover.png");
            npm_sheet = loader.loadImage(PARENT_FOLDER + "/Items_Objects.png");
            intro_sheet = loader.loadImage(PARENT_FOLDER + "/intro.png");
            flag_sheet = loader.loadImage(PARENT_FOLDER + "/flag.png");

        }catch (Exception e){
            e.printStackTrace();
        }
        getPlayerTexture();
        getSneakTexture();
        getTileTexture();
        getPipeTexture();
        getDebrisTextures();
        getItemTextures();
        getEnemy1Texture();
        getShellTexture();
        getBlock_1Texture();
        getBush_1Texture();
        getStarTexture();
        getFlagTexture();
    }

    private void getFlagTexture(){
        int x_off = 0;
        int y_off = 0;
        int width = 16;
        int height = 16;

        flag_1[0] = flag_sheet.getSubimage(x_off, y_off + 38 * height, width,height);
        flag_1[2] = flag_sheet.getSubimage(x_off, y_off + 37 * height, width,height);
    }

    public BufferedImage[] getFlag_1(){
        return flag_1;
    }

    private void getSneakTexture() {
        int x_off = 182;
        int y_off = 1;
        int width = 16;
        int height = 32;

        y_off += height + 1;
        height = 16;

        for (int i = 0; i < MARIO_SNEAK_COUNT; i++) {
            mario_sneak[i] = player_sheet.getSubimage(x_off + i*(width+1), y_off, width, height);
        }
    }

    private void getPlayerTexture() {
        int x_off = 80;
        int y_off = 1;
        int width = 16;
        int height = 32;

        for (int i = 0; i < MARIO_L_COUNT; i++){
            mario_l[i] = player_sheet.getSubimage(x_off + i*(width+1), y_off, width, height);
        }

        y_off += height + 1;
        height = 16;

        for (int i = 0; i < MARIO_S_COUNT; i++){
            mario_s[i] = player_sheet.getSubimage(x_off + i*(width+1), y_off,width, height);
        }
    }

    private void getBlock_1Texture(){
        int x_off = 0;
        int y_off = 0;
        int width = 16;
        int height = 16;

        block_1[0] = tile_sheet.getSubimage(x_off,y_off +  height, width, height);
    }

    public BufferedImage[] getBlock_1(){
        return block_1;
    }

    private void getTileTexture() {
        int x_off = 0;
        int y_off = 0;
        int width = 16;
        int height = 16;

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < TILE_1_COUNT; i++) {
                if (j == 0) {
                    tile_1[i] = tile_sheet.getSubimage(x_off + i * width, 0, width, height);
                } else if (j == 1) {
                    tile_2[i] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                } else if (j == 2) {
                    tile_3[i] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                } else if (j == 3) {
                    tile_4[i] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                }
            }
            y_off += height; // Hier die Anpassung des y-Offsets

            for (int i = 0; i < TILE_2_COUNT; i++) {
                if (j == 0) {
                    tile_1[i + TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i * width, y_off, width, height);
                } else if (j == 1) {
                    tile_2[i + TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                } else if (j == 2) {
                    tile_3[i + TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                } else {
                    tile_4[i + TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                }
            }
        }
    }

    private void getPipeTexture() {
        int x_off = 0;
        int y_off = 16*8;
        int width = 32;
        int height = 16;

        pipe_1[0] = tile_sheet.getSubimage(x_off,y_off,width,height);
        pipe_1[1] = tile_sheet.getSubimage(x_off,y_off + height,width,height);
        pipe_1[2] = tile_sheet.getSubimage(x_off,y_off + height,width,height);
        pipe_1[3] = tile_sheet.getSubimage(x_off + width,y_off + height,width,height);
    }

    private void getBush_1Texture(){
        int x_off = 0;
        int y_off = 16*8;
        int width = 16;
        int height = 16;

        bush_1[0] = tile_sheet.getSubimage(x_off + 12 * width,y_off + 5 * height,width,height);
    }

    public BufferedImage[] getBush_1(){
        return bush_1;
    }

    private void getDebrisTextures(){
        int x_off = 304;
        int y_off = 112;
        int width = 8;
        int height = 8;

        debris_1[0] = block_sheet.getSubimage(x_off,y_off,width,height);
        debris_1[1] = block_sheet.getSubimage(x_off,y_off + height,width,height);
        debris_1[2] = block_sheet.getSubimage(x_off + width,y_off,width,height);
        debris_1[3] = block_sheet.getSubimage(x_off + width,y_off + height,width,height);
    }
    private void getEnemy1Texture(){
        int x_off = 0;
        int y_off = 16;
        int width = 16;
        int height = 16;

        goomba_1[0] = enemy_sheet.getSubimage(x_off,y_off,width,height);
        goomba_1[1] = enemy_sheet.getSubimage(x_off,y_off,width,height);
        goomba_1[2] = enemy_sheet.getSubimage(x_off + 2 * width,y_off,width,height);
    }

    private void getShellTexture(){
        int x_off = 0;
        int y_off = 16;
        int width = 16;
        int height = 16;

        shell_1[0] = enemy_sheet.getSubimage(x_off + width * 10, y_off,width,height);
    }

    public BufferedImage[] getShell_1(){
        return shell_1;
    }
    public BufferedImage[] getGoomba_1(){
        return goomba_1;
    }

    private void getItemTextures(){
        int x_off = 0;
        int y_off = 0;
        int width = 16;
        int height = 16;

        item_1[0] = npm_sheet.getSubimage(x_off + width, y_off + height, width, height);
        item_1[1] = npm_sheet.getSubimage(x_off, y_off + 5 * height, width, height);
        item_1[2] = npm_sheet.getSubimage(x_off + width, y_off + 5 * height + height, width, height);
        item_1[3] = npm_sheet.getSubimage(x_off, y_off + 5 * height + height, width, height);
    }

    private void getStarTexture(){
        int x_off = 0;
        int y_off = 0;
        int width = 16;
        int height = 16;

        star_1[0] = npm_sheet.getSubimage(x_off, y_off + 3 * height, width, height);

    }

    public BufferedImage[] getStar_1(){
        return star_1;
    }
    public BufferedImage[] getItem_1() {
        return item_1;
    }
    public BufferedImage[] getDebris_1(){
        return debris_1;
    }
    public BufferedImage[] getMario_l() {
        return mario_l;
    }
    public BufferedImage[] getMario_s() {
        return mario_s;
    }
    public BufferedImage[] getMario_sneak() {
        return mario_sneak;
    }
    public BufferedImage[] getTile_1() {
        return tile_1;
    }
    public BufferedImage[] getPipe_1() {
        return pipe_1;
    }
}
