package com.example.caroproject.Data;

public class StoreItem{
    private Coins itemCoins;
    private Boolean status;
    private StoreItem item;

    public Coins getItemCoins() {
        return itemCoins;
    }

    public void setItemCoins(Coins itemCoins) {
        this.itemCoins = itemCoins;
    }

    public Boolean wasSold() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public StoreItem getItem() {
        return item;
    }

    public void setItem(StoreItem item) {
        this.item = item;
    }

    public StoreItem(Coins itemCoins, Boolean status, StoreItem item) {
        this.itemCoins = itemCoins;
        this.status = status;
        this.item = item;
    }

    public StoreItem(Boolean status) {
        this.status = status;
    }

    public StoreItem(){}
}
