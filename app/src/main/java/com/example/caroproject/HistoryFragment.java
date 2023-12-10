package com.example.caroproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.caroproject.Adapter.FirebaseHelper;
import com.example.caroproject.Adapter.FriendListAdapter;
import com.example.caroproject.Adapter.MatchHistoryAdapter;
import com.example.caroproject.Data.MatchHistory;
import com.example.caroproject.Data.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class HistoryFragment extends Fragment {

    private ListView listViewHistory;
    private UserInfo userInfo;
    private SharedPreferences pref;

    public HistoryFragment() {
        //Empty constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_match_history, null);
        initiateData();
        listViewHistory = view.findViewById(R.id.listViewHistory);
        if(userInfo.getMatchHistory() == null) {
            userInfo.setMatchHistory(new ArrayList<>());
            ArrayList<MatchHistory> histories = new ArrayList<>();
        }
        ArrayAdapter<MatchHistory> adapter = new MatchHistoryAdapter(requireContext(), R.layout.custom_match_history_view, userInfo.getMatchHistory());
        listViewHistory.setAdapter(adapter);
        return view;
    }

    private void initiateData() {
        pref = requireContext().getSharedPreferences("CARO", Context.MODE_PRIVATE);
        userInfo = getUserInfoFromSharedPreferences();
    }

    private UserInfo getUserInfoFromSharedPreferences() {
        Gson gson = new Gson();
        String json = pref.getString("USER_INFORMATION", null);
        Type type = new TypeToken<UserInfo>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
