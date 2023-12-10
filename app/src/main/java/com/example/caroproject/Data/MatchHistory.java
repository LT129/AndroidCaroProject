package com.example.caroproject.Data;

import java.sql.Time;
import java.util.Date;

public class MatchHistory {
    private String userId;
    private Date date;
    private boolean result;

    public MatchHistory() {
    }

    public MatchHistory(String userId, Date date, boolean result) {
        this.userId = userId;
        this.date = date;
        this.result = result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
