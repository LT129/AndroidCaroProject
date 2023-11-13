package com.example.caroproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainMenuFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RadioGroup groupButton;
    private RadioButton btnMenu;
    private RadioButton btnFriendList;
    private RadioButton btnHistory;

    public MainMenuFragment() {
        // Required empty public constructor
    }
    public static MainMenuFragment newInstance(String param1, String param2) {
        MainMenuFragment fragment = new MainMenuFragment();
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
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        btnMenu = view.findViewById(R.id.btnMenu);
        btnFriendList = view.findViewById(R.id.btnFriendList);
        btnHistory = view.findViewById(R.id.btnHistory);
        groupButton = view.findViewById(R.id.groupButton);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        btnMenu.setChecked(true);
        fragmentTransaction.replace(R.id.main_view_holder, new GameModeFragment());
        fragmentTransaction.commit();
        groupButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                btnMenu.setBackgroundResource(R.drawable.game_mode_wait_choose_button);
                btnFriendList.setBackgroundResource(R.drawable.game_mode_wait_choose_button);
                btnHistory.setBackgroundResource(R.drawable.game_mode_wait_choose_button);
                view.findViewById(checkedId).setBackgroundResource(R.drawable.game_mode_on_choose_button);

                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if(checkedId == R.id.btnMenu){
                    fragmentTransaction.replace(R.id.main_view_holder, new GameModeFragment());
                } else if (checkedId ==R.id.btnFriendList) {
                    fragmentTransaction.replace(R.id.main_view_holder, new FriendFragment());
                } else if (checkedId ==R.id.btnHistory){
                    fragmentTransaction.replace(R.id.main_view_holder, new HistoryFragment());
                }
                int state = checkedId;

                fragmentTransaction.addToBackStack(String.valueOf(state)); // Add transaction to back stack
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set the checked state of the RadioButtons based on the currently displayed fragment
        updateRadioButtons();
    }
    public void updateRadioButtons() {
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_view_holder);

        if (currentFragment instanceof GameModeFragment) {
            btnMenu.setChecked(true);
        } else if (currentFragment instanceof FriendFragment) {
            btnFriendList.setChecked(true);
        } else if (currentFragment instanceof HistoryFragment) {
            btnHistory.setChecked(true);
        }
    }
}
