package com.example.caroproject.Data;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatRoom {
    String ChatroomID;
    List<String> userIDs;
    com.google.firebase.Timestamp LastMsgTimeStamp;
    String LastMsgID;
    public ChatRoom(){
        //default constructor
    }


    public ChatRoom(String chatroomID, List<String> userIDs, com.google.firebase.Timestamp now, String lastMsgID) {
        ChatroomID = chatroomID;
        this.userIDs = userIDs;
        LastMsgTimeStamp = now;
        LastMsgID = lastMsgID;
    }

    public String getChatroomID() {
        return ChatroomID;
    }

    public void setChatroomID(String chatroomID) {
        ChatroomID = chatroomID;
    }

    public List<String> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(List<String> userIDs) {
        this.userIDs = userIDs;
    }

    public Timestamp getLastMsgTimeStamp() {
        return LastMsgTimeStamp;
    }

    public void setLastMsgTimeStamp(Timestamp lastMsgTimeStamp) {
        LastMsgTimeStamp = lastMsgTimeStamp;
    }

    public String getLastMsgID() {
        return LastMsgID;
    }

    public void setLastMsgID(String lastMsgID) {
        LastMsgID = lastMsgID;
    }
}
