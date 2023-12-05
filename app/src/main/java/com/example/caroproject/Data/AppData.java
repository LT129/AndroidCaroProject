package com.example.caroproject.Data;

import com.example.caroproject.R;

import java.util.ArrayList;

public class AppData {

    private ArrayList<Background> backgrounds;
    private ArrayList<StoreItem> storeItems;
    private ArrayList<Integer> musicList;

    public ArrayList<Background> getBackgrounds() {
        return backgrounds;
    }

    public void setBackgrounds(ArrayList<Background> backgrounds) {
        this.backgrounds = backgrounds;
    }

    public ArrayList<StoreItem> getStoreItems() {
        return storeItems;
    }

    public void setStoreItems(ArrayList<StoreItem> storeItems) {
        this.storeItems = storeItems;
    }

    public ArrayList<Integer> getMusicList() {
        return musicList;
    }

    public void setMusicList(ArrayList<Integer> musicList) {
        this.musicList = musicList;
    }

    private static AppData instance;
    private AppData() {
        backgrounds = new ArrayList<>();
        backgrounds.add(new Background(true, R.drawable.temp_background_1, R.drawable.background_1));
        backgrounds.add(new Background(false, R.drawable.temp_background_2, R.drawable.background_2));
        backgrounds.add(new Background(false, R.drawable.temp_background_3, R.drawable.background_3));
        backgrounds.add(new Background(false, R.drawable.temp_background_4, R.drawable.background_4));

        storeItems = new ArrayList<>();
        storeItems.add(new StoreItem(new Coins(0), true, new Background(R.drawable.temp_background_1, R.drawable.background_1)));
        storeItems.add(new StoreItem(new Coins(0), false, new Background(R.drawable.temp_background_2, R.drawable.background_2)));
        storeItems.add(new StoreItem(new Coins(300), false, new Background(R.drawable.temp_background_3, R.drawable.background_3)));
        storeItems.add(new StoreItem(new Coins(400), false, new Background(R.drawable.temp_background_4, R.drawable.background_4)));

        musicList = new ArrayList<>();
        musicList.add(R.raw.music_monoman_v1);
        musicList.add(R.raw.music_monoman_v2);
    }
    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }
}
