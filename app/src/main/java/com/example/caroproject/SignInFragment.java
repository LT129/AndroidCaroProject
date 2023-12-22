package com.example.caroproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caroproject.Adapter.FirebaseHelper;
import com.example.caroproject.Data.Background;
import com.example.caroproject.Data.Coins;
import com.example.caroproject.Data.UserInfo;
import com.example.caroproject.Data.StoreItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SignInFragment extends Fragment {
    private Coins userCoins;
    private ArrayList<Background> userBackground;
    private ArrayList<StoreItem> storeItems;
    private SharedPreferences pref;
    private FirebaseAuth auth;

    public SignInFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            //TODO go to the Main memu when the user is already signed in


        }
    }

    private Button btnSignIn;
    private EditText edtEmail, edtPassword;
    private TextView forgotPassword;
    private Button btnSignUp;
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        btnSignIn=view.findViewById(R.id.btnSignInSignIn);
        edtPassword=view.findViewById(R.id.edtPasswordSignIn);
        edtEmail=view.findViewById(R.id.edtEmail);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        forgotPassword = view.findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_signInFragment_to_forgotPasswordFragment);
            }
        });

        pref = requireActivity().getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);

        
        auth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if(email.equals("")||password.equals(""))
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else {
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //Get information of new user to firestore
                                        FirebaseUser user = task.getResult().getUser();
                                        FirebaseHelper.getInstance().retrieveDataFromDatabase("UserInfo", user.getUid(), UserInfo.class,
                                                new FirebaseHelper.OnCompleteRetrieveDataListener() {
                                                    @Override
                                                    public<T> void onComplete(List<T> list) {
                                                        UserInfo userInfo = (UserInfo) list.get(0);
                                                        userInfo.setStatus(true);
                                                        FirebaseHelper.getInstance().addDataToDatabase("UserInfo", userInfo.getID(), userInfo);
                                                        updateSharedPreferences(userInfo);
                                                    }
                                                });

                                        // Sign in success, update UI with the signed-in user's information
                                        NavController navController = Navigation.findNavController(v);
                                        navController.navigate(R.id.action_signInFragment_to_mainMenuFragment);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(requireContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    }
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

    private void updateSharedPreferences(UserInfo userInfo) {
        Gson gson = new Gson();
        String json;
        json = gson.toJson(userInfo);
        pref.edit().putString("USER_INFORMATION", json).apply();
        pref.edit().putBoolean(MainActivity.LOGGED_IN_ACCOUNT, true).apply();
    }

}