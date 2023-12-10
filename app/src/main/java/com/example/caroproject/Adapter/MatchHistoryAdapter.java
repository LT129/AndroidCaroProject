package com.example.caroproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.caroproject.Data.MatchHistory;
import com.example.caroproject.Data.UserInfo;
import com.example.caroproject.R;

import java.util.ArrayList;
import java.util.List;

public class MatchHistoryAdapter extends ArrayAdapter<MatchHistory> {
    private Context context;
    private ArrayList<MatchHistory> items;
    public MatchHistoryAdapter(Context context, int layout, ArrayList<MatchHistory> items){
        super(context, R.layout.custom_match_history_view,items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.custom_match_history_view, null);
        TextView matchResult = (TextView) row.findViewById(R.id.matchResult);
        ImageView avatar = (ImageView) row.findViewById(R.id.imgViewAvatar);
        TextView opponentName = (TextView) row.findViewById(R.id.opponentName);
        TextView date =  (TextView) row.findViewById(R.id.date);
        row.setBackgroundResource((items.get(position).isResult())?R.drawable.custom_background_match_history_victory:R.drawable.custom_background_match_history_defeat);

        FirebaseHelper.getInstance().retrieveDataFromDatabase("UserInfo", items.get(position).getUserId(), UserInfo.class, new FirebaseHelper.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInfo userInfo = (UserInfo) list.get(0);
                Glide.with(row).load(userInfo.getAvatar()).error(R.drawable.user_account).into(avatar);
                opponentName.setText(userInfo.getUsername());
            }
        });

        matchResult.setText((items.get(position).isResult())?"Victory":"Defeat");
        date.setText(items.get(position).getDate().toString());

        return (row);
    }
}
