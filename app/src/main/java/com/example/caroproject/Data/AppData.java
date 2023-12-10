package com.example.caroproject.Data;

import com.example.caroproject.R;

import java.util.ArrayList;

public class AppData {

    private ArrayList<StoreItem> backgroundList;
    private ArrayList<StoreItem> musicList;

    public ArrayList<StoreItem> getBackgroundList() {
        return backgroundList;
    }

    public void setBackgroundList(ArrayList<StoreItem> backgroundList) {
        this.backgroundList = backgroundList;
    }

    public ArrayList<StoreItem> getMusicList() {
        return musicList;
    }

    public void setMusicList(ArrayList<StoreItem> musicList) {
        this.musicList = musicList;
    }

    private static AppData instance;
    private AppData() {

        backgroundList = new ArrayList<>();
        backgroundList.add(new StoreItem(new Coins(0), new Background(R.drawable.temp_background_1, R.drawable.background_1)));
        backgroundList.add(new StoreItem(new Coins(50), new Background(R.drawable.temp_background_2, R.drawable.background_2)));
        backgroundList.add(new StoreItem(new Coins(300), new Background(R.drawable.temp_background_3, R.drawable.background_3)));
        backgroundList.add(new StoreItem(new Coins(400), new Background(R.drawable.temp_background_4, R.drawable.background_4)));

        musicList = new ArrayList<>();
        musicList.add(new StoreItem(new Coins(0), new Music(R.raw.music_monoman_v1, "monoman_v1", R.drawable.music_pic_monoman_v1)));
        musicList.add(new StoreItem(new Coins(50), new Music(R.raw.music_monoman_v2, "monoman_v2", R.drawable.music_pic_monoman_v2)));
    }
    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }
}
