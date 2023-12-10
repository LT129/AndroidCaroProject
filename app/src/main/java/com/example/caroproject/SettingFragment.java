package com.example.caroproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
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
import android.widget.SeekBar;


public class SettingFragment extends Fragment {

    private ImageButton btnCallBack;
    private ImageButton btnChooseBackground;
    private ImageButton btnChooseMusic;
    private SeekBar seekBarMusic;
    private SeekBar seekBarSound;

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
        seekBarMusic = view.findViewById(R.id.seekBarMusic);
        seekBarSound = view.findViewById(R.id.seekBarSound);
        SharedPreferences pref = requireActivity().getSharedPreferences("CARO", Context.MODE_PRIVATE);

        AudioManager audioManager = (AudioManager) requireActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        seekBarMusic.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBarMusic.setProgress(pref.getInt(MainActivity.MUSIC_VOLUME, 100));
        seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int newVolume, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
                pref.edit().putInt(MainActivity.MUSIC_VOLUME, newVolume).apply();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

//        seekBarSound.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
//        seekBarSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, progress, 0);
//            }
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//        });

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