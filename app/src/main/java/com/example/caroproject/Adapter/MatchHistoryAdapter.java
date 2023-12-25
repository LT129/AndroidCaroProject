package com.example.caroproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.caroproject.Data.MatchHistory;
import com.example.caroproject.Data.UserInfo;
import com.example.caroproject.R;
import com.example.caroproject.ShowInfoFragment;

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

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(ShowInfoFragment.USER_ID, items.get(position).getUserId());
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_mainMenuFragment_to_showInfoFragment, args);
            }
        });

        matchResult.setText((items.get(position).isResult())?"Victory":"Defeat");
        date.setText(items.get(position).getDate().toString());

        return (row);
    }
}
