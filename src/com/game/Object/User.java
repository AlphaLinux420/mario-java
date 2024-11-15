package com.game.Object;

public class User {
    private int score;
    private String name;
    private String level;

    public User(String name) {
        this.name = name;
    }

    public User(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public User(int score, String name, String level) {
        this.score = score;
        this.name = name;
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
