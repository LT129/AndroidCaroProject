package com.example.caroproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Stack;

public class MainMenuFragment extends Fragment {
    private RadioGroup groupButton;
    private RadioButton btnMenu;
    private RadioButton btnFriendList;
    private RadioButton btnHistory;
    private SharedPreferences pref;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        pref =  requireActivity().getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);

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

                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if(checkedId == R.id.btnMenu){
                    fragmentTransaction.replace(R.id.main_view_holder, new GameModeFragment());
                } else if (checkedId ==R.id.btnFriendList) {
                    if(!pref.contains(MainActivity.LOGGED_IN_ACCOUNT)) {
                        new AlertDialog.Builder(requireContext()).setMessage("You're not Sign In, Sign In now?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        View v = requireView();
                                        NavController navController = Navigation.findNavController(v);
                                        navController.navigate(R.id.action_mainMenuFragment_to_signInFragment);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }else {
                        fragmentTransaction.replace(R.id.main_view_holder, new FriendFragment());
                    }
                } else if (checkedId ==R.id.btnHistory){
                    if(!pref.contains(MainActivity.LOGGED_IN_ACCOUNT)) {
                        new AlertDialog.Builder(requireContext()).setMessage("You're not Sign In, Sign In now?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        View v = requireView();
                                        NavController navController = Navigation.findNavController(v);
                                        navController.navigate(R.id.action_mainMenuFragment_to_signInFragment);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }else {
                        fragmentTransaction.replace(R.id.main_view_holder, new HistoryFragment());
                    }
                }
                int state = checkedId;

                fragmentTransaction.commit();
            }
        });

            OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(groupButton.getCheckedRadioButtonId() != R.id.btnMenu) {
                    btnMenu.setChecked(true);
                    getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view_holder, new GameModeFragment())
                            .commit();
                } else {
                    setEnabled(false); //this is important line
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        return view;
    }

//
}
