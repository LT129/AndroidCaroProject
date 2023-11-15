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

import com.example.caroproject.Data.Background;
import com.example.caroproject.Data.Coins;
import com.example.caroproject.Data.StoreItems;
import com.example.caroproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomStoreGridviewAdapter extends ArrayAdapter<StoreItems> {
    private Context context;
    private ArrayList<StoreItems> items;
    private SharedPreferences storePref;
    private Gson gson;
    private Coins userCoins;
    public CustomStoreGridviewAdapter(Context context, int layoutToBeInflated, ArrayList<StoreItems> items) {
        super(context, layoutToBeInflated, items);
        this.context = context;
        this.items = items;
        storePref = context.getSharedPreferences("CARO", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = storePref.getString("USER_COINS", null);
        userCoins = gson.fromJson(json, Coins.class);
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
                showBuyDialog(context, items.get(position), v).show();
            }
        });
        if(items.get(position).getWasSold()) {
            buyItems.setVisibility(View.INVISIBLE);
        }

        ImageView imageView = view.findViewById(R.id.tempImage);
        imageView.setImageResource(items.get(position).getTempImage());

        TextView numberOfCoins = view.findViewById(R.id.numberOfCoins);
        numberOfCoins.setText(String.valueOf(items.get(position).getItemCoins().getCopperCoins()));
        return (view);
    }

    private Dialog showBuyDialog(Context context, StoreItems item, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.dialog_ask_to_buy_item)
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(userCoins.getCopperCoins() < item.getItemCoins().getCopperCoins()) {
                            new AlertDialog.Builder(context)
                                    .setMessage("Not enough coins")
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //NOTHING TO DO
                                        }
                                    }).show();
                        } else {
                            // Update user coins
                            userCoins.setCopperCoins(userCoins.getCopperCoins() - item.getItemCoins().getCopperCoins());
                            String json = gson.toJson(userCoins);
                            storePref.edit().putString("USER_COINS", json).apply();

                            // Set view can not buy anymore
                            view.setVisibility(View.INVISIBLE);

                            // Update user Background
                            json = storePref.getString("USER_BACKGROUND", null);
                            Type type = new TypeToken<ArrayList<Background>>(){}.getType();
                            ArrayList<Background> userBackground = gson.fromJson(json, type);
                            userBackground.add(item);
                            json = gson.toJson(userBackground);
                            storePref.edit().putString("USER_BACKGROUND", json).apply();

                            // Update store items
                            json = storePref.getString("STORE_ITEMS", null);
                            type = new TypeToken<ArrayList<StoreItems>>(){}.getType();
                            ArrayList<StoreItems> storeItems = gson.fromJson(json, type);
                            storeItems.get(getPosition(item)).setWasSold(true);
                            json = gson.toJson(storeItems);
                            storePref.edit().putString("STORE_ITEMS", json).apply();
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
}
