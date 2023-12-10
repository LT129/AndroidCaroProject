package com.example.caroproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.caroproject.Adapter.FirebaseHelper;
import com.example.caroproject.Adapter.FriendListAdapter;
import com.example.caroproject.Data.UserInfo;
import com.firebase.ui.auth.data.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ShowInfoFragment extends Fragment implements FriendListAdapter.OnItemClickListener {
    public static final String USER_ID = "UserID";
    public static final String PHONE = "Phone";
    private String TargetUserID;

    private TextView txtDisplayName,txtUserName,txtPhone,txtEmail;
    private Button btnBack,btnFriendRequest;
    private FirebaseHelper firebaseHelper;
    private SharedPreferences pref;
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
        View view = inflater.inflate(R.layout.fragment_show_info, container, false);
        txtDisplayName = view.findViewById(R.id.txtDisplayName);
        txtUserName = view.findViewById(R.id.txtUserName);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtEmail = view.findViewById(R.id.txtEmail);
        btnBack = view.findViewById(R.id.btnBack);
        btnFriendRequest = view.findViewById(R.id.btnFriendRequest);
        firebaseHelper = new FirebaseHelper();
        TargetUserID = getArguments().getString(USER_ID);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("Src","Friend");
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_showInfoFragment_to_friendFragment,args);
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
        txtDisplayName.setText(userInfo.getUsername());
        txtUserName.setText(userInfo.getUsername());
        txtEmail.setText(userInfo.getEmail());
        txtPhone.setText(userInfo.getPhoneNumber());
    }

    @Override
    public void onItemClick(String userID) {
        this.TargetUserID = userID;
    }
}
