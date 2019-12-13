package com.wakeup.wakeup.ObjectClass;

public class Game {

    private int gameOption;
    private int score;
    private String user;

    public Game() {
    }

    public Game(int gameOption, int score, String user) {
        this.gameOption = gameOption;
        this.score = score;
        this.user = user;
    }

    public int getGameOption() {
        return gameOption;
    }

    public int getScore() {
        return score;
    }

    public String getUser() {
        return user;
    }
}
