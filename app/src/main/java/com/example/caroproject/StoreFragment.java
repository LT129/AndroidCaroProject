package com.example.caroproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.caroproject.Adapter.CustomStoreGridviewAdapter;
import com.example.caroproject.Data.AppData;
import com.example.caroproject.Data.Background;
import com.example.caroproject.Data.Coins;
import com.example.caroproject.Data.UserInfo;
import com.example.caroproject.Data.StoreItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StoreFragment extends Fragment {

    public static final String STORE_TYPE = "store_type";
    public static final int MODE_MUSIC = 1;
    public static final int MODE_BACKGROUND = 2;

    private TextView txtUserCoins;
    private GridView viewItems;
    private ArrayList<StoreItem> items;
    private ImageButton btnCallBack;

    private RadioGroup groupButton;
    private RadioButton btnBackground;
    private RadioButton btnMusic;

    private SharedPreferences pref;
    private Gson gson;
    private Coins userCoins;

    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initiateData(view);

        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
                if (key != null && key.equals("USER_INFORMATION")) {
                    Gson gson = new Gson();
                    String json = pref.getString("USER_INFORMATION", null);
                    Type type = new TypeToken<UserInfo>() {
                    }.getType();
                    UserInfo userInfo = gson.fromJson(json, type);
                    txtUserCoins.setText(String.valueOf(userInfo.getCoins().getCopperCoins()));
                }
            }
        };
        pref.registerOnSharedPreferenceChangeListener(listener);


        userCoins = getUserInfoFromSharedPreferences().getCoins();
        txtUserCoins.setText(String.valueOf(userCoins.getCopperCoins()));

        // Show template of the item
        btnBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items = AppData.getInstance().getBackgroundList();
                pref.edit().putInt(STORE_TYPE, MODE_BACKGROUND).apply();
                CustomStoreGridviewAdapter customStoreGridviewAdapter =
                        new CustomStoreGridviewAdapter(view.getContext(), R.layout.store_item_gridview,
                                items);
                viewItems.setAdapter(customStoreGridviewAdapter);
                viewItems.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
            }
        });

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items = AppData.getInstance().getMusicList();
                pref.edit().putInt(STORE_TYPE, MODE_MUSIC).apply();
                CustomStoreGridviewAdapter customStoreGridviewAdapter =
                        new CustomStoreGridviewAdapter(view.getContext(), R.layout.store_item_gridview,
                                items);
                viewItems.setAdapter(customStoreGridviewAdapter);
                viewItems.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
            }
        });




        groupButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                btnBackground.setBackgroundResource(R.drawable.store_wait_choose_button);
                btnMusic.setBackgroundResource(R.drawable.store_wait_choose_button);
                view.findViewById(checkedId).setBackgroundResource(R.drawable.store_on_choose_button);
            }
        });

        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        return view;
    }

    private UserInfo getUserInfoFromSharedPreferences() {
        Gson gson = new Gson();
        String json = pref.getString("USER_INFORMATION", null);
        Type type = new TypeToken<UserInfo>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private void initiateData(View view) {
        txtUserCoins = view.findViewById(R.id.txtUserCoins);
        viewItems = view.findViewById(R.id.viewItems);
        groupButton = view.findViewById(R.id.groupButton);
        btnBackground = view.findViewById(R.id.btnBackground);
        btnMusic = view.findViewById(R.id.btnMusic);
        btnCallBack = view.findViewById(R.id.btnCallBack);
        pref = requireActivity().getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);


        items = AppData.getInstance().getBackgroundList();
        pref.edit().putInt(STORE_TYPE, MODE_BACKGROUND).apply();
        CustomStoreGridviewAdapter customStoreGridviewAdapter =
                new CustomStoreGridviewAdapter(view.getContext(), R.layout.store_item_gridview,
                        items);
        viewItems.setAdapter(customStoreGridviewAdapter);
        viewItems.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
    }

}