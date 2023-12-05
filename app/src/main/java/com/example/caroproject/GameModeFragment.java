package com.example.caroproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.caroproject.Data.SoundMaking;

public class GameModeFragment extends Fragment {

    private ImageButton btnStore;
    private Button btnPvp;
    private Button btnPve;
    private Button btnSetting;
    private Button btnExit;
    private ImageView userAvatar;

    private SharedPreferences pref;



    public GameModeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_mode, container, false);

        initiateData();

        btnPvp = view.findViewById(R.id.btnPvp);
        btnPve = view.findViewById(R.id.btnPve);
        btnSetting = view.findViewById(R.id.btnSetting);
        btnExit = view.findViewById(R.id.btnExit);
        btnStore = view.findViewById(R.id.btnStore);

        btnPvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_mainMenuFragment_to_pvpFragment);
            }
        });

        btnPve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_mainMenuFragment_to_pveFragment);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_mainMenuFragment_to_settingFragment);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finishAffinity();
            }
        });

        userAvatar = view.findViewById(R.id.userAvatar);
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pref.contains(MainActivity.LOGGED_IN_ACCOUNT)) {
                    new AlertDialog.Builder(requireContext()).setMessage("You're not Sign In, Sign In now?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    NavController navController = Navigation.findNavController(v);
                                    navController.navigate(R.id.action_mainMenuFragment_to_signInFragment);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }else {
                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_mainMenuFragment_to_userInfoFragment);
                }
            }
        });

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pref.contains(MainActivity.LOGGED_IN_ACCOUNT)) {
                    new AlertDialog.Builder(requireContext()).setMessage("You're not Sign In, Sign In now?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    NavController navController = Navigation.findNavController(v);
                                    navController.navigate(R.id.action_mainMenuFragment_to_signInFragment);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                } else {
                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_mainMenuFragment_to_storeFragment);
                }
            }
        });

        return view;
    }

    private void initiateData() {
        pref = requireActivity().getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);
    }
}