package com.example.caroproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caroproject.Data.MatchHistory;
import com.example.caroproject.R;

public class MatchHistoryAdapter extends ArrayAdapter<MatchHistory> {
    private Context context;
    private MatchHistory[] items;
    public MatchHistoryAdapter(Context context,int layout,MatchHistory[] items){
        super(context, R.layout.custom_match_history_view,items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.custom_match_history_view, null);
        TextView opponent = (TextView) row.findViewById(R.id.txtViewOpponent);
        ImageView avatar = (ImageView) row.findViewById(R.id.imgViewAvatar);
        TextView status = (TextView) row.findViewById(R.id.txtViewStatus);
        TextView time =  (TextView) row.findViewById(R.id.txtViewTime);
        return (row);
    }
}
