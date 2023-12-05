package com.example.caroproject.Data;

import java.sql.Time;

public class MatchHistory {
    private UserInfo opponent;
    private Time time;
    private boolean result;

    public MatchHistory(UserInfo opponent, Time time, boolean result) {
        this.opponent = opponent;
        this.time = time;
        this.result = result;
    }

    public UserInfo getOpponent() {
        return opponent;
    }

    public void setOpponent(UserInfo opponent) {
        this.opponent = opponent;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
