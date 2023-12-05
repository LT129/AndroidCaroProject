package com.example.caroproject.Data;


import java.util.Map;

public class Room {
    private String currentPlayer;
    private int position;
    private int timeTurn;
    private String player1;
    private String player2;
    private String roomId;
    private String winner;
    private boolean isGameOver;

    public Room(int timeTurn, String roomId, boolean isGameOver) {
        this.timeTurn = timeTurn;
        this.roomId = roomId;
        this.isGameOver = isGameOver;
    }

    public Room() {
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTimeTurn() {
        return timeTurn;
    }

    public void setTimeTurn(int timeTurn) {
        this.timeTurn = timeTurn;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
