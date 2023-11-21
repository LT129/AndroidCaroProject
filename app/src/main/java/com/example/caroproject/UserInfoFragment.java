package com.example.caroproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserInfoFragment extends Fragment {

    private ImageButton userAvatar;
    private TextView txtNickName;

    private RelativeLayout usernameDetail;
    private TextView txtUsername;

    private RelativeLayout emailDetail;
    private TextView txtEmail;

    private RelativeLayout phoneDetail;
    private TextView txtPhone;

    private RelativeLayout passwordDetail;

    private Button btnCancel;
    private Button btnSave;
    private ImageButton btnCallBack;
    private RelativeLayout logOut;



    private SharedPreferences pref;


    public UserInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        // Get SharedPreferences
        pref = requireContext().getSharedPreferences("CARO", Context.MODE_PRIVATE);




        userAvatar = view.findViewById(R.id.userAvatar);
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO change layout to change avatar
            }
        });
        txtNickName = view.findViewById(R.id.txtNickName);

        usernameDetail = view.findViewById(R.id.usernameDetail);
        usernameDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO change layout
            }
        });
        txtUsername = view.findViewById(R.id.txtUsername);

        emailDetail = view.findViewById(R.id.emailDetail);
        emailDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO change layout
            }
        });
        txtEmail = view.findViewById(R.id.txtEmail);

        phoneDetail = view.findViewById(R.id.phoneDetail);
        phoneDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO change layout
            }
        });
        txtPhone = view.findViewById(R.id.txtPhone);

        passwordDetail = view.findViewById(R.id.passwordDetail);
        passwordDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO change layout
            }
        });


        btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO reset information and go back to home
            }
        });


        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ask user to save and save information to db
            }
        });

        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        logOut = view.findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setMessage("Log Out from your account?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pref.edit().clear().apply();
                                NavController navController = Navigation.findNavController(v);
                                navController.navigate(R.id.action_userInfoFragment_to_signInFragment);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });



        return view;
    }
}