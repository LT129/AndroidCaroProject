package com.example.caroproject.Data;

public class StoreItems extends Background{
    private Coins itemCoins;
    private Boolean wasSold;
    public StoreItems(int tempImage, int layoutBackground, int imageButtonBackground, int textViewBackground, Coins itemCoins) {
        this(tempImage, layoutBackground, imageButtonBackground, textViewBackground, itemCoins, false);
    }

    public StoreItems(int tempImage, int layoutBackground, int imageButtonBackground, int textViewBackground, Coins itemCoins, Boolean wasSold) {
        super(tempImage, layoutBackground, imageButtonBackground, textViewBackground);
        this.itemCoins = itemCoins;
        this.wasSold = wasSold;
    }

    public Coins getItemCoins() {
        return itemCoins;
    }

    public void setItemCoins(Coins itemCoins) {
        this.itemCoins = itemCoins;
    }

    public Boolean getWasSold() {
        return wasSold;
    }

    public void setWasSold(Boolean wasSold) {
        this.wasSold = wasSold;
    }
}
