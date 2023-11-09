package com.example.caroproject.Data;

public class StoreItems extends Background{
    private Coins itemCoins;
    public StoreItems(int tempImage, int layoutBackground, int imageButtonBackground, int textViewBackground, Coins itemCoins) {
        super(tempImage, layoutBackground, imageButtonBackground, textViewBackground);
        this.itemCoins = itemCoins;
    }

    public Coins getItemCoins() {
        return itemCoins;
    }

    public void setItemCoins(Coins itemCoins) {
        this.itemCoins = itemCoins;
    }
}
