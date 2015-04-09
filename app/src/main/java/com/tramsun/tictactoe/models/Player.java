package com.tramsun.tictactoe.models;

/**
 * Created by Tushar on 19-03-2015.
 */
public class Player {
    String name;
    int difficulty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Player(String name, int difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }
}
