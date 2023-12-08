package com.example.caroproject.Data;

import java.util.List;


public class UserInfo {
    
    public static int DEFAULT_AVATAR = 0;
    private String ID;
    private String username;
    private String email;
    private String password;
    private List<String> friends;
    private String avatar;
    private MatchHistory[] matchHistory;
    private boolean Status;
    private String phoneNumber;
    private Coins coins;

    public UserInfo(String ID, String userName, String password, List<String> friends, String avatar, MatchHistory[] matchHistory) {
        this.ID = ID;
        this.username = userName;
        this.password = password;
        this.friends = null;
        this.avatar = avatar;
        this.matchHistory = null;
        this.coins = new Coins(1000);
    }
    public UserInfo(String ID, String username, String email, String password){
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends = null;
        this.avatar = null;
        this.matchHistory = null;
        this.coins = new Coins(1000);
    }

    public UserInfo(String ID, String username, String password, List<String> friends, String avatar, MatchHistory[] matchHistory, boolean status, String email, String phoneNumber, Coins coins) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.friends = friends;
        this.avatar = avatar;
        this.matchHistory = matchHistory;
        Status = status;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.coins = coins;
    }

    public UserInfo() {
        this.ID = null;
        this.username = null;
        this.password = null;
        this.friends = null;
        this.avatar = null;
        this.matchHistory = null;
        this.coins = new Coins(1000);
    }
    public UserInfo(String username, boolean status, String avatar){
        this.username = username;
        this.Status = status;
        this.avatar = avatar;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<String> getFriends() {
        return friends;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        if (avatar != null && avatar.matches("\\d+")) {
            // It's a numeric string, convert to int if necessary
            this.avatar = avatar;
        } else {
            // Handle non-numeric or null values
            // You might set a default avatar or handle it according to your app logic
            this.avatar = avatar; // Define a constant or default value
        }
    }

    public MatchHistory[] getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(MatchHistory[] matchHistory) {
        this.matchHistory = matchHistory;
    }

    public boolean isOnline() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Coins getCoins() {
        return coins;
    }

    public void setCoins(Coins coins) {
        this.coins = coins;
    }
}
