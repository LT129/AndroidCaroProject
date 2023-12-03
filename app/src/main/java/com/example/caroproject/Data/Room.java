package com.example.caroproject.Data;


import java.util.Map;

public class Room {
    private int currentPlayer;
    private int position;
    private int timeCurrentPlayer;
    private String roomId;
    private String userId1;
    private String userId2;

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    public Room(String roomId, int timeCurrentPlayer) {
        this.roomId = roomId;
        this.timeCurrentPlayer = timeCurrentPlayer;
    }


    // Các hàm getter và setter

    public Room() {
        // Hàm tạo mặc định để phục vụ cho Firebase
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTimeCurrentPlayer() {
        return timeCurrentPlayer;
    }

    public void setTimeCurrentPlayer(int timeCurrentPlayer) {
        this.timeCurrentPlayer = timeCurrentPlayer;
    }

}
