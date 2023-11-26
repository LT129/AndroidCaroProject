package com.example.caroproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
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

    public static final String NICKNAME = "Nickname";
    public static final String USERNAME = "Username";
    public static final String EMAIL = "Email";
    public static final String PHONE = "Phone";
    public static final String PASSWORD = "Password";

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


    private DialogFragment dialog;

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


        dialog = new ChangeUserInfoDialogFragment();
        getChildFragmentManager().setFragmentResultListener(ChangeUserInfoDialogFragment.REQUEST_KEY_DIALOG, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if(result.containsKey(NICKNAME)) {
                    txtNickName.setText(result.getString(NICKNAME));
                }

                if(result.containsKey(EMAIL)) {
                    txtEmail.setText(result.getString(EMAIL));
                }

                if(result.containsKey(PHONE)) {
                    txtPhone.setText(result.getString(PHONE));
                }
            }
        });
        Bundle args = new Bundle();


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
                args.putString("UserInfoType", USERNAME);
                dialog.setArguments(args);
                dialog.show(getChildFragmentManager(), "dialog");
            }
        });
        txtUsername = view.findViewById(R.id.txtUsername);

        emailDetail = view.findViewById(R.id.emailDetail);
        emailDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args.putString("UserInfoType", EMAIL);
                dialog.setArguments(args);
                dialog.show(getChildFragmentManager(), "dialog");
            }
        });
        txtEmail = view.findViewById(R.id.txtEmail);

        phoneDetail = view.findViewById(R.id.phoneDetail);
        phoneDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args.putString("UserInfoType", PHONE);
                dialog.setArguments(args);
                dialog.show(getChildFragmentManager(), "dialog");
            }
        });
        txtPhone = view.findViewById(R.id.txtPhone);

        passwordDetail = view.findViewById(R.id.passwordDetail);
        passwordDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args.putString("UserInfoType", PASSWORD);
                dialog.setArguments(args);
                dialog.show(getChildFragmentManager(), "dialog");
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