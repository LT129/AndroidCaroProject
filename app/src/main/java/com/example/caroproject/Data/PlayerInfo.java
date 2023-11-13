package com.example.caroproject.Data;

import android.media.Image;

public class PlayerInfo {
    private String userName;
    private String password;
    private PlayerInfo[] friends;
    private Integer avatar;
    private MatchHistory[] matchHistory;
    private boolean Status;

    public boolean getStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public PlayerInfo(String userName, String password, PlayerInfo[] friends, Integer avatar, MatchHistory[] matchHistory) {
        this.userName = userName;
        this.password = password;
        this.friends = null;
        this.avatar = avatar;
        this.matchHistory = null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public MatchHistory[] getMatchHistory() {
        return matchHistory;
    }
}
