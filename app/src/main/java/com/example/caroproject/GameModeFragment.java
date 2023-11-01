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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GameModeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton btnStore;
    private Button btnPvp;
    private Button btnPvc;
    private Button btnSetting;
    private Button btnExit;
    private ImageView userAvatar;

    private RadioGroup groupButton;
    private RadioButton btnMenu;
    private RadioButton btnFriendList;
    private RadioButton btnHistory;

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
        btnStore = view.findViewById(R.id.btnStore);
        groupButton = view.findViewById(R.id.groupButton);
        btnMenu = view.findViewById(R.id.btnMenu);
        btnFriendList = view.findViewById(R.id.btnFriendList);
        btnHistory = view.findViewById(R.id.btnHistory);
        groupButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                btnMenu.setBackgroundResource(R.drawable.game_mode_wait_choose_button);
                btnFriendList.setBackgroundResource(R.drawable.game_mode_wait_choose_button);
                btnHistory.setBackgroundResource(R.drawable.game_mode_wait_choose_button);
                view.findViewById(checkedId).setBackgroundResource(R.drawable.game_mode_on_choose_button);
            }
        });

        btnPvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SoundMaking.buttonClickedSound();
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_gameModeFragment_to_pvpFragment);
            }
        });

        btnPvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SoundMaking.buttonClickedSound();
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_gameModeFragment_to_inGameAIFragment);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SoundMaking.buttonClickedSound();

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_gameModeFragment_to_settingFragment);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SoundMaking.buttonClickedSound();
                getActivity().finishAffinity();
            }
        });

        userAvatar = view.findViewById(R.id.userAvatar);
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO change to fragment_user_info
            }
        });

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_gameModeFragment_to_storeFragment);
            }
        });
        return view;
    }
}