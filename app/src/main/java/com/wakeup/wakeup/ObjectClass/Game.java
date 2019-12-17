package com.wakeup.wakeup.ObjectClass;

public class Game {

    private int gameOption;
    private int score;
    private String phoneNum;
    private String user;

    public Game() {
    }

    public Game(int gameOption, int score) {
        this.gameOption = gameOption;
        this.score = score;
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

    public void setGameOption(int gameOption) {
        this.gameOption = gameOption;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
