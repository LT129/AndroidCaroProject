package com.example.caroproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.caroproject.Data.Background;
import com.example.caroproject.R;

public class CustomChooseBackgroundAdapter extends ArrayAdapter<Background> {
    private Context context;
    private Background[] items;
    public CustomChooseBackgroundAdapter(Context context, int layoutToBeInflated, Background[] items) {
        super(context, layoutToBeInflated, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_item_choose_background_gridview, null);
        ImageView imageView = view.findViewById(R.id.tempImage);
        imageView.setImageResource(items[position].getTempImage());
        return (view);
    }
}
