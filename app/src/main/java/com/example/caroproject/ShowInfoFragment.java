package com.example.caroproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.caroproject.Adapter.FirebaseHelper;
import com.example.caroproject.Adapter.FriendListAdapter;
import com.example.caroproject.Data.UserInfo;
import com.firebase.ui.auth.data.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ShowInfoFragment extends Fragment {
    public static final String USER_ID = "UserID";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String USER_TYPE_1 = "Friend";
    public static final String USER_TYPE_2 = "Opponent";
    private static final int FRIEND_REQUEST_NOTIFICATION = 1;
    private String TargetUserID;
    private ImageView userAvatar;

    private TextView txtUserName,txtPhone,txtEmail, txtNameInfo;
    private TextView now;
    private TextView nol;
    private Button btnFriendRequest;
    private ImageButton btnCallBack;
    private FirebaseHelper firebaseHelper;
    private View view;
    public ShowInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_info, container, false);
        txtUserName = view.findViewById(R.id.txtUsername);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtEmail = view.findViewById(R.id.txtEmail);
        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnFriendRequest = view.findViewById(R.id.btnFriendRequest);
        now = view.findViewById(R.id.now);
        nol = view.findViewById(R.id.nol);
        txtNameInfo = view.findViewById(R.id.txtNameInfo);
        userAvatar = view.findViewById(R.id.userAvatar);
        firebaseHelper = FirebaseHelper.getInstance();
        TargetUserID = getArguments().getString(USER_ID);

        txtNameInfo.setText(getArguments().getString(USER_TYPE) + " Information");
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        //TODO check if this user is friend or not
        btnFriendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO send friends request
                sendFriendRequestNotification();
            }
        });

        firebaseHelper.getUserById(TargetUserID, new FirebaseHelper.OnGetUserByIdListener() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                UpdateData(userInfo);
            }
            @Override
            public void onFailure(Exception e) {
                Log.e("Error:",e.getMessage());
            }
        });



        return view;
    }
    private void UpdateData(UserInfo userInfo){
        txtUserName.setText(userInfo.getUsername());
        txtEmail.setText(userInfo.getEmail());
        txtPhone.setText(userInfo.getPhoneNumber());
        now.setText("Number of Wins: " + userInfo.getWins());
        nol.setText("Number of Losses: " + userInfo.getLosses());
        Glide.with(view).load(userInfo.getAvatar())
                .placeholder(R.drawable.user_account)
                .error(R.drawable.user_account)
                .into(userAvatar);
    }

    private void sendFriendRequestNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_playstore);

        String channelId = "channelId";


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getContext().getApplicationContext(), channelId);
        builder.setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Want to add friend")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = notificationManager.getNotificationChannel(channelId);

        if(channel == null) {
            channel = new NotificationChannel(channelId,
                    "NAME", NotificationManager.IMPORTANCE_HIGH);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(FRIEND_REQUEST_NOTIFICATION, builder.build());
    }
}
