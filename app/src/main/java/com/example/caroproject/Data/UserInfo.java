package com.example.caroproject.Data;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
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

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }


    public List<String> getFriends() {
        return friends;
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
