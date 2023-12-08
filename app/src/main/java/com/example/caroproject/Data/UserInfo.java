package com.example.caroproject.Data;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class UserInfo {


    private String ID;
    private String username;
    private String email;
    private UserInfo[] friends;
    private String avatar;
    private MatchHistory[] matchHistory;
    private boolean Status;
    private String phoneNumber;
    private Coins coins;
    private ArrayList<Boolean> backgroundStatus;
    private ArrayList<Boolean> musicStatus;

    public UserInfo(String ID, String username, String email){
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.friends = null;
        this.avatar = null;
        this.matchHistory = null;
        this.coins = new Coins(1000);
        backgroundStatus = new ArrayList<>();
        backgroundStatus.add(true);
        for(int i = 1; i < AppData.getInstance().getBackgroundList().size(); i++) {
            backgroundStatus.add(false);
        }
        musicStatus = new ArrayList<>();
        musicStatus.add(true);
        for(int i = 1; i < AppData.getInstance().getMusicList().size(); i++) {
            musicStatus.add(false);
        }
    }

    public UserInfo(FirebaseUser user){
        this.ID = user.getUid();
        this.username = user.getDisplayName();
        this.email = user.getEmail();
        this.friends = null;
        this.avatar = null;
        this.matchHistory = null;
        this.coins = new Coins(1000);
        backgroundStatus = new ArrayList<>();
        backgroundStatus.add(true);
        for(int i = 1; i < AppData.getInstance().getBackgroundList().size(); i++) {
            backgroundStatus.add(false);
        }
        musicStatus = new ArrayList<>();
        musicStatus.add(true);
        for(int i = 1; i < AppData.getInstance().getMusicList().size(); i++) {
            musicStatus.add(false);
        }
    }

    public UserInfo() {
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

    public UserInfo[] getFriends() {
        return friends;
    }

    public void setFriends(UserInfo[] friends) {
        this.friends = friends;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
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

    public ArrayList<Boolean> getBackgroundStatus() {
        return backgroundStatus;
    }

    public void setBackgroundStatus(ArrayList<Boolean> backgroundStatus) {
        this.backgroundStatus = backgroundStatus;
    }

    public ArrayList<Boolean> getMusicStatus() {
        return musicStatus;
    }

    public void setMusicStatus(ArrayList<Boolean> musicStatus) {
        this.musicStatus = musicStatus;
    }
}
