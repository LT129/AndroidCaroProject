package com.example.caroproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.caroproject.Adapter.FriendListAdapter;
import com.example.caroproject.Data.PlayerInfo;

public class FriendFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;

    private PlayerInfo[] items;
    private ListView listViewFriend;
    private EditText edtSearchBar;

    public FriendFragment() {
        //Empty constructor
    }
    public static FriendFragment newInstance(String param1, String param2) {
        FriendFragment fragment = new FriendFragment();
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

        LinearLayout fragment_1 = (LinearLayout) inflater.inflate(R.layout.fragment_friendlist, null);
        edtSearchBar = (EditText) fragment_1.findViewById(R.id.edtSearchBar);
        listViewFriend = (ListView) fragment_1.findViewById(R.id.listViewFriend);
        items = new PlayerInfo[] {
                new PlayerInfo(null,"Username1", "Password1", null, R.drawable.custom_picture2, null),
                new PlayerInfo(null,"Username2", "Password2", null, R.drawable.custom_picture2, null),
                // Add more PlayerInfo objects as needed
        };
        FriendListAdapter adapter = new FriendListAdapter(context,
                R.layout.custom_friendlist_view, items);
        listViewFriend.setAdapter(adapter);
        return fragment_1;
    }
}
