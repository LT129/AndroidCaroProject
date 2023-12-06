package com.example.caroproject.Data;

public class StoreItem{
    private Coins itemCoins;
    private StoreItem item;

    public Coins getItemCoins() {
        return itemCoins;
    }

    public void setItemCoins(Coins itemCoins) {
        this.itemCoins = itemCoins;
    }

    public StoreItem getItem() {
        return item;
    }

    public void setItem(StoreItem item) {
        this.item = item;
    }

    public StoreItem(Coins itemCoins, StoreItem item) {
        this.itemCoins = itemCoins;
        this.item = item;
    }


    public StoreItem(){}
}
