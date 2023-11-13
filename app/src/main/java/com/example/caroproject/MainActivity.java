package com.example.caroproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.caroproject.Data.Background;
import com.example.caroproject.Data.Coins;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Coins userCoins;
    private ArrayList<Background> userBackground;
    private static NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialData();
        getWindow().setBackgroundDrawableResource(userBackground.get(0).getLayoutBackground());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Confirm Exit")
                .setMessage("Are you sure you want to exit the application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Thoát ứng dụng
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng hộp thoại và tiếp tục hoạt động ứng dụng
                        dialog.dismiss();
                    }
                })
                .show();
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initialData() {
        userCoins = new Coins(2000);
        userBackground = new ArrayList<>();
        userBackground.add(new Background(R.drawable.temp_background_1, R.drawable.background_1, R.drawable.custom_button_1, R.drawable.custom_edittext));
        userBackground.add(new Background(R.drawable.temp_background_2, R.drawable.background_2, R.drawable.custom_button_1, R.drawable.custom_edittext));
    }

}