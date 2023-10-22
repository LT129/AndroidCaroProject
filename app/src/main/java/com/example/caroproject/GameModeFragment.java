package com.example.caroproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class GameModeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnPvp;
    private Button btnPvc;
    private Button btnSetting;
    private Button btnExit;
    private ImageView userAvatar;

    public GameModeFragment() {
        // Required empty public constructor
    }

    public static GameModeFragment newInstance(String param1, String param2) {
        GameModeFragment fragment = new GameModeFragment();
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
        View view = inflater.inflate(R.layout.fragment_game_mode, container, false);
        btnPvp = view.findViewById(R.id.btnPvp);
        btnPvc = view.findViewById(R.id.btnPvc);
        btnSetting = view.findViewById(R.id.btnSetting);
        btnExit = view.findViewById(R.id.btnExit);
        btnPvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_gameModeFragment_to_pvpFragment);
            }
        });

        btnPvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO change to fragment_pvc
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_gameModeFragment_to_settingFragment);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO finish all activity
            }
        });

        userAvatar = view.findViewById(R.id.userAvatar);
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO change to fragment_user_info
            }
        });
        return view;
    }
}