package com.example.caroproject.Data;

public class UserInfo {


    private String ID;
    private String username;
    private String email;
    private String password;
    private UserInfo[] friends;
    private Integer avatar;
    private MatchHistory[] matchHistory;
    private boolean Status;
    private String phoneNumber;
    private Coins coins;

    public UserInfo(String ID, String userName, String password, UserInfo[] friends, Integer avatar, MatchHistory[] matchHistory) {
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

    public UserInfo(String ID, String username, String password, UserInfo[] friends, Integer avatar, MatchHistory[] matchHistory, boolean status, String email, String phoneNumber, Coins coins) {
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
    public UserInfo(String username, boolean status, Integer avatar){
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

    public UserInfo[] getFriends() {
        return friends;
    }

    public void setFriends(UserInfo[] friends) {
        this.friends = friends;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public MatchHistory[] getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(MatchHistory[] matchHistory) {
        this.matchHistory = matchHistory;
    }

    public boolean isStatus() {
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
