package com.example.caroproject;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class SettingFragment extends Fragment {

    private ImageButton btnCallBack;
    private ImageButton btnChooseBackground;
    private ImageButton btnChooseMusic;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        btnChooseBackground = view.findViewById(R.id.btnChooseBackground);
        btnChooseMusic = view.findViewById(R.id.btnChooseMusic);
        btnCallBack = view.findViewById(R.id.btnCallBack);

        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        btnChooseMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseMusicDialogFragment dialog = new ChooseMusicDialogFragment();
                dialog.show(requireActivity().getSupportFragmentManager(), "dialog");
            }
        });

        btnChooseBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseBackgroundDialogFragment dialog = new ChooseBackgroundDialogFragment();
                dialog.show(requireActivity().getSupportFragmentManager(), "dialog");
            }
        });
        return view;
    }
}