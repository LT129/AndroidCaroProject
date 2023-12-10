package com.example.caroproject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.caroproject.Adapter.FirebaseHelper;
import com.example.caroproject.Data.AppData;
import com.example.caroproject.Data.Background;
import com.example.caroproject.Data.Music;
import com.example.caroproject.Data.SoundMaking;
import com.example.caroproject.Data.UserInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.firebase.auth.FirebaseAuth;

public class UserInfoFragment extends Fragment {

    public static final String USERNAME = "Username";
    public static final String PHONE = "Phone";
    public static final String PASSWORD = "Password";

    private ImageView userAvatar;
    private TextView txtDisplayName;

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
    private UserInfo userInfo;
    private ActivityResultLauncher<Intent> launchSomeActivity;
    private Uri selectedImageUri;


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
        userInfo = getUserInfoFromSharedPreferences();


        dialog = new ChangeUserInfoDialogFragment();
        getChildFragmentManager().setFragmentResultListener(ChangeUserInfoDialogFragment.REQUEST_KEY_DIALOG, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                if(result.containsKey(USERNAME)) {
                    String username = result.getString(USERNAME);
                    txtUsername.setText(username);
                    txtDisplayName.setText(username);
                    userInfo.setUsername(username);
                }

                if(result.containsKey(PHONE)) {
                    String phone = result.getString(PHONE);
                    txtPhone.setText(phone);
                    userInfo.setPhoneNumber(phone);
                }

                if(result.containsKey(PASSWORD)) {
                    String password = result.getString(PASSWORD);
                    pref.edit().putString(PASSWORD, password).apply();
                }
            }
        });
        Bundle args = new Bundle();


        userAvatar = view.findViewById(R.id.userAvatar);
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser(view);
            }
        });
        txtDisplayName = view.findViewById(R.id.txtDisplayName);

        usernameDetail = view.findViewById(R.id.usernameDetail);
        usernameDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args.putString("UserInfoType", USERNAME);
                args.putString(USERNAME, userInfo.getUsername());
                dialog.setArguments(args);
                dialog.show(getChildFragmentManager(), "dialog");
            }
        });
        txtUsername = view.findViewById(R.id.txtUsername);

        emailDetail = view.findViewById(R.id.emailDetail);
        txtEmail = view.findViewById(R.id.txtEmail);

        phoneDetail = view.findViewById(R.id.phoneDetail);
        phoneDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args.putString("UserInfoType", PHONE);
                args.putString(PHONE, userInfo.getPhoneNumber());
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
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_userInfoFragment_to_gameModeFragment);
            }
        });


        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(requireContext()).setMessage("Save all change?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateInfoToDatabase();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
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
                new AlertDialog.Builder(requireContext()).setMessage("Log Out from your account?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pref.edit().clear().apply();
                                FirebaseHelper.getInstance().logOut();
                                reSetAppSetting();
                                NavController navController = Navigation.findNavController(v);
                                navController.navigate(R.id.action_userInfoFragment_to_signInFragment);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        launchSomeActivity = registerForActivityResult(
                new ActivityResultContracts
                        .StartActivityForResult(),
                result -> {
                    if (result.getResultCode()
                            == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        // do your operation from here....
                        if (data != null
                                && data.getData() != null) {
                            pref.edit().putString("AVATAR", "avatar").apply();
                            selectedImageUri = data.getData();
                            userInfo.setAvatar(selectedImageUri.toString());
                            Glide.with(view).load(selectedImageUri).error(R.drawable.user_account).into(userAvatar);
                        }
                    }
                });
        init(view);
        return view;
    }

    private UserInfo getUserInfoFromSharedPreferences() {
        Gson gson = new Gson();
        String json = pref.getString("USER_INFORMATION", null);
        Type type = new TypeToken<UserInfo>() {
        }.getType();
        return gson.fromJson(json, type);
    }
    private void updateUserInfoToSharedPreferences() {
        Gson gson = new Gson();
        String json = gson.toJson(userInfo);
        pref.edit().putString("USER_INFORMATION", json).apply();
    }

    private void updateInfoToDatabase() {
        if(pref.contains("AVATAR")) {
            FirebaseHelper.getInstance().uploadUserAvatar(userInfo.getID(), selectedImageUri, getFileExtension(selectedImageUri), new FirebaseHelper.OnResultUploadAvatarListener() {
                @Override
                public void onResult(Uri uri) {
                    userInfo.setAvatar(uri.toString());
                    updateUserInfoToSharedPreferences();
                    FirebaseHelper.getInstance().addDataToDatabase("UserInfo", userInfo.getID(), userInfo);
                    pref.edit().remove("AVATAR").apply();
                }
            });
        } else {
            updateUserInfoToSharedPreferences();
            FirebaseHelper.getInstance().addDataToDatabase("UserInfo", userInfo.getID(), userInfo);
        }


        if(pref.contains(PASSWORD)) {
            FirebaseHelper.getInstance().changePassword(pref.getString(PASSWORD, null));
            pref.edit().remove(PASSWORD).apply();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = requireContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }



    private void init(View view) {
        txtDisplayName.setText(userInfo.getUsername());
        txtUsername.setText(userInfo.getUsername());
        txtEmail.setText(userInfo.getEmail());
        txtPhone.setText(userInfo.getPhoneNumber());
        Glide.with(view).load(userInfo.getAvatar())
                .placeholder(R.drawable.user_account)
                .error(R.drawable.user_account)
                .into(userAvatar);
    }


    private void imageChooser(View view)
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);

    }



    private void reSetAppSetting() {
        AppData appData = AppData.getInstance();
        requireActivity().getWindow().setBackgroundDrawableResource(((Background)appData.getBackgroundList().get(0).getItem()).getLayoutBackground());
        SoundMaking.getInstance().releaseMusic();
        SoundMaking.getInstance().createMusic(requireContext(),((Music) appData.getMusicList().get(0).getItem()).getSourceId());
        SoundMaking.getInstance().playMusic();
    }


}