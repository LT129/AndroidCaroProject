package com.example.caroproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.caroproject.Adapter.DBHelper;
import com.example.caroproject.Adapter.FriendListAdapter;
import com.example.caroproject.Data.UserInfo;

public class HistoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;

    private UserInfo[] items;
    private ListView listViewHistory;
    private DBHelper dbHelper;

    public HistoryFragment() {
        //Empty constructor
    }
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity(); // use this reference to invoke main callbacks
        }
        catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout fragment_match_history = (LinearLayout) inflater.inflate(R.layout.fragment_match_history, null);
        listViewHistory = (ListView) fragment_match_history.findViewById(R.id.listViewHistory);

//        items = new PlayerInfo[] {
//                new PlayerInfo(null,"Username1", "Password1", null, R.drawable.custom_picture2, null),
//                new PlayerInfo(null,"Username2", "Password2", null, R.drawable.custom_picture2, null),
//                // Add more PlayerInfo objects as needed
//        };
//        FriendListAdapter adapter = new FriendListAdapter(context,
////                R.layout.custom_match_history_view, items);
//        listViewHistory.setAdapter(adapter);
        return fragment_match_history;
    }
}
