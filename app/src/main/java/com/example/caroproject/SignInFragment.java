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
import com.example.caroproject.Data.StoreItems;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SignInFragment extends Fragment {
    private Coins userCoins;
    private ArrayList<Background> userBackground;
    private ArrayList<StoreItems> storeItems;
    private SharedPreferences pref;


    public SignInFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.background_1);
        initialData();
        pref = getActivity().getSharedPreferences("CARO", Context.MODE_PRIVATE);
        loadData();
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
                        public void onCredentialsCheckResult(boolean credentialsMatch) {
                            if (credentialsMatch){
                                Toast.makeText(requireContext(), "Login Successfully!", Toast.LENGTH_SHORT).show();

                                String position;

                                if(pref != null && pref.contains("BG_POSITION")) {
                                    position = pref.getString("BG_POSITION", "0");
                                } else {
                                    position = "0";
                                }
                                getActivity().getWindow().setBackgroundDrawableResource(userBackground.get(Integer.parseInt(position)).getLayoutBackground());
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


    private void initialData() {
        userCoins = new Coins(2000);
        userBackground = new ArrayList<>();
        userBackground.add(new Background(R.drawable.temp_background_1, R.drawable.background_1, R.drawable.custom_button_1, R.drawable.custom_edittext));

        storeItems = new ArrayList<>();
        storeItems.add(new StoreItems(R.drawable.temp_background_2, R.drawable.background_2, R.drawable.custom_button_2, R.drawable.custom_edittext, new Coins(300)));
        storeItems.add(new StoreItems(R.drawable.temp_background_3, R.drawable.background_3, R.drawable.custom_button_1, R.drawable.custom_edittext, new Coins(400)));
        storeItems.add(new StoreItems(R.drawable.temp_background_4, R.drawable.background_4, R.drawable.custom_button_2, R.drawable.custom_edittext, new Coins(500)));
    }

    private void loadData() {
        Gson gson = new Gson();
        String json;

        if(pref != null) {
            System.out.println("ins");
            Type type;
            if(pref.contains("USER_COINS")) {
                // Get user coins from Shared Preferences
                json = pref.getString("USER_COINS", null);
                userCoins = gson.fromJson(json, Coins.class);
            } else {
                // Save user coins to preferences
                json = gson.toJson(userCoins);
                pref.edit().putString("USER_COINS", json).apply();

            }

            if(pref.contains("USER_BACKGROUND")) {
                // Get user background
                json = pref.getString("USER_BACKGROUND", null);
                type = new TypeToken<ArrayList<Background>>() {
                }.getType();
                userBackground = gson.fromJson(json, type);
            } else {
                // Save user background to preferences
                json = gson.toJson(userBackground);
                pref.edit().putString("USER_BACKGROUND", json).apply();
            }

            if(pref.contains("STORE_ITEMS")) {
                // Get storeItems
                json = pref.getString("STORE_ITEMS", null);
                type = new TypeToken<ArrayList<StoreItems>>(){}.getType();
                storeItems = gson.fromJson(json, type);
            } else {
                // Save store items to preferences
                json = gson.toJson(storeItems);
                pref.edit().putString("STORE_ITEMS", json).apply();
            }
        }
    }
}