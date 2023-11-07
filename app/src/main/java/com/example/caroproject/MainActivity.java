package com.example.caroproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.drawable.background_1);
    }
    @Override
    public void onBackPressed() {
        //vô hiệu hóa nút back trên điện thoại để tránh lỗi không mong muốn
        }


    @Override
    protected void onPause() {
        super.onPause();
    }

}