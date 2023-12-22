package com.example.caroproject.Data;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class UserInfo {

    private String ID;
    private String username;
    private String email;
    private List<String> friends;
    private List<String> friendRequest;
    private String avatar;
    private ArrayList<MatchHistory> matchHistory;
    private boolean Status;
    private String phoneNumber;
    private Coins coins;
    private ArrayList<Boolean> backgroundStatus;
    private ArrayList<Boolean> musicStatus;
    private int wins;
    private int losses;

    public UserInfo(String ID, String username, String email){
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.friends = null;
        this.avatar = null;
        this.matchHistory = null;
        this.coins = new Coins(50);
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
        this.losses = 0;
        this.wins = 0;
    }

    public UserInfo(FirebaseUser user){
        this.ID = user.getUid();
        this.username = user.getDisplayName();
        this.email = user.getEmail();
        this.friends = null;
        this.avatar = null;
        this.matchHistory = null;
        this.coins = new Coins(100);
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
        this.losses = 0;
        this.wins = 0;
    }

    public UserInfo(String ID, String username, List<String> friends, List<String> friendRequest, String avatar, ArrayList<MatchHistory> matchHistory, boolean status, String email, String phoneNumber, Coins coins) {
        this.ID = ID;
        this.username = username;
        this.friends = friends;
        this.friendRequest = friendRequest;
        this.avatar = avatar;
        this.matchHistory = matchHistory;
        this.Status = status;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.coins = coins;
        this.losses = 0;
        this.wins = 0;
    }

    public UserInfo() {
        this.ID = null;
        this.username = null;
        this.friends = null;
        this.avatar = null;
        this.matchHistory = null;
        this.coins = new Coins(100);
        this.losses = 0;
        this.wins = 0;
    }
    public UserInfo(String username, boolean status, String avatar){
        this.username = username;
        this.Status = status;
        this.avatar = avatar;
        this.coins = new Coins(100);
        this.matchHistory = null;
        this.losses = 0;
        this.wins = 0;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
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

    public List<String> getFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(List<String> friendRequest) {
        this.friendRequest = friendRequest;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<MatchHistory> getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(ArrayList<MatchHistory> matchHistory) {
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
