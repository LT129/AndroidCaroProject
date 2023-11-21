package com.example.caroproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.caroproject.Data.Background;
import com.example.caroproject.Data.Coins;
import com.example.caroproject.Data.StoreItems;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Coins userCoins;
    private ArrayList<Background> userBackground;
    private ArrayList<StoreItems> storeItems;
    private SharedPreferences pref;
    private static NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialData();

        pref = getSharedPreferences("CARO", Context.MODE_PRIVATE);

        loadData();
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        new AlertDialog.Builder(this)
//                .setTitle("Confirm Exit")
//                .setMessage("Are you sure you want to exit the application?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Thoát ứng dụng
//                        finish();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Đóng hộp thoại và tiếp tục hoạt động ứng dụng
//                        dialog.dismiss();
//                    }
//                })
//                .show();
//    }



    @Override
    protected void onPause() {
        super.onPause();
//        pref.edit().clear().apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String position;

        if(pref != null && pref.contains("BG_POSITION")) {
            position = pref.getString("BG_POSITION", "0");
        } else {
            position = "0";
        }

        getWindow().setBackgroundDrawableResource(userBackground.get(Integer.parseInt(position)).getLayoutBackground());
    }

    private void initialData() {
        userCoins = new Coins(2000);
        userBackground = new ArrayList<>();
        userBackground.add(new Background(R.drawable.temp_background_1, R.drawable.background_1, R.drawable.custom_button_1, R.drawable.custom_edittext));

        storeItems = new ArrayList<>();
        storeItems.add(new StoreItems(R.drawable.temp_background_2, R.drawable.background_2, R.drawable.custom_button_2, R.drawable.custom_edittext, new Coins(300)));
        storeItems.add(new StoreItems(R.drawable.temp_background_3, R.drawable.background_3, R.drawable.custom_button_1, R.drawable.custom_edittext, new Coins(400)));
        storeItems.add(new StoreItems(R.drawable.temp_background_4, R.drawable.background_4, R.drawable.custom_button_2, R.drawable.custom_edittext, new Coins(500)));
    }

    private void loadData() {
        Gson gson = new Gson();
        String json;

        if(pref != null) {
            System.out.println("ins");
            Type type;
            if(pref.contains("USER_COINS")) {
                // Get user coins from Shared Preferences
                json = pref.getString("USER_COINS", null);
                userCoins = gson.fromJson(json, Coins.class);
            } else {
                // Save user coins to preferences
                json = gson.toJson(userCoins);
                pref.edit().putString("USER_COINS", json).apply();

            }

            if(pref.contains("USER_BACKGROUND")) {
                // Get user background
                json = pref.getString("USER_BACKGROUND", null);
                type = new TypeToken<ArrayList<Background>>() {
                }.getType();
                userBackground = gson.fromJson(json, type);
            } else {
                // Save user background to preferences
                json = gson.toJson(userBackground);
                pref.edit().putString("USER_BACKGROUND", json).apply();
            }

            if(pref.contains("STORE_ITEMS")) {
                // Get storeItems
                json = pref.getString("STORE_ITEMS", null);
                type = new TypeToken<ArrayList<StoreItems>>(){}.getType();
                storeItems = gson.fromJson(json, type);
            } else {
                // Save store items to preferences
                json = gson.toJson(storeItems);
                pref.edit().putString("STORE_ITEMS", json).apply();
            }
        }
    }

}