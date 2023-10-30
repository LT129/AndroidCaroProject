package com.example.caroproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caroproject.Data.StoreItems;
import com.example.caroproject.R;

public class CustomStoreGridviewAdapter extends ArrayAdapter<StoreItems> {
    private Context context;
    private StoreItems[] items;
    public CustomStoreGridviewAdapter(Context context, int layoutToBeInflated, StoreItems[] items) {
        super(context, layoutToBeInflated, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.store_item_gridview, null);

        ImageView imageView = view.findViewById(R.id.tempImage);
        imageView.setImageResource(items[position].getTempImage());

        TextView numberOfCoins = view.findViewById(R.id.numberOfCoins);
        numberOfCoins.setText("200");
        return (view);
    }
}
