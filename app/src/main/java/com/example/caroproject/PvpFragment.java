package com.example.caroproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PvpFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private RadioGroup playMode;
    private RadioGroup boardSize;

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
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create an argument and pass it to next fragment
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_pvpFragment_to_inGameFragment);

            }
        });

        return view;
    }
}