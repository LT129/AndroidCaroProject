package com.example.caroproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.caroproject.Adapter.CustomStoreGridviewAdapter;
import com.example.caroproject.Data.Background;
import com.example.caroproject.Data.Coins;
import com.example.caroproject.Data.StoreItems;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView txtUserCoins;
    private GridView viewItems;
    private ArrayList<StoreItems> storeItems;
    private ImageButton btnCallBack;
    private ImageView showItem;

    private RadioGroup groupButton;
    private RadioButton btnBackground;
    private RadioButton btnShape;

    private SharedPreferences storePref;
    private Gson gson;
    private Coins userCoins;

    public StoreFragment() {
        // Required empty public constructor
    }
    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        getData();

        // Get user coins from Preferences
        txtUserCoins = view.findViewById(R.id.txtUserCoins);
        storePref = requireContext().getSharedPreferences("CARO", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = storePref.getString("USER_COINS", "");
        userCoins = gson.fromJson(json, Coins.class);
        txtUserCoins.setText(String.valueOf(userCoins.getCopperCoins()));
        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
                if (key.equals("USER_COINS")) {
                    String json = sharedPreferences.getString("USER_COINS", "");
                    userCoins = gson.fromJson(json, Coins.class);
                    txtUserCoins.setText(String.valueOf(userCoins.getCopperCoins()));
                }
            }
        };
        storePref.registerOnSharedPreferenceChangeListener(listener);


        // Show template of the ite
        showItem = view.findViewById(R.id.showItem);

        // Show store items
        viewItems = view.findViewById(R.id.viewItems);
        CustomStoreGridviewAdapter customStoreGridviewAdapter =
                new CustomStoreGridviewAdapter(view.getContext(), R.layout.store_item_gridview, storeItems);
        viewItems.setAdapter(customStoreGridviewAdapter);
        viewItems.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        viewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewItems.setItemChecked(position, true);
                showItem.setImageResource(storeItems.get(position).getLayoutBackground());
            }
        });

        groupButton = view.findViewById(R.id.groupButton);
        btnBackground = view.findViewById(R.id.btnBackground);
        btnShape = view.findViewById(R.id.btnShape);

        groupButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                btnBackground.setBackgroundResource(R.drawable.store_wait_choose_button);
                btnShape.setBackgroundResource(R.drawable.store_wait_choose_button);
                view.findViewById(checkedId).setBackgroundResource(R.drawable.store_on_choose_button);
            }
        });

        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        return view;
    }

    private void getData() {
        storePref = requireActivity().getSharedPreferences("CARO", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = storePref.getString("STORE_ITEMS", null);
        Type type = new TypeToken<ArrayList<StoreItems>>(){}.getType();
        storeItems = gson.fromJson(json, type);
    }

}