package com.example.caroproject.Data;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.caroproject.R;

import java.io.IOException;

public class SoundMaking {

    private MediaPlayer music;
    public void setMusic(MediaPlayer music) {
        this.music = music;
    }
    public MediaPlayer getMusic() {
        return music;
    }


    public void createMusic(Context context, int idSource) {
        music = MediaPlayer.create(context, idSource);
    }
    public void playMusic() {
        music.start();
    }
    public void pauseMusic() {
        music.pause();
    }
    public void releaseMusic() {
        music.release();
    }


    private static SoundMaking instance;
    private SoundMaking() {}
    public static SoundMaking getInstance() {
        if (instance == null) {
            instance = new SoundMaking();
        }
        return instance;
    }


}
