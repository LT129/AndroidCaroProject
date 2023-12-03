package com.example.caroproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caroproject.Adapter.DBHelper;
import com.example.caroproject.Data.Background;
import com.example.caroproject.Data.Coins;
import com.example.caroproject.Data.PlayerInfo;
import com.example.caroproject.Data.StoreItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SignInFragment extends Fragment {
    private Coins userCoins;
    private ArrayList<Background> userBackground;
    private ArrayList<StoreItem> storeItems;
    private SharedPreferences pref;


    public SignInFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Button btnSignIn;
    private EditText edtUsername, edtPassword;
    private Button btnSignUp;
    DBHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        btnSignIn=view.findViewById(R.id.btnSignInSignIn);
        edtPassword=view.findViewById(R.id.edtPasswordSignIn);
        edtUsername=view.findViewById(R.id.edtUsernameSignIn);
        btnSignUp = view.findViewById(R.id.btnSignUp);

        pref = requireActivity().getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);

        //myDatabaseHelper = new MyDatabaseHelper(requireContext());
        dbHelper = new DBHelper();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                PlayerInfo player = new PlayerInfo(username,password);
                if(username.equals("")||password.equals(""))
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    dbHelper.checkCredentials(player, new DBHelper.OnCredentialsCheckListener() {
                        @Override
                        public void onCredentialsCheckResult(PlayerInfo userInfo) {
                            if (userInfo != null){
                                Toast.makeText(requireContext(), "Login Successfully!", Toast.LENGTH_SHORT).show();

                                updateSharedPreferences(userInfo);
                                NavController navController = Navigation.findNavController(v);
                                navController.navigate(R.id.action_signInFragment_to_mainMenuFragment);
                            }
                            else Toast.makeText(requireContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });
        return view;
    }

    private void updateSharedPreferences(PlayerInfo userInfo) {
        Gson gson = new Gson();
        String json;
        json = gson.toJson(userInfo);
        pref.edit().putString("USER_INFORMATION", json).apply();
        pref.edit().putBoolean(MainActivity.LOGGED_IN_ACCOUNT, true).apply();
    }

}