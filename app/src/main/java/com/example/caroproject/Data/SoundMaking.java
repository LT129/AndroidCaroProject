package com.example.caroproject.Data;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.caroproject.R;

public class SoundMaking {
    private static MediaPlayer buttonSound;
    public static void createSound(Context context) {
        buttonSound = MediaPlayer.create(context, R.raw.button_click_2);
    }
    //TODO The mediaplayer is still not working
    public static void buttonClickedSound() {
        buttonSound.start();
    }
}
