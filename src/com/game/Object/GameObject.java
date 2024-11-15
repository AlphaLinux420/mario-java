package com.game.Object;

import java.awt.*;

import com.game.Object.Util.ObjectId;

public abstract class GameObject {
    private float x;
    private float y;
    private final ObjectId id;
    private float velocityX;
    private float velocityY;
    private float width;
    private float height;
    private int scale;
    private final boolean shouldRemove;

    protected GameObject(float x, float y, ObjectId id, float width, float height, int scale){
        this.x = x * scale;
        this.y = y * scale;
        this.id = id;
        this.width = width * scale;
        this.height = height * scale;
        this.scale = scale;
        shouldRemove = false;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();
    public abstract Rectangle getBoundsTop();
    public abstract Rectangle getBoundsLeft();
    public abstract Rectangle getBoundsRight();

    public void applyGravity(){
        velocityY += 0.75f;
    }

    public void setGravityToZero() {
        velocityY = 0f;
    }

    public boolean shouldRemove() {
        return shouldRemove;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ObjectId getId() {
        return id;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width * scale;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height * scale;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
