package com.example.caroproject.Data;


import java.util.Map;

public class Room {
    private String currentPlayer;
    private int sizeBoard;
    private int position;
    private String player1;
    private String player2;
    private String roomId;
    private String winner;
    private String message;
    private String keyMessage;
    private boolean isGameOver;
    private int countTurn;
    private int time;
    private int rematchPlayer1;
    private int rematchPlayer2;
    private boolean checkRematch;
    private boolean checkRandom;

    public Room(boolean checkRandom,boolean checkRematch, int rematchPlayer1, int rematchPlayer2, String keyMessage, String message, int time ,String currentPlayer, int sizeBoard, int position, String player1, String player2, String roomId, String winner, boolean isGameOver, int countTurn) {
        this.checkRandom=checkRandom;
        this.checkRematch=checkRematch;
        this.rematchPlayer1=rematchPlayer1;
        this.rematchPlayer2=rematchPlayer2;
        this.keyMessage=keyMessage;
        this.message=message;
        this.time = time;
        this.currentPlayer = currentPlayer;
        this.sizeBoard = sizeBoard;
        this.position = position;
        this.player1 = player1;
        this.player2 = player2;
        this.roomId = roomId;
        this.winner = winner;
        this.isGameOver = isGameOver;
        this.countTurn = countTurn;
    }

    public boolean isCheckRandom() {
        return checkRandom;
    }

    public void setCheckRandom(boolean checkRandom) {
        this.checkRandom = checkRandom;
    }

    public int getRematchPlayer1() {
        return rematchPlayer1;
    }

    public int getRematchPlayer2() {
        return rematchPlayer2;
    }

    public boolean isCheckRematch() {
        return checkRematch;
    }

    public void setCheckRematch(boolean checkRematch) {
        this.checkRematch = checkRematch;
    }

    public void setRematchPlayer1(int rematchPlayer1) {
        this.rematchPlayer1 = rematchPlayer1;
    }

    public void setRematchPlayer2(int rematchPlayer2) {
        this.rematchPlayer2 = rematchPlayer2;
    }

    public String getKeyMessage() {
        return keyMessage;
    }

    public void setKeyMessage(String keyMessage) {
        this.keyMessage = keyMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Room() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCountTurn() {
        return countTurn;
    }

    public void setCountTurn(int countTurn) {
        this.countTurn = countTurn;
    }
    public int getSizeBoard() {
        return sizeBoard;
    }

    public void setSizeBoard(int sizeBoard) {
        this.sizeBoard = sizeBoard;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
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
