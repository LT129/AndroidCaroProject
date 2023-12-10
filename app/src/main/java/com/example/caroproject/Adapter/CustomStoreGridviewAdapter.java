package com.example.caroproject.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.caroproject.Data.AppData;
import com.example.caroproject.Data.Background;
import com.example.caroproject.Data.Music;
import com.example.caroproject.Data.UserInfo;
import com.example.caroproject.Data.StoreItem;
import com.example.caroproject.MainActivity;
import com.example.caroproject.R;
import com.example.caroproject.StoreFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomStoreGridviewAdapter extends ArrayAdapter<StoreItem> {
    private Context context;
    private ArrayList<Boolean> userStatus;
    private SharedPreferences pref;
    private UserInfo userInfo;
    private ArrayList<StoreItem> items;
    public CustomStoreGridviewAdapter(Context context, int layoutToBeInflated, ArrayList<StoreItem> items) {
        super(context, layoutToBeInflated, items);
        this.context = context;
        this.items = items;
        initiateData();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.store_item_gridview, null);
        LinearLayout buyItems = view.findViewById(R.id.buyId);

        // Button buy item
        buyItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBuyDialog(context, position, v).show();
            }
        });

        if(userStatus.get(position)) {
            buyItems.setVisibility(View.INVISIBLE);
        }

        ImageView imageView = view.findViewById(R.id.tempImage);
        StoreItem item = items.get(position).getItem();

        if (item.getClass() == Background.class) {
            imageView.setImageResource(((Background) item).getTempImage());
        } else {
            imageView.setImageResource(((Music) item).getImage());
        }

        TextView numberOfCoins = view.findViewById(R.id.numberOfCoins);
        numberOfCoins.setText(String.valueOf(items.get(position).getItemCoins().getCopperCoins()));
        return (view);
    }

    private Dialog showBuyDialog(Context context, int pos, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.dialog_ask_to_buy_item)
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int coins = userInfo.getCoins().getCopperCoins() - items.get(pos).getItemCoins().getCopperCoins();

                        if(coins < 0) {
                            new AlertDialog.Builder(context)
                                    .setMessage("Not enough coins")
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //NOTHING TO DO
                                        }
                                    }).show();
                        } else {
                            // Set view can not buy anymore
                            view.setVisibility(View.INVISIBLE);

                            // Update store items
                            userStatus.set(pos, true);

                            // update user info
                            userInfo.getCoins().setCopperCoins(coins);
                            switch (pref.getInt(StoreFragment.STORE_TYPE, 0)) {
                                case StoreFragment.MODE_BACKGROUND: {
                                    userInfo.setBackgroundStatus(userStatus);
                                    break;
                                }
                                case StoreFragment.MODE_MUSIC: {
                                    userInfo.setMusicStatus(userStatus);
                                    break;
                                }
                            }

                            FirebaseHelper.getInstance().addDataToDatabase("UserInfo",userInfo.getID(), userInfo);
                            updateUserInfoToSharedPreferences(userInfo);
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO nothing happen
                    }
                });
        return builder.create();
    }

    private UserInfo getUserInfoFromSharedPreferences() {
        Gson gson = new Gson();
        String json = pref.getString("USER_INFORMATION", null);
        Type type = new TypeToken<UserInfo>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private void updateUserInfoToSharedPreferences(UserInfo userInfo) {
        Gson gson = new Gson();
        String json = gson.toJson(userInfo);
        pref.edit().putString("USER_INFORMATION", json).apply();
    }

    private void initiateData() {
        pref = context.getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);
        userInfo = getUserInfoFromSharedPreferences();

        switch (pref.getInt(StoreFragment.STORE_TYPE, 0)) {
            case StoreFragment.MODE_BACKGROUND: {
                userStatus = userInfo.getBackgroundStatus();
                break;
            }
            case StoreFragment.MODE_MUSIC: {
                userStatus = userInfo.getMusicStatus();
                break;
            }
        }

    }

}
