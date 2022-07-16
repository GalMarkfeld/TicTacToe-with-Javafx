package com.example.tictactoev4;

import java.util.LinkedList;

public class Player {

    public LinkedList<Integer> positions;
    private char character;
    private String name;
    private int playerNumber;
    public int winCount;

    public Player(char character, int playerNumber) {
        this.positions = new LinkedList<>();
        this.character = character;
        this.name = " ";
        this.playerNumber = playerNumber;
        this.winCount = 0;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void resetPos(){
        this.positions.clear();
    }
}
