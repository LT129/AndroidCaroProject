package com.example.caroproject.Data;

import android.media.Image;

public class PlayerInfo {


    private Integer ID;
    private String username;
    private String password;
    private PlayerInfo[] friends;
    private Integer avatar;
    private MatchHistory[] matchHistory;
    private boolean Status;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Coins coins;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public Coins getCoins() {
        return coins;
    }

    public void setCoins(Coins coins) {
        this.coins = coins;
    }

    public PlayerInfo(Integer ID, String userName, String password, PlayerInfo[] friends, Integer avatar, MatchHistory[] matchHistory) {
        this.ID = ID;
        this.username = userName;
        this.password = password;
        this.friends = null;
        this.avatar = avatar;
        this.matchHistory = null;
        this.coins = new Coins(1000);
    }
    public PlayerInfo(String username,String password){
        this.ID = null;
        this.username = username;
        this.password = password;
        this.friends = null;
        this.avatar = null;
        this.matchHistory = null;
        this.coins = new Coins(1000);
    }

    public PlayerInfo(Integer ID, String username, String password, PlayerInfo[] friends, Integer avatar, MatchHistory[] matchHistory, boolean status, String nickname, String email, String phoneNumber, Coins coins) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.friends = friends;
        this.avatar = avatar;
        this.matchHistory = matchHistory;
        Status = status;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.coins = coins;
    }

    public PlayerInfo() {
        this.ID = null;
        this.username = null;
        this.password = null;
        this.friends = null;
        this.avatar = null;
        this.matchHistory = null;
        this.coins = new Coins(1000);
    }
    public PlayerInfo(String username,boolean status,Integer avatar){
        this.userName = username;
        this.Status = status;
        this.avatar = avatar;
    }

    public PlayerInfo() {
        //default constructor
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAvatar() {
        return avatar;
    }
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public MatchHistory[] getMatchHistory() {
        return matchHistory;
    }
    public PlayerInfo[] getFriends() {
        return friends;
    }
}
