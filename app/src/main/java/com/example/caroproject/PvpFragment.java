package com.example.caroproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PvpFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ImageButton btnCallBack;

    private RadioGroup playMode;
    private RadioButton rdoOffline, rdoOnline, rdoSize9, rdoSize15, rdoSize21, time15, time45, timeUnlimited;

    private ImageButton chooseShape;
    private ImageButton chooseColor;

    private Button btnPlay;
    public PvpFragment() {
        // Required empty public constructor
    }
    public static PvpFragment newInstance(String param1, String param2) {
        PvpFragment fragment = new PvpFragment();
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
        View view = inflater.inflate(R.layout.fragment_pvp, container, false);

        playMode = view.findViewById(R.id.playMode);
        playMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton mode = (RadioButton) view.findViewById(checkedId);
                switch (mode.getText().toString()) {
                    case"Online": {
                        //TODO setting for online mode
                        break;
                    }

                    case"Offline": {
                        //TODO setting for offline mode
                        break;
                    }
                }
            }
        });

        chooseShape = view.findViewById(R.id.chooseShape);
        chooseShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO show a dialog to choose shape
            }
        });

        chooseColor = view.findViewById(R.id.chooseColor);
        chooseColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO show a dialog to choose color
            }
        });

        btnPlay = view.findViewById(R.id.btnPlay);
        rdoOffline=view.findViewById(R.id.offlinePlayMode);
        rdoOnline=view.findViewById(R.id.onlinePlayMode);
        rdoSize9=view.findViewById(R.id.boardSize9);
        rdoSize15=view.findViewById(R.id.boardSize15);
        rdoSize21=view.findViewById(R.id.boardSize21);
        time45=view.findViewById(R.id.time45sPVE);
        time15=view.findViewById(R.id.time15sPVE);
        timeUnlimited=view.findViewById(R.id.timeUnlimitedPVE);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create an argument and pass it to next fragment

                // Tạo một Bundle để chứa dữ liệu
                Bundle bundle = new Bundle();
                // Đặt dữ liệu vào Bundle, ví dụ:
                int sizeBoard;
                if(rdoSize9.isChecked()){
                    sizeBoard=10;
                } else if (rdoSize15.isChecked()) {
                    sizeBoard=15;
                } else{
                    sizeBoard=20;
                }
                int time;
                if(time15.isChecked()){
                    time=16000;
                } else if (time45.isChecked()) {
                    time=46000;
                }else{
                    time=-1;
                }
                bundle.putInt("sizeBoard", sizeBoard);
                bundle.putInt("time", time);
                if((rdoSize21.isChecked()||rdoSize15.isChecked()||rdoSize9.isChecked())
                        &&(time15.isChecked()||time45.isChecked()||timeUnlimited.isChecked())) {
                    if (rdoOffline.isChecked()) {
                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.action_pvpFragment_to_inGameFragment, bundle);
                    }
                    else{
                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.action_pvpFragment_to_inGameOnlineFragment, bundle);
                    }
                }
            }
        });

        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getOnBackPressedDispatcher().onBackPressed();
                NavController navController=Navigation.findNavController(v);
                navController.navigate(R.id.action_pvpFragment_to_gameModeFragment);
            }
        });

        return view;
    }
}