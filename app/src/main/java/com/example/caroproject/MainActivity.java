package com.example.caroproject;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.caroproject.Data.AppData;
import com.example.caroproject.Data.Background;
import com.example.caroproject.Data.Music;
import com.example.caroproject.Data.SoundMaking;

public class MainActivity extends AppCompatActivity {
    public final static String PREF_FILE = "CARO";
    public final static String LOGGED_IN_ACCOUNT = "username";
    public final static String BACKGROUND = "background";
    public final static String MUSIC = "music";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startSetUpAppSetting();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void startSetUpAppSetting() {
        SharedPreferences pref = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        int backgroundPosition;
        int musicPosition;
//        pref.edit().clear().apply();

        if(pref != null) {
            backgroundPosition = pref.getInt(BACKGROUND, 0);
            musicPosition = pref.getInt(MUSIC, 0);
        } else {
            backgroundPosition = 0;
            musicPosition = 0;
        }
        //TODO create sharedPreferences for setting

        AppData appData = AppData.getInstance();
        getWindow().setBackgroundDrawableResource(((Background)appData.getBackgroundList().get(backgroundPosition).getItem()).getLayoutBackground());
        SoundMaking.getInstance().createMusic(this,((Music) appData.getMusicList().get(musicPosition).getItem()).getSourceId());
        SoundMaking.getInstance().playMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SoundMaking.getInstance().pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundMaking.getInstance().playMusic();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoundMaking.getInstance().releaseMusic();
    }

}